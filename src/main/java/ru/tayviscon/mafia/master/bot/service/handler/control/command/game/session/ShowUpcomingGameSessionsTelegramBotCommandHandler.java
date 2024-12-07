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
import ru.tayviscon.mafia.master.bot.repository.game.session.MafiaGameSessionRepository;
import ru.tayviscon.mafia.master.bot.service.handler.control.command.TelegramBotCommandHandler;
import ru.tayviscon.mafia.master.bot.util.DateTimeUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShowUpcomingGameSessionsTelegramBotCommandHandler implements TelegramBotCommandHandler {

    private final MafiaGameSessionRepository mafiaGameSessionRepository;

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

        var upcomingMafiaGameSessions = mafiaGameSessionRepository.findUpcoming();

        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        var gameSessionButtons = upcomingMafiaGameSessions.stream().map(gameSession -> {
            var gameSessionButton = new InlineKeyboardButton();
            gameSessionButton.setText(
                    DateTimeUtils.getGameSessionDateTimeRange(gameSession.getStartedAt(), gameSession.getEndedAt())
            );
            gameSessionButton.setCallbackData(String.format("get-mafia-game-session?id=%s", gameSession.getId().toString()));
            return List.of(gameSessionButton);
        }).toList();
        inlineKeyboardMarkup.setKeyboard(gameSessionButtons);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        telegramBot.execute(sendMessage);
    }

}
