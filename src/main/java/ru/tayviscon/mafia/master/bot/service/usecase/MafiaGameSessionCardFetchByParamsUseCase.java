package ru.tayviscon.mafia.master.bot.service.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.tayviscon.mafia.master.bot.converter.MafiaGameSessionConverter;
import ru.tayviscon.mafia.master.bot.domain.dto.mafia.game.session.MafiaGameSessionCardFilter;
import ru.tayviscon.mafia.master.bot.domain.dto.mafia.game.session.MafiaGameSessionCardTo;
import ru.tayviscon.mafia.master.bot.domain.query.param.FetchQueryParams;
import ru.tayviscon.mafia.master.bot.repository.game.session.MafiaGameSessionRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MafiaGameSessionCardFetchByParamsUseCase {

    private final ObjectMapper objectMapper;
    private final MafiaGameSessionRepository mafiaGameSessionRepository;
    private final MafiaGameSessionConverter mafiaGameSessionConverter;

    @SneakyThrows
    @Transactional(readOnly = true)
    public List<MafiaGameSessionCardTo> exec(FetchQueryParams<MafiaGameSessionCardFilter> queryParams) {
        String jsonParams = objectMapper.writeValueAsString(queryParams);
        return mafiaGameSessionRepository.searchCardProjections(jsonParams).stream()
            .map(mafiaGameSessionConverter::createFromProjection)
            .toList();
    }

}
