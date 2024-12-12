package ru.tayviscon.mafia.master.bot.service.ui.button;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class PreviousStageButtonTelegramBotUiComponent {

    public InlineKeyboardButton getButton(String callbackData) {
        return InlineKeyboardButton.builder()
            .text("◀ Назад")
            .callbackData(callbackData)
            .build();
    }

}
