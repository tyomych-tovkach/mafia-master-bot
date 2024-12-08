package ru.tayviscon.mafia.master.bot.service.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.tayviscon.mafia.master.bot.domain.dto.mafia.game.session.participant.MafiaGameSessionParticipantMinimalMeritInfoFilter;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session.participant.MafiaGameSessionParticipantMinimalMeritInfo;
import ru.tayviscon.mafia.master.bot.domain.query.param.FetchQueryParams;
import ru.tayviscon.mafia.master.bot.repository.game.session.participant.MafiaGameSessionParticipantRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MafiaGameSessionParticipantMinimalMeritInfoFetchByParamsUseCase {

    private final ObjectMapper objectMapper;
    private final MafiaGameSessionParticipantRepository gameSessionParticipantRepository;

    @SneakyThrows
    @Transactional(readOnly = true)
    List<MafiaGameSessionParticipantMinimalMeritInfo> exec(
        FetchQueryParams<MafiaGameSessionParticipantMinimalMeritInfoFilter> queryParams
    ) {
        String jsonParams = objectMapper.writeValueAsString(queryParams);
        return gameSessionParticipantRepository.searchParticipantsMinimalMeritInfo(jsonParams);
    }

}
