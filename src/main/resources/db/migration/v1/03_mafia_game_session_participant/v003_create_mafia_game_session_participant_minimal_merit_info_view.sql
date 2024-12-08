--liquibase formatted sql
--changeSet runAlways:true runOnChange:true splitStatements:false

DROP VIEW IF EXISTS mafia_game_session_participant_minimal_merit_info_view CASCADE;
CREATE VIEW mafia_game_session_participant_minimal_merit_info_view AS
SELECT
    mgsp.id as id,
    mu.id as user_id,
    mgs.id as game_session_id,
    mu.telegram_user_id as telegram_user_id,
    mu.surname as surname,
    mu.name as name,
    mu.username as username,
    (SELECT COUNT(*) FROM mafia_game_session_participant mgsp_sub where mgsp_sub.mafia_user_id = mu.id and mgsp_sub.status = 'CONFIRMED') as attended_games_count,
    mgsp.status
FROM mafia_game_session_participant mgsp
         LEFT JOIN mafia_game_session mgs ON mgsp.mafia_game_session_id = mgs.id
         LEFT JOIN mafia_user mu ON mgsp.mafia_user_id = mu.id;