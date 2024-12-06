package ru.tayviscon.mafia.master.bot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tayviscon.mafia.master.bot.properties.TelegramBotProperties;
import ru.tayviscon.mafia.master.bot.service.consumer.TelegramUpdateConsumer;

import java.util.List;

@Component
public class MafiaMasterTelegramBotService extends TelegramLongPollingBot {

    private final TelegramBotProperties telegramBotProperties;
    private final List<TelegramUpdateConsumer> telegramUpdateConsumers;

    public MafiaMasterTelegramBotService(
        TelegramBotProperties telegramBotProperties,
        List<TelegramUpdateConsumer> telegramUpdateConsumers
    ) {
        super(telegramBotProperties.getToken());
        this.telegramBotProperties = telegramBotProperties;
        this.telegramUpdateConsumers = telegramUpdateConsumers;
    }

    @Override
    public void onUpdateReceived(Update update) {
        telegramUpdateConsumers.forEach(consumer -> consumer.accept(update));
    }

    @Override
    public String getBotUsername() {
        return telegramBotProperties.getUsername();
    }
}
