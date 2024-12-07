CREATE TABLE IF NOT EXISTS mafia_game_session
(
    id uuid NOT NULL
        CONSTRAINT mafia_game_session_pkey PRIMARY KEY,
    min_players_count int DEFAULT 8 NOT NULL,
    max_players_count int DEFAULT 12 NOT NULL,
    started_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    ended_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    stage varchar,

    created_at timestamp WITHOUT TIME ZONE,
    updated_at timeStamp WITHOUT TIME ZONE,
    version bigint
);

COMMENT ON TABLE mafia_game_session IS 'Сеанс игры в "Мафию"';
COMMENT ON COLUMN mafia_game_session.id IS 'Идентификатор сеанса игры в "Мафию"';
COMMENT ON COLUMN mafia_game_session.min_players_count IS 'Минимальное количество игроков';
COMMENT ON COLUMN mafia_game_session.max_players_count IS 'Максимальное количество игроков';
COMMENT ON COLUMN mafia_game_session.started_at IS 'Дата и время начала сеанса игры в "Мафию"';
COMMENT ON COLUMN mafia_game_session.ended_at IS 'Дата и время окончания сеанса игры в "Мафию"';
COMMENT ON COLUMN mafia_game_session.stage IS 'Статус сеанса игры';

COMMENT ON COLUMN mafia_game_session.created_at IS 'Время создания записи';
COMMENT ON COLUMN mafia_game_session.updated_at IS 'Время обновления записи';
COMMENT ON COLUMN mafia_game_session.version IS 'Версия записи';