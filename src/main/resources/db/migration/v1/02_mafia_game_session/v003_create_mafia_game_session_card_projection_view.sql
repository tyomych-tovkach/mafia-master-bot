--liquibase formatted sql
--changeSet runAlways:true runOnChange:true splitStatements:false

DROP VIEW IF EXISTS mafia_game_session_card_projection_view CASCADE;
CREATE VIEW mafia_game_session_card_projection_view AS
SELECT
    mgs.id,
    mgs.started_at,
    mgs.ended_at,
    mgs.min_players_count,
    mgs.max_players_count,
    mgs.stage
FROM mafia_game_session mgs;