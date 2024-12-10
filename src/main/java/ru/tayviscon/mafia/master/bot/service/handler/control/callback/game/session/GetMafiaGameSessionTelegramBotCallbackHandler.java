package ru.tayviscon.mafia.master.bot.service.handler.control.callback.game.session;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.tayviscon.mafia.master.bot.service.handler.control.callback.TelegramBotCallbackHandler;
import ru.tayviscon.mafia.master.bot.service.usecase.MafiaGameSessionCardFetchUseCase;
import ru.tayviscon.mafia.master.bot.util.ValueUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class GetMafiaGameSessionTelegramBotCallbackHandler implements TelegramBotCallbackHandler {

    public static final String NAME = "get-mafia-game-session";
    private static final String ID_REQUIRED_PARAM_KEY = "id";

    private final MafiaGameSessionCardFetchUseCase mafiaGameSessionCardFetchUseCase;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    @SneakyThrows
    public void handle(Update update, TelegramLongPollingBot telegramBot) {
        String callbackData = update.getCallbackQuery().getData();
        var queryParams = UriComponentsBuilder
            .fromUriString(callbackData)
            .build().getQueryParams().toSingleValueMap();
        String gameSessionId = queryParams.get(ID_REQUIRED_PARAM_KEY);

        if (gameSessionId == null || !ValueUtils.isValidUuid(gameSessionId)) {
            log.error("Callback '{}' does not contains required attributes: [id]", callbackData);
            return;
        }

        var mafiaGameSession = mafiaGameSessionCardFetchUseCase.exec(UUID.fromString(gameSessionId));

        var sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setText(
            """
            Игра: %s
            Состоится: *%s*
            На эту игру действует скидка 10%%
            """.formatted(
                mafiaGameSession.getId(),
                mafiaGameSession.getGameSessionDateTimeRange()
            )
        );
        sendMessage.setParseMode(ParseMode.MARKDOWN);

        var inlineKeyboardMarkup = new InlineKeyboardMarkup();

        var telegramUserId = update.getCallbackQuery().getFrom().getId();


        var buttonsLine = new ArrayList<InlineKeyboardButton>();
        if (
            mafiaGameSession.getActiveParticipantsMinimalMeritInfo() != null
                && mafiaGameSession.getActiveParticipantsMinimalMeritInfo().stream()
                .anyMatch(activeParticipant -> Objects.equals(activeParticipant.getTelegramUserId(), telegramUserId))
        ) {
            var unregisterForGameSessionButton = InlineKeyboardButton.builder()
                .text("Отменить запись")
                .callbackData(String.format("unreg-in-game?id=%s", mafiaGameSession.getId()))
                .build();
            buttonsLine.add(unregisterForGameSessionButton);
        } else {
            var registerForGameSessionButton = InlineKeyboardButton.builder()
                .text("Записаться")
                .callbackData(String.format("reg-in-game?id=%s", mafiaGameSession.getId()))
                .build();
            buttonsLine.add(registerForGameSessionButton);
        }

        inlineKeyboardMarkup.setKeyboard(List.of(buttonsLine));
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        telegramBot.execute(sendMessage);

    }
}
