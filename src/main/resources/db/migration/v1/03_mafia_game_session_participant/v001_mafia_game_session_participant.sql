CREATE TABLE IF NOT EXISTS mafia_game_session_participant
(
    id uuid NOT NULL CONSTRAINT mafia_game_session_participant_pkey PRIMARY KEY,
    mafia_game_session_id uuid NOT NULL CONSTRAINT mafia_game_session_id_fkey REFERENCES mafia_game_session(id),
    mafia_user_id uuid not null CONSTRAINT mafia_user_id_fkey REFERENCES mafia_user(id),
    status varchar,

    created_at timestamp WITHOUT TIME ZONE,
    updated_at timestamp WITHOUT TIME ZONE,
    version bigint
);

COMMENT ON TABLE mafia_game_session_participant IS 'Участник сеанса игры в "Мафию"';
COMMENT ON COLUMN mafia_game_session_participant.id IS 'Идентификатор участника сеанса игры в "Мафию"';
COMMENT ON COLUMN mafia_game_session_participant.mafia_game_session_id IS 'Идентификатор сеанса игры в "Мафию"';
COMMENT ON COLUMN mafia_game_session_participant.mafia_user_id IS 'Идентификатор пользователя';
COMMENT ON COLUMN mafia_game_session_participant.status IS 'Статус участника';

COMMENT ON COLUMN mafia_game_session_participant.created_at IS 'Время создания записи';
COMMENT ON COLUMN mafia_game_session_participant.updated_at IS 'Время обновления записи';
COMMENT ON COLUMN mafia_game_session_participant.version IS 'Время записи';