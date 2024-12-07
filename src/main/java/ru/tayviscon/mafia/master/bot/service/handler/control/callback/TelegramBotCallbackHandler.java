package ru.tayviscon.mafia.master.bot.service.handler.control.callback;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramBotCallbackHandler {

    String getName();

    void handle(Update update, TelegramLongPollingBot telegramBot);

    default boolean isApplicableForHandling(Update update) {
        return update.getCallbackQuery().getData().startsWith(getName());
    };
}
