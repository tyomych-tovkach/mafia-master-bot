package ru.tayviscon.mafia.master.bot.repository.game.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session.MafiaGameSessionCardProjection;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session.MafiaGameSessionEntity;

import java.util.List;
import java.util.UUID;

public interface MafiaGameSessionRepository extends JpaRepository<MafiaGameSessionEntity, UUID> {

    @Query(
        nativeQuery = true,
        value = """
        SELECT * FROM search_entities(
            'mafia_game_session_card_projection_search_function',
            :params,
            TRUE,
            CAST(NULL AS mafia_game_session_card_projection_view)
        );
        """
    )
    List<MafiaGameSessionCardProjection> searchCardProjections(String params);

}
