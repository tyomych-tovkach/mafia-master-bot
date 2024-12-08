package ru.tayviscon.mafia.master.bot.repository.game.session.participant;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session.participant.MafiaGameSessionParticipantEntity;

import java.util.UUID;

public interface MafiaGameSessionParticipantRepository extends JpaRepository<MafiaGameSessionParticipantEntity, UUID> {

}
