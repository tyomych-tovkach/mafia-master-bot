package ru.tayviscon.mafia.master.bot.service.ui.button;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class UpcomingGamesButtonTelegramBotUiComponent {

    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
            .text("Предстоящие игры \uD83D\uDD1C")
            .callbackData("/game-session/upcoming")
            .build();
    }

}
