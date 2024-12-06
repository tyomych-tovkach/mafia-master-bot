package ru.tayviscon.mafia.master.bot.service.handler.control.command;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramBotCommandHandler {

    String COMMAND_PREFIX = "/";

    String getName();

    String getLabel();

    void handle(Update update, TelegramLongPollingBot telegramBot);

    default String getPrefixedName() {
        return COMMAND_PREFIX + getName();
    }

}
