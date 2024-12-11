package ru.tayviscon.mafia.master.bot.service.handler.control.callback.game.session;

import static ru.tayviscon.mafia.master.bot.domain.query.filter.AdvancedFieldFilter.eq;
import static ru.tayviscon.mafia.master.bot.domain.query.filter.AdvancedFieldFilter.notIn;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tayviscon.mafia.master.bot.domain.dto.mafia.game.session.participant.MafiaGameSessionParticipantMinimalMeritInfoFilter;
import ru.tayviscon.mafia.master.bot.domain.entity.mafia.game.session.participant.MafiaGameSessionParticipantEntity;
import ru.tayviscon.mafia.master.bot.domain.enums.MafiaGameSessionParticipantStatus;
import ru.tayviscon.mafia.master.bot.domain.query.pagination.Pagination;
import ru.tayviscon.mafia.master.bot.domain.query.param.FetchQueryParams;
import ru.tayviscon.mafia.master.bot.repository.game.session.MafiaGameSessionRepository;
import ru.tayviscon.mafia.master.bot.repository.game.session.participant.MafiaGameSessionParticipantRepository;
import ru.tayviscon.mafia.master.bot.repository.mafia.user.MafiaUserRepository;
import ru.tayviscon.mafia.master.bot.service.handler.control.callback.TelegramBotCallbackHandler;
import ru.tayviscon.mafia.master.bot.util.ValueUtils;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class RegisterGameSessionTelegramBotCallbackHandler implements TelegramBotCallbackHandler {

    private static final String NAME = "reg-in-game";
    private static final String ID_REQUIRED_PARAM_KEY = "id";
    private final MafiaGameSessionParticipantRepository mafiaGameSessionParticipantRepository;
    private final ObjectMapper objectMapper;
    private final MafiaUserRepository mafiaUserRepository;
    private final MafiaGameSessionRepository mafiaGameSessionRepository;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    @SneakyThrows
    @Transactional
    public void handle(Update update, TelegramLongPollingBot telegramBot) {

        String callbackData = update.getCallbackQuery().getData();
        var queryParams = UriComponentsBuilder
            .fromUriString(callbackData)
            .build().getQueryParams().toSingleValueMap();
        String gameSessionId = queryParams.get(ID_REQUIRED_PARAM_KEY);

        if (gameSessionId == null || !ValueUtils.isValidUuid(gameSessionId)) {
            log.error("Callback '{}' does not contains required attributes: [id]", callbackData);
            return;
        }

        var telegramUserId = update.getCallbackQuery().getFrom().getId();
        var participantFilter = new MafiaGameSessionParticipantMinimalMeritInfoFilter();
        participantFilter.setTelegramUserId(eq(telegramUserId));
        participantFilter.setGameSessionId(eq(UUID.fromString(gameSessionId)));
        participantFilter.setParticipantStatus(notIn(List.of(MafiaGameSessionParticipantStatus.REGISTERED, MafiaGameSessionParticipantStatus.CONFIRMED)));
        var participantParams = new FetchQueryParams<MafiaGameSessionParticipantMinimalMeritInfoFilter>();
        participantParams.setFilter(participantFilter);
        participantParams.setPagination(Pagination.getOneRow());
        String jsonParams = objectMapper.writeValueAsString(participantParams);
        var participantsInfo = mafiaGameSessionParticipantRepository.searchParticipantsMinimalMeritInfo(jsonParams);
        MafiaGameSessionParticipantEntity mafiaGameSessionParticipant;
        if (participantsInfo.isEmpty()) {
             mafiaGameSessionParticipant = new MafiaGameSessionParticipantEntity();
             mafiaGameSessionParticipant.setStatus(MafiaGameSessionParticipantStatus.REGISTERED);
             mafiaGameSessionParticipant.setMafia_user(
                 mafiaUserRepository.findByTelegramUserId(telegramUserId).get()
             );
             mafiaGameSessionParticipant.setMafiaGameSession(
                 mafiaGameSessionRepository.findById(participantsInfo.getFirst().getGameSessionId()).get()
             );
        } else {
            mafiaGameSessionParticipant = mafiaGameSessionParticipantRepository.findById(participantsInfo.getFirst().getId()).get();
            mafiaGameSessionParticipant.setStatus(MafiaGameSessionParticipantStatus.REGISTERED);
        }
        mafiaGameSessionParticipantRepository.save(mafiaGameSessionParticipant);

        var editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(update.getCallbackQuery().getMessage().getChatId());
        editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageReplyMarkup.setReplyMarkup(null);
        telegramBot.execute(editMessageReplyMarkup);

        var sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setText("Вы успешно записаны на игру!)");
        telegramBot.execute(sendMessage);

        var answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        telegramBot.execute(answerCallbackQuery);
    }
}
