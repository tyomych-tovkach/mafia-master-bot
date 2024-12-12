package ru.tayviscon.mafia.master.bot.service.ui.form;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.tayviscon.mafia.master.bot.service.ui.button.PreviousStageButtonTelegramBotUiComponent;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressFormTelegramBotUiComponent {

    private final PreviousStageButtonTelegramBotUiComponent previousStageButtonUiComponent;

    @Value("${mafia.address.physical}")
    private String physicalAddress;
    @Value("${mafia.address.yandex-map-link}")
    private String yandexMapLink;

    public String getMessage() {
        return """
        Мы находимся по адресу: [%s](%s) ✅
        """.formatted(physicalAddress, yandexMapLink);
    }

    public InlineKeyboardMarkup getKeyboard() {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(List.of(
                List.of(previousStageButtonUiComponent.getButton("/main-menu"))
            )
        );
        return inlineKeyboardMarkup;
    }

}
