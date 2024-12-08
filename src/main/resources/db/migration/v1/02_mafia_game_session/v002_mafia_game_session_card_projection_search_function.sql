--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

DROP FUNCTION IF EXISTS mafia_game_session_card_projection_search_function CASCADE;
CREATE FUNCTION mafia_game_session_card_projection_search_function(
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
            mgs.id,
            mgs.started_at,
            mgs.ended_at,
            mgs.min_players_count,
            mgs.max_players_count,
            mgs.stage
        ';
    END IF;
    stmt := stmt || ' from mafia_game_session_card_projection_view mgs ';
    filter := 'filter';
    stmt := stmt || ' where 1 = 1 ';

    stmt := stmt || compute_advanced_filter_condition(params, filter, 'id', 'mgs.id');
    stmt := stmt || compute_advanced_filter_condition(params, filter, 'stage', 'mgs.stage');
    return stmt;

END
$$;