package ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UpcomingMafiaGameSessionProjection {

    UUID getId();
    LocalDateTime getStartedAt();
    LocalDateTime getEndedAt();


}
