package ru.tayviscon.mafia.master.bot.service.ui.button;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class FaqButtonTelegramBotUiComponent {

    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
            .text("❔ FAQ")
            .callbackData("/faq")
            .build();
    }

}
