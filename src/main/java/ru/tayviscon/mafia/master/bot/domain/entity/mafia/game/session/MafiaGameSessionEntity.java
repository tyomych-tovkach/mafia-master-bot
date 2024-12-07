package ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.tayviscon.mafia.master.bot.domain.entity.DomainObjectEntity;
import ru.tayviscon.mafia.master.bot.domain.enums.MafiaGameSessionStage;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@Table(name = "mafia_game_session")
public class MafiaGameSessionEntity extends DomainObjectEntity {

    private Integer minPlayersCount;

    private Integer maxPlayersCount;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @Enumerated(EnumType.STRING)
    private MafiaGameSessionStage stage;

}
