package ru.tayviscon.mafia.master.bot.service.handler.control.command;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tayviscon.mafia.master.bot.service.ui.form.MainMenuFormTelegramBotUiComponent;

@Log4j2
@Component
@RequiredArgsConstructor
public class StartTelegramBotCommandHandler implements TelegramBotCommandHandler {

    public static final String NAME = "start";
    private final MainMenuFormTelegramBotUiComponent mainMenuFormTelegramBotUiComponent;

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
        var telegramUserId = update.getMessage().getFrom().getId();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(mainMenuFormTelegramBotUiComponent.getMessage());
        sendMessage.setReplyMarkup(mainMenuFormTelegramBotUiComponent.getKeyboard(telegramUserId));
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        telegramBot.execute(sendMessage);
    }


}
