package ru.tayviscon.mafia.master.bot.service.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tayviscon.mafia.master.bot.converter.MafiaUserConverter;
import ru.tayviscon.mafia.master.bot.repository.mafia.user.MafiaUserRepository;

@Component
@RequiredArgsConstructor
public class TelegramUserConsumer implements TelegramUpdateConsumer {

    private final MafiaUserRepository mafiaUserRepository;
    private final MafiaUserConverter mafiaUserConverter;

    @Override
    @Transactional
    public void accept(Update update) {
        var telegramUser = update.getMessage().getFrom();
        mafiaUserRepository.findByTelegramUserId(telegramUser.getId())
            .ifPresentOrElse(
                mafiaUser -> {
                    mafiaUserConverter.enrichFromTelegramUser(mafiaUser, telegramUser);
                    mafiaUserRepository.save(mafiaUser);
                },
                () -> {
                    var mafiaUser = mafiaUserConverter.createFromTelegramUser(telegramUser);
                    mafiaUserRepository.save(mafiaUser);
                }
            );
    }
}
