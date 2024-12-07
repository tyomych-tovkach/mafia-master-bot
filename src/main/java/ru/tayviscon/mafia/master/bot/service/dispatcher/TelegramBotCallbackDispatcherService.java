package ru.tayviscon.mafia.master.bot.service.dispatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tayviscon.mafia.master.bot.service.handler.control.callback.TelegramBotCallbackHandler;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class TelegramBotCallbackDispatcherService {

    private final List<TelegramBotCallbackHandler> telegramBotCallbackHandlers;

    public void dispatchCallback(Update update, TelegramLongPollingBot telegramBot) {
        if (update.hasCallbackQuery()) {
            telegramBotCallbackHandlers.stream()
                .filter(handler -> handler.isApplicableForHandling(update))
                .forEach(handler -> handler.handle(update, telegramBot));
        }
    }

}
