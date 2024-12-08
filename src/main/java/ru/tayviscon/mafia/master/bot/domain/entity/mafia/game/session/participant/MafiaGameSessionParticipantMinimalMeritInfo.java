package ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session.participant;

import ru.tayviscon.mafia.master.bot.domain.enums.MafiaGameSessionParticipantStatus;

import java.util.UUID;

public interface MafiaGameSessionParticipantMinimalMeritInfo {

    UUID getId();
    UUID getUserId();
    UUID getGameSessionId();
    Long getTelegramUserId();
    String getSurname();
    String getName();
    String getUsername();
    Integer getAttendedGamesCount();
    MafiaGameSessionParticipantStatus getStatus();

}
