package ru.tayviscon.mafia.master.bot.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tayviscon.mafia.master.bot.properties.TelegramBotProperties;
import ru.tayviscon.mafia.master.bot.service.consumer.TelegramUpdateConsumer;
import ru.tayviscon.mafia.master.bot.service.dispatcher.TelegramBotCommandDispatcherService;
import ru.tayviscon.mafia.master.bot.service.registrar.TelegramBotCommandRegisterService;

import java.util.List;

@Component
public class MafiaMasterTelegramBotService extends TelegramLongPollingBot {

    private final TelegramBotProperties telegramBotProperties;
    private final List<TelegramUpdateConsumer> telegramUpdateConsumers;
    private final TelegramBotCommandRegisterService telegramBotCommandRegisterService;
    private final TelegramBotCommandDispatcherService telegramBotCommandDispatcherService;

    public MafiaMasterTelegramBotService(
        TelegramBotProperties telegramBotProperties,
        List<TelegramUpdateConsumer> telegramUpdateConsumers,
        TelegramBotCommandRegisterService telegramBotCommandRegisterService,
        TelegramBotCommandDispatcherService telegramBotCommandDispatcherService
    ) {
        super(telegramBotProperties.getToken());
        this.telegramBotProperties = telegramBotProperties;
        this.telegramUpdateConsumers = telegramUpdateConsumers;
        this.telegramBotCommandRegisterService = telegramBotCommandRegisterService;
        this.telegramBotCommandDispatcherService = telegramBotCommandDispatcherService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        telegramUpdateConsumers.forEach(consumer -> consumer.accept(update));
        telegramBotCommandDispatcherService.dispatchCommands(update, this);
    }

    @Override
    public String getBotUsername() {
        return telegramBotProperties.getUsername();
    }

    @PostConstruct
    private void postConstruct() {
        telegramBotCommandRegisterService.registerCommands(this);
    }
}
