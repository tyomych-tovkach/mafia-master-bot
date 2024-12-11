package ru.tayviscon.mafia.master.bot.service.ui;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class MainMenuTelegramBotUseCase {

    @SneakyThrows
    public void exec(Update update, TelegramLongPollingBot telegramBot) {

        var telegramUserId = update.getMessage().getFrom().getId();

        var sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("""
        Привет!👋
        
        Я твой личный помощник по игре в мафию!
        
        Здесь ты можешь все 🙌:
        
        • *Забронировать игру* 💶
        • *Собрать команду* 👥
        • *Просмотреть свою статистику* 📈
        
        Если у вас возникнут вопросы, не стесняйтесь обращаться к нам!
        """);

        var profileKeyboardButton = new InlineKeyboardButton();
        profileKeyboardButton.setText("\uD83D\uDC64 Профиль");
        profileKeyboardButton.setCallbackData(String.format("/profile?telegramUserId=%s", telegramUserId));

        var faqKeyboardButton = new InlineKeyboardButton();
        faqKeyboardButton.setText("❔ FAQ");
        faqKeyboardButton.setCallbackData("/faq");

        var contactsKeyboardButton = new InlineKeyboardButton();
        contactsKeyboardButton.setText("\uD83D\uDCE8 Контакты");
        contactsKeyboardButton.setCallbackData("/contacts");

        var addressKeyButton = new InlineKeyboardButton();
        addressKeyButton.setText("\uD83D\uDDFA Адрес");
        addressKeyButton.setCallbackData("/address");

        var showUpcomingGameSessionsKeyboardButton = new InlineKeyboardButton();
        showUpcomingGameSessionsKeyboardButton.setText("Предстоящие игры \uD83D\uDD1C");
        showUpcomingGameSessionsKeyboardButton.setCallbackData("/game-session/upcoming");

        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(
            List.of(
                List.of(profileKeyboardButton),
                List.of(showUpcomingGameSessionsKeyboardButton),
                List.of(addressKeyButton, faqKeyboardButton, contactsKeyboardButton)
            )

        );

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        telegramBot.execute(sendMessage);
    }
}
