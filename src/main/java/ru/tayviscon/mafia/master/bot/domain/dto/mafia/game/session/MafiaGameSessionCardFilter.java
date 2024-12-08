package ru.tayviscon.mafia.master.bot.domain.dto.mafia.game.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.tayviscon.mafia.master.bot.domain.enums.MafiaGameSessionStage;
import ru.tayviscon.mafia.master.bot.domain.query.filter.AdvancedFieldFilter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Accessors(chain = true)
public class MafiaGameSessionCardFilter {

    private AdvancedFieldFilter<UUID> id;
    private AdvancedFieldFilter<MafiaGameSessionStage> stage;

}
