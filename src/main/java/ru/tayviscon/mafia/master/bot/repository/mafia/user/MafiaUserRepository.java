package ru.tayviscon.mafia.master.bot.repository.mafia.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.user.MafiaUserEntity;

import java.util.Optional;
import java.util.UUID;

public interface MafiaUserRepository extends JpaRepository<MafiaUserEntity, UUID> {

    Optional<MafiaUserEntity> findByTelegramUserId(Long telegramUserId);

}
