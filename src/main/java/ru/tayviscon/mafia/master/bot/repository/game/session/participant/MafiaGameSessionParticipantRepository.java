package ru.tayviscon.mafia.master.bot.repository.game.session.participant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session.participant.MafiaGameSessionParticipantEntity;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session.participant.MafiaGameSessionParticipantMinimalMeritInfo;

import java.util.List;
import java.util.UUID;

public interface MafiaGameSessionParticipantRepository extends JpaRepository<MafiaGameSessionParticipantEntity, UUID> {

    @Query(
        nativeQuery = true,
        value = """
        SELECT * FROM search_entities(
            'mafia_game_session_participant_minimal_merit_info_search_func',
            :params,
            TRUE,
            CAST(NULL AS mafia_game_session_participant_minimal_merit_info_view)
        );
        """
    )
    List<MafiaGameSessionParticipantMinimalMeritInfo> searchParticipantsMinimalMeritInfo(String params);

}
