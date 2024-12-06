package ru.tayviscon.mafia.master.bot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tayviscon.mafia.master.bot.properties.TelegramBotProperties;

@Component
public class MafiaMasterTelegramBotService extends TelegramLongPollingBot {

    private final TelegramBotProperties telegramBotProperties;

    public MafiaMasterTelegramBotService(
        TelegramBotProperties telegramBotProperties
    ) {
        super(telegramBotProperties.getToken());
        this.telegramBotProperties = telegramBotProperties;
    }


    @Override
    public void onUpdateReceived(Update update) {
    }

    @Override
    public String getBotUsername() {
        return telegramBotProperties.getUsername();
    }
}
