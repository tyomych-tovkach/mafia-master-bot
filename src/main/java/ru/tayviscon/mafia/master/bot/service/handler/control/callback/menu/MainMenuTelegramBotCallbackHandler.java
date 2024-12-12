package ru.tayviscon.mafia.master.bot.service.handler.control.callback.menu;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tayviscon.mafia.master.bot.service.handler.control.callback.TelegramBotCallbackHandler;
import ru.tayviscon.mafia.master.bot.service.ui.form.MainMenuFormTelegramBotUiComponent;

@Component
@RequiredArgsConstructor
public class MainMenuTelegramBotCallbackHandler implements TelegramBotCallbackHandler {

    public static final String NAME = "/main-menu";
    private final MainMenuFormTelegramBotUiComponent mainMenuFormTelegramBotUiComponent;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    @SneakyThrows
    public void handle(Update update, TelegramLongPollingBot telegramBot) {
        var telegramUserId = update.getCallbackQuery().getFrom().getId();
        var editMessageText = new EditMessageText();
        editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId());
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageText.setText(mainMenuFormTelegramBotUiComponent.getMessage());
        editMessageText.setReplyMarkup(mainMenuFormTelegramBotUiComponent.getKeyboard(telegramUserId));
        editMessageText.setParseMode(ParseMode.MARKDOWN);
        telegramBot.execute(editMessageText);
    }
}
