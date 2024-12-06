package ru.tayviscon.mafia.master.bot.service.dispatcher;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tayviscon.mafia.master.bot.service.handler.control.command.TelegramBotCommandHandler;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Log4j2
public class TelegramBotCommandDispatcherService {

    private final Map<String, TelegramBotCommandHandler> commandNameToHandler;

    public TelegramBotCommandDispatcherService(List<TelegramBotCommandHandler> telegramBotCommandHandlers) {
        this.commandNameToHandler = telegramBotCommandHandlers.stream()
            .collect(Collectors.toMap(TelegramBotCommandHandler::getPrefixedName, Function.identity()));
    }

    public void dispatchCommands(Update update, TelegramLongPollingBot telegramBot) {
        Optional.ofNullable(update.getMessage())
            .map(Message::getEntities)
            .filter(messageEntities -> !messageEntities.isEmpty())
            .flatMap(
                messageEntities -> messageEntities.stream()
                    .filter(messageEntity -> "bot_command".equals(messageEntity.getType()))
                    .map(MessageEntity::getText)
                    .findFirst()
            ).ifPresent(
                commandText -> Optional.ofNullable(commandNameToHandler.get(commandText))
                    .ifPresentOrElse(
                        handler -> handler.handle(update, telegramBot),
                        () -> log.warn("Command '{}' received, but no handler was found fot it", commandText)
                    )
            );
    }


}
