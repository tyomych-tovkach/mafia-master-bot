package ru.tayviscon.mafia.master.bot.domain.dto.mafia.game.session.participant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.tayviscon.mafia.master.bot.domain.enums.MafiaGameSessionParticipantStatus;
import ru.tayviscon.mafia.master.bot.domain.query.filter.AdvancedFieldFilter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Accessors(chain = true)
public class MafiaGameSessionParticipantMinimalMeritInfoFilter {

    private AdvancedFieldFilter<UUID> id;

    private AdvancedFieldFilter<UUID> gameSessionId;

    private AdvancedFieldFilter<Long> telegramUserId;

    private AdvancedFieldFilter<MafiaGameSessionParticipantStatus> participantStatus;

}
