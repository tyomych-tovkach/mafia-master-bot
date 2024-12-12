package ru.tayviscon.mafia.master.bot.service.ui.button;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class ContactButtonTelegramBotUiComponent {

    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
            .text("\uD83D\uDCE8 Контакты")
            .callbackData("/contacts")
            .build();
    }

}
