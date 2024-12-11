package ru.tayviscon.mafia.master.bot.service.handler.control.command;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tayviscon.mafia.master.bot.service.ui.MainMenuTelegramBotUseCase;

@Log4j2
@Component
@RequiredArgsConstructor
public class StartTelegramBotCommandHandler implements TelegramBotCommandHandler {

    public static final String NAME = "start";
    private final MainMenuTelegramBotUseCase mainMenuTelegramBotUseCase;

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
        mainMenuTelegramBotUseCase.exec(update, telegramBot);
    }


}
