package ru.tayviscon.mafia.master.bot.service.ui.button;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class ProfileButtonTelegramBotUiComponent {

    public InlineKeyboardButton getButton(Long telegramUserId) {
        return InlineKeyboardButton.builder()
            .text("\uD83D\uDC64 Профиль")
            .callbackData(String.format("/profile?telegramUserId=%s", telegramUserId))
            .build();
    }

}
