package ru.tayviscon.mafia.master.bot.service.handler.control.command.game.session;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.tayviscon.mafia.master.bot.domain.dto.mafia.game.session.MafiaGameSessionCardFilter;
import ru.tayviscon.mafia.master.bot.domain.enums.MafiaGameSessionStage;
import ru.tayviscon.mafia.master.bot.domain.query.filter.AdvancedFieldFilter;
import ru.tayviscon.mafia.master.bot.domain.query.pagination.Pagination;
import ru.tayviscon.mafia.master.bot.domain.query.param.FetchQueryParams;
import ru.tayviscon.mafia.master.bot.domain.query.sorting.Sorting;
import ru.tayviscon.mafia.master.bot.domain.query.sorting.SortingType;
import ru.tayviscon.mafia.master.bot.service.handler.control.command.TelegramBotCommandHandler;
import ru.tayviscon.mafia.master.bot.service.usecase.MafiaGameSessionCardFetchByParamsUseCase;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShowUpcomingGameSessionsTelegramBotCommandHandler implements TelegramBotCommandHandler {

    private final MafiaGameSessionCardFetchByParamsUseCase mafiaGameSessionCardFetchByParamsUseCase;

    public static final String NAME = "upcoming_games";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getLabel() {
        return "Покажи ближайшие игры";
    }

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public void handle(Update update, TelegramLongPollingBot telegramBot) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Вот список ближайших игр:");

        var filter = new MafiaGameSessionCardFilter();
        filter.setStage(AdvancedFieldFilter.eq(MafiaGameSessionStage.PLANNED));
        var params = new FetchQueryParams<MafiaGameSessionCardFilter>();
        params.setFilter(filter);
        params.setPagination(Pagination.getAllRows());
        params.setSorting(List.of(Sorting.of("startedAt", SortingType.ASC_NULLS_LAST)));

        var mafiaGameSessionCards = mafiaGameSessionCardFetchByParamsUseCase.exec(params);


        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        var gameSessionButtons = mafiaGameSessionCards.stream().map(gameSession -> {
            var gameSessionButton = new InlineKeyboardButton();
            gameSessionButton.setText(gameSession.getGameSessionDateTimeRange());
            gameSessionButton.setCallbackData(String.format("get-mafia-game-session?id=%s", gameSession.getId().toString()));
            return List.of(gameSessionButton);
        }).toList();
        inlineKeyboardMarkup.setKeyboard(gameSessionButtons);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        telegramBot.execute(sendMessage);
    }

}
