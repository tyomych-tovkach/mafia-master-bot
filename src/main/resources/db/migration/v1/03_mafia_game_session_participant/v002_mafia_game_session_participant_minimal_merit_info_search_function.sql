--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

DROP FUNCTION IF EXISTS mafia_game_session_participant_minimal_merit_info_search_func CASCADE;
CREATE FUNCTION mafia_game_session_participant_minimal_merit_info_search_func(
    countable boolean,
    params json,
    pagination boolean
)
    RETURNS text
    LANGUAGE plpgsql
AS
$$
DECLARE
    filter varchar;
    stmt   varchar;
BEGIN
    IF countable THEN
        stmt := ' select count(*) ';
    ELSE
        stmt := '
        SELECT
            mgsp.id,
            mgsp.user_id,
            mgsp.game_session_id,
            mgsp.telegram_user_id,
            mgsp.surname,
            mgsp.name,
            mgsp.username,
            mgsp.attended_games_count,
            mgsp.status
        ';
    END IF;
    stmt := stmt || ' from mafia_game_session_participant_minimal_merit_info_view mgsp ';
    filter := 'filter';
    stmt := stmt || ' where 1 = 1 ';

    stmt := stmt || compute_advanced_filter_condition(params, filter, 'id', 'mgsp.id');
    stmt := stmt || compute_advanced_filter_condition(params, filter, 'gameSessionId', 'mgsp.game_session_id');
    stmt := stmt || compute_advanced_filter_condition(params, filter, 'participantStatus', 'mgsp.status');
    stmt := stmt || compute_advanced_filter_condition(params, filter, 'telegramUserId', 'mgsp.telegram_user_id');
    return stmt;

END
$$;