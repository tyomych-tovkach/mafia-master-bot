package ru.tayviscon.mafia.master.bot.converter;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.user.MafiaUserEntity;

@Component
public class MafiaUserConverter {

    public void enrichFromTelegramUser(MafiaUserEntity mafiaUser, User user) {
        mafiaUser.setTelegramUserId(user.getId());
        mafiaUser.setUsername(user.getUserName());
        mafiaUser.setSurname(user.getLastName());
        mafiaUser.setName(user.getFirstName());
    }

    public MafiaUserEntity createFromTelegramUser(User user) {
        var mafiaUser = new MafiaUserEntity();
        mafiaUser.setTelegramUserId(user.getId());
        mafiaUser.setUsername(user.getUserName());
        mafiaUser.setSurname(user.getLastName());
        mafiaUser.setName(user.getFirstName());
        return mafiaUser;
    }

}
