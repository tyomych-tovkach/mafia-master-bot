package ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session.participant;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.tayviscon.mafia.master.bot.domain.entity.DomainObjectEntity;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session.MafiaGameSessionEntity;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.user.MafiaUserEntity;
import ru.tayviscon.mafia.master.bot.domain.enums.MafiaGameSessionParticipantStatus;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@Table(name = "mafia_game_session_participant")
public class MafiaGameSessionParticipantEntity extends DomainObjectEntity {

    @ManyToOne
    @JoinColumn(name = "mafia_game_session_id")
    private MafiaGameSessionEntity mafiaGameSession;

    @ManyToOne
    @JoinColumn(name = "mafia_user_id")
    private MafiaUserEntity mafia_user;

    @Enumerated(EnumType.STRING)
    private MafiaGameSessionParticipantStatus status;

}
