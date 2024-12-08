--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

DROP FUNCTION IF EXISTS search_entities CASCADE;
CREATE FUNCTION search_entities(query_name varchar, params VARCHAR , pagination boolean, _t anyelement)
    RETURNS SETOF anyelement
    LANGUAGE plpgsql
AS
$$
DECLARE
    stmt varchar;
BEGIN

    EXECUTE FORMAT('SELECT * FROM %s($1, $2, $3)', query_name)
        INTO stmt
        USING FALSE, params::json, pagination;

    --при фильтрации с типом CONTAINS требуется заменять % -> %%, чтобы format() корректно обрабатывала запрос
    RETURN QUERY EXECUTE FORMAT(replace(stmt, '%', '%%'), PG_TYPEOF(_t));

    RETURN;
END;
$$;