package ru.tayviscon.mafia.master.bot.service.handler.control.command;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartTelegramBotCommandHandler implements TelegramBotCommandHandler {

    public static final String NAME = "start";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getLabel() {
        return "Запустить";
    }

    @Override
    @SneakyThrows
    public void handle(Update update, TelegramLongPollingBot telegramBot) {
        //TODO: написать обработчик для команды '/start'
    }


}
