package ru.tayviscon.mafia.master.bot.service.handler.control.command.game.session;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tayviscon.mafia.master.bot.service.handler.control.command.TelegramBotCommandHandler;

@Component
public class AddGameSessionTelegramBotCommandHandler implements TelegramBotCommandHandler {

    public static final String NAME = "add_game";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getLabel() {
        return "Запланировать игру";
    }

    @Override
    public void handle(Update update, TelegramLongPollingBot telegramBot) {

    }
}
