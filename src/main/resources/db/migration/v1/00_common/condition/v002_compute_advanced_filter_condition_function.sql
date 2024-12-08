--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

DROP FUNCTION IF EXISTS compute_advanced_filter_condition CASCADE;
CREATE FUNCTION compute_advanced_filter_condition(
    params json,
    filter varchar,
    field_in_filter varchar,
    field_in_db varchar
)
    RETURNS varchar
    LANGUAGE plpgsql
AS
$$
DECLARE
    operation_type varchar;
BEGIN
    IF JSON_EXTRACT_PATH_TEXT(params, filter, field_in_filter) IS NOT NULL THEN
        operation_type := JSON_EXTRACT_PATH_TEXT(params, filter, field_in_filter, 'type');
    END IF;
    IF operation_type IS NOT NULL THEN
        RETURN advanced_filter_condition(
            field_in_db,
            operation_type,
            JSON_EXTRACT_PATH_TEXT(params, filter, field_in_filter, 'singleValue'),
            JSON_EXTRACT_PATH_TEXT(params, filter, field_in_filter, 'multipleValue')
        );
    END IF;
    RETURN '';

END
$$;