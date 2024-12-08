--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

DROP FUNCTION IF EXISTS define_type_casting_by_value CASCADE;
CREATE FUNCTION define_type_casting_by_value(value varchar)
    RETURNS varchar
    LANGUAGE plpgsql
AS
$$
DECLARE

    uuid_regexp       varchar;
    bool_regexp       varchar;
    number_regexp     varchar;
    date_regexp       varchar;
    array_date_regexp varchar;
    datetime_regexp   varchar;
    boolean_regexp    varchar;

BEGIN

    uuid_regexp := '^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$';
    bool_regexp := '^(true|false)$';
    number_regexp := '^-?[0-9]\d*(\.\d+)?$';
    date_regexp := '^[0-9]{4}-[0-9]{2}-[0-9]{2}$';
    array_date_regexp := '^\[[0-9]{4},[0-9]{1,2},[0-9]{1,2}\]$';
    datetime_regexp := '^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}Z$';
    boolean_regexp := '^(true|false)$';

    CASE
        WHEN value IS NULL THEN RETURN '';
        WHEN value ~ uuid_regexp THEN RETURN '::uuid';
        WHEN value ~ bool_regexp THEN RETURN '::bool';
        WHEN value ~ number_regexp THEN RETURN '::numeric';
        WHEN value ~ date_regexp THEN RETURN '::date';
        WHEN value ~ datetime_regexp THEN RETURN '::timestamp';
        WHEN LOWER(value) ~ boolean_regexp THEN RETURN '::boolean';
        WHEN value ~ array_date_regexp THEN RETURN '';
        ELSE RETURN '::varchar';
        END CASE;

END;
$$