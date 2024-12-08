package ru.tayviscon.mafia.master.bot.converter;

import org.springframework.stereotype.Component;
import ru.tayviscon.mafia.master.bot.domain.dto.mafia.game.session.MafiaGameSessionCardTo;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session.MafiaGameSessionCardProjection;
import ru.tayviscon.mafia.master.bot.util.DateTimeUtils;

@Component
public class MafiaGameSessionConverter {

    public MafiaGameSessionCardTo createFromProjection(MafiaGameSessionCardProjection cardProjection) {
        var mafiaGameSessionCard = new MafiaGameSessionCardTo();
        mafiaGameSessionCard.setId(cardProjection.getId());
        mafiaGameSessionCard.setGameSessionDateTimeRange(
            DateTimeUtils.getGameSessionDateTimeRange(cardProjection.getStartedAt(), cardProjection.getEndedAt())
        );
        return mafiaGameSessionCard;
    }

}
