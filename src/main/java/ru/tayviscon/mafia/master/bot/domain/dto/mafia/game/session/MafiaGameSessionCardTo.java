package ru.tayviscon.mafia.master.bot.domain.dto.mafia.game.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tayviscon.mafia.master.bot.domain.dto.CommonTo;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session.participant.MafiaGameSessionParticipantMinimalMeritInfo;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MafiaGameSessionCardTo implements CommonTo {

    private UUID id;

    private String gameSessionDateTimeRange;

    private List<MafiaGameSessionParticipantMinimalMeritInfo> activeParticipantsMinimalMeritInfo;

}
