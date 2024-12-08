package ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session;

import ru.tayviscon.mafia.master.bot.domain.enums.MafiaGameSessionStage;

import java.time.LocalDateTime;
import java.util.UUID;

public interface MafiaGameSessionCardProjection {

    UUID getId();
    LocalDateTime getStartedAt();
    LocalDateTime getEndedAt();
    Integer getMinPlayersCount();
    Integer getMaxPlayersCount();
    MafiaGameSessionStage getStage();

}
