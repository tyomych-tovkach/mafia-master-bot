CREATE TABLE IF NOT EXISTS mafia_user
(
    id uuid NOT NULL
        CONSTRAINT mafia_user_pkey PRIMARY KEY,
    telegram_user_id bigserial NOT NULL,
    surname varchar,
    name varchar,
    patronymic varchar,
    username varchar,

    created_at timestamp WITHOUT TIME ZONE,
    updated_at timestamp WITHOUT TIME ZONE,
    version bigint
);

COMMENT ON TABLE mafia_user IS 'Пользователь бота "MafiaMasterBot"';
COMMENT ON COLUMN mafia_user.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN mafia_user.telegram_user_id IS 'Внешний идентификатор пользователя';
COMMENT ON COLUMN mafia_user.surname IS 'Фамилия';
COMMENT ON COLUMN mafia_user.name IS 'Имя';
COMMENT ON COLUMN mafia_user.patronymic IS 'Отчество';
COMMENT ON COLUMN mafia_user.username IS 'Имя пользователя';

COMMENT ON COLUMN mafia_user.created_at IS 'Время создание записи';
COMMENT ON COLUMN mafia_user.updated_at IS 'Время обновления записи';
COMMENT ON COLUMN mafia_user.version IS 'Версия записи';