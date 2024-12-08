package ru.tayviscon.mafia.master.bot.service.usecase;

import static ru.tayviscon.mafia.master.bot.domain.query.filter.AdvancedFieldFilter.eq;
import static ru.tayviscon.mafia.master.bot.domain.query.filter.AdvancedFieldFilter.notIn;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.tayviscon.mafia.master.bot.converter.MafiaGameSessionConverter;
import ru.tayviscon.mafia.master.bot.domain.dto.mafia.game.session.MafiaGameSessionCardFilter;
import ru.tayviscon.mafia.master.bot.domain.dto.mafia.game.session.MafiaGameSessionCardTo;
import ru.tayviscon.mafia.master.bot.domain.dto.mafia.game.session.participant.MafiaGameSessionParticipantMinimalMeritInfoFilter;
import ru.tayviscon.mafia.master.bot.domain.enums.MafiaGameSessionParticipantStatus;
import ru.tayviscon.mafia.master.bot.domain.query.pagination.Pagination;
import ru.tayviscon.mafia.master.bot.domain.query.param.FetchQueryParams;
import ru.tayviscon.mafia.master.bot.repository.game.session.MafiaGameSessionRepository;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MafiaGameSessionCardFetchUseCase {

    private final ObjectMapper objectMapper;
    private final MafiaGameSessionRepository gameSessionRepository;
    private final MafiaGameSessionConverter gameSessionConverter;
    private final MafiaGameSessionParticipantMinimalMeritInfoFetchByParamsUseCase
        gameSessionParticipantMinimalMeritInfoFetchByParamsUseCase;

    @SneakyThrows
    @Transactional(readOnly = true)
    public MafiaGameSessionCardTo exec(UUID mafiaGameSessionId) {

        var filter = new MafiaGameSessionCardFilter();
        filter.setId(eq(mafiaGameSessionId));
        var params = new FetchQueryParams<MafiaGameSessionCardFilter>();
        params.setFilter(filter);
        params.setPagination(Pagination.getOneRow());

        String jsonParams = objectMapper.writeValueAsString(params);
        var gameSessionCardProjections = gameSessionRepository.searchCardProjections(jsonParams);

        if (gameSessionCardProjections.isEmpty()) {

        }

        var gameSessionCard = gameSessionConverter.createFromProjection(gameSessionCardProjections.getFirst());

        var gameSessionParticipantFilter = new MafiaGameSessionParticipantMinimalMeritInfoFilter();
        gameSessionParticipantFilter.setGameSessionId(eq(mafiaGameSessionId));
        gameSessionParticipantFilter.setParticipantStatus(notIn(List.of(MafiaGameSessionParticipantStatus.CANCELLED)));
        var gameSessionParticipantParams = new FetchQueryParams<MafiaGameSessionParticipantMinimalMeritInfoFilter>();
        gameSessionParticipantParams.setFilter(gameSessionParticipantFilter);
        gameSessionParticipantParams.setPagination(Pagination.getAllRows());

        var participantMinimalMeritInfos = gameSessionParticipantMinimalMeritInfoFetchByParamsUseCase
            .exec(gameSessionParticipantParams);
        gameSessionCard.setActiveParticipantsMinimalMeritInfo(participantMinimalMeritInfos);

        return gameSessionCard;

    }

}
