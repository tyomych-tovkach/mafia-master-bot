package ru.tayviscon.mafia.master.bot.service.handler.control.callback.address;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tayviscon.mafia.master.bot.service.handler.control.callback.TelegramBotCallbackHandler;
import ru.tayviscon.mafia.master.bot.service.ui.form.AddressFormTelegramBotUiComponent;

@Log4j2
@Component
@RequiredArgsConstructor
public class AddressTelegramBotCallbackHandler implements TelegramBotCallbackHandler {

    private static final String NAME = "/address";
    private final AddressFormTelegramBotUiComponent addressFormUiComponent;
    private final AddressFormTelegramBotUiComponent addressFormTelegramBotUiComponent;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    @SneakyThrows
    public void handle(Update update, TelegramLongPollingBot telegramBot) {
        var editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.MARKDOWN);
        editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId());
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageText.setText(addressFormTelegramBotUiComponent.getMessage());
        editMessageText.setReplyMarkup(addressFormTelegramBotUiComponent.getKeyboard());
        telegramBot.execute(editMessageText);

        var answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        telegramBot.execute(answerCallbackQuery);
    }
}
