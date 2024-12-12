package ru.tayviscon.mafia.master.bot.service.ui.form;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.tayviscon.mafia.master.bot.service.ui.button.AddressButtonTelegramBotUiComponent;
import ru.tayviscon.mafia.master.bot.service.ui.button.ContactButtonTelegramBotUiComponent;
import ru.tayviscon.mafia.master.bot.service.ui.button.FaqButtonTelegramBotUiComponent;
import ru.tayviscon.mafia.master.bot.service.ui.button.ProfileButtonTelegramBotUiComponent;
import ru.tayviscon.mafia.master.bot.service.ui.button.UpcomingGamesButtonTelegramBotUiComponent;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MainMenuFormTelegramBotUiComponent {

    private final ProfileButtonTelegramBotUiComponent profileButtonUiComponent;
    private final AddressButtonTelegramBotUiComponent addressButtonUiComponent;
    private final FaqButtonTelegramBotUiComponent faqButtonUiComponent;
    private final ContactButtonTelegramBotUiComponent contactButtonUiComponent;
    private final UpcomingGamesButtonTelegramBotUiComponent upcomingGamesButtonUiComponent;

    public String getMessage() {
        return """
            Привет!👋
            
            Я твой личный помощник по игре в мафию!
            
            Здесь ты можешь все 🙌:
            
            • *Забронировать игру* 💶
            • *Собрать команду* 👥
            • *Просмотреть свою статистику* 📈
            
            Если у вас возникнут вопросы, не стесняйтесь обращаться к нам!
            """;
    }

    public InlineKeyboardMarkup getKeyboard(Long telegramUserId) {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(List.of(
            List.of(profileButtonUiComponent.getButton(telegramUserId)),
            List.of(upcomingGamesButtonUiComponent.getButton()),
            List.of(
                addressButtonUiComponent.getButton(),
                faqButtonUiComponent.getButton(),
                contactButtonUiComponent.getButton()
            )
        ));
        return inlineKeyboardMarkup;
    }
}
