package ru.tayviscon.mafia.master.bot.service.registrar;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import ru.tayviscon.mafia.master.bot.service.handler.control.command.TelegramBotCommandHandler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramBotCommandRegisterService {

    private final List<TelegramBotCommandHandler> telegramBotCommandHandlers;

    @SneakyThrows
    public void registerCommands(TelegramLongPollingBot telegramBot) {
        var defaultScopeBotCommands = telegramBotCommandHandlers.stream()
            .map(command -> new BotCommand(command.getName(), command.getLabel())).toList();
        telegramBot.execute(new SetMyCommands(defaultScopeBotCommands, new BotCommandScopeDefault(), null));
    }

}
