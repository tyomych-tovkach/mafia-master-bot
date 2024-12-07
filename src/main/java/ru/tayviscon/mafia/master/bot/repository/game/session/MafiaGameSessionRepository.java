package ru.tayviscon.mafia.master.bot.repository.game.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session.MafiaGameSessionEntity;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session.UpcomingMafiaGameSessionProjection;

import java.util.List;
import java.util.UUID;

public interface MafiaGameSessionRepository extends JpaRepository<MafiaGameSessionEntity, UUID> {

    @Query(
        nativeQuery = true,
        value = """
        SELECT
            mgs.id,
            mgs.started_at,
            mgs.ended_at
        FROM mafia_game_session mgs ORDER BY started_at;
        """
    )
    List<UpcomingMafiaGameSessionProjection> findUpcoming();

}
