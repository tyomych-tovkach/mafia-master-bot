--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

DROP FUNCTION IF EXISTS advanced_filter_condition CASCADE;
CREATE FUNCTION advanced_filter_condition(
    field_name character varying,
    type varchar,
    single_value varchar,
    multiple_value varchar
)
    RETURNS character varying
    LANGUAGE plpgsql
AS
$$
DECLARE
    final_field_name                varchar;
    final_single_value              varchar;
    in_condition                    text;
    should_replace_json_elem        bool;
    value_type_casting              varchar;
    final_field_name_with_casting   varchar;
    final_single_value_with_casting varchar;
BEGIN

    IF single_value IS NOT NULL THEN
        value_type_casting = define_type_casting_by_value(single_value);
    ELSEIF (multiple_value IS NOT NULL AND JSON_ARRAY_LENGTH(multiple_value::json) > 0) THEN
        value_type_casting = define_type_casting_by_value(TRIM(BOTH '"' FROM
                                                               CAST(json_array_element(multiple_value::json, 0) AS varchar)));
    ELSE
        value_type_casting = '';
    END IF;


    IF single_value IS NOT NULL AND value_type_casting = '::varchar' THEN
        final_field_name := 'make_string_suitable_for_fuzzy_search(cast(' || field_name ||
                            ' as varchar))';
        final_single_value :=
                QUOTE_LITERAL(make_string_suitable_for_fuzzy_search(single_value));
    ELSEIF (multiple_value IS NOT NULL AND JSON_ARRAY_LENGTH(multiple_value::json) > 0 AND
            value_type_casting = '::varchar') THEN
        RAISE NOTICE 'condition_mul: %', multiple_value;
        final_field_name := 'make_string_suitable_for_fuzzy_search(cast(' || field_name ||
                            ' as varchar))';
        should_replace_json_elem := TRUE;
    ELSE
        should_replace_json_elem := FALSE;
        final_field_name := field_name;
        final_single_value := QUOTE_LITERAL(single_value);
    END IF;

    final_field_name_with_casting := final_field_name || value_type_casting;
    final_single_value_with_casting := final_single_value || value_type_casting;

    IF (type = 'IS_NULL') THEN
        RETURN ' and ' || final_field_name || ' is null';
    ELSEIF (type = 'IS_NOT_NULL') THEN
        RETURN ' and ' || final_field_name || ' is not null';
    END IF;

    IF single_value IS NOT NULL OR multiple_value IS NOT NULL THEN
        IF (type = 'EQUALS') THEN
            RETURN ' and ' || final_field_name || ' = ' || final_single_value;
        ELSEIF (type = 'EQUALS_LENGTH') THEN
            RETURN ' and LENGTH(' || final_field_name || ') = ' || QUOTE_LITERAL(single_value);
        ELSEIF (type = 'NOT_EQUALS') THEN
            RETURN ' and ' || final_field_name || ' != ' || final_single_value;
        ELSEIF (type = 'EQUALS_IGNORE_CASE') THEN
            RETURN ' and lower(' || final_field_name || '::text) = ' || LOWER(final_single_value::text);
        ELSEIF (type = 'START_WITH') THEN
            RETURN ' and lower( ' || final_field_name || '::text) ilike ''' || LOWER(single_value::text) || '%''';
        ELSEIF (type = 'CONTAINS') THEN
            RETURN ' and lower(' || final_field_name || '::text) like ''%' ||
                   make_string_suitable_for_fuzzy_search(single_value) || '%'' ';
        ELSEIF (type = 'EQUALS_OR_NULL') THEN
            RETURN ' and (' || final_field_name || ' = ' ||
                   final_single_value || ' OR ' || final_field_name || ' IS NULL) ';
        ELSEIF (type = 'GREATER') THEN
            RETURN ' and ' || final_field_name_with_casting || ' > ' || final_single_value_with_casting;
        ELSEIF (type = 'GREATER_OR_EQUALS') THEN
            RETURN ' and ' || final_field_name_with_casting || ' >= ' || final_single_value_with_casting;
        ELSEIF (type = 'LOWER') THEN
            RETURN ' and ' || final_field_name_with_casting || ' < ' || final_single_value_with_casting;
        ELSEIF (type = 'LOWER_OR_EQUALS') THEN
            RETURN ' and ' || final_field_name_with_casting || ' <= ' || final_single_value_with_casting;
        ELSEIF (type = 'GREATER_OR_EQUALS_OR_NULL') THEN
            RETURN ' and ' || ' (' || final_field_name_with_casting || ' >= ' || final_single_value_with_casting ||
                   ' or ' || final_field_name ||
                   ' is NULL)';
        ELSEIF (type = 'LOWER_OR_EQUALS_OR_NULL') THEN
            RETURN ' and ' || ' (' || final_field_name_with_casting || ' <= ' || final_single_value_with_casting ||
                   ' or ' || final_field_name ||
                   ' is NULL)';
        ELSEIF (type = 'GREATER_OR_NULL') THEN
            RETURN ' and ' || ' (' || final_field_name_with_casting || ' > ' || final_single_value_with_casting ||
                   ' or ' || final_field_name ||
                   ' is NULL)';
        ELSEIF (type = 'LOWER_OR_NULL') THEN
            RETURN ' and ' || ' (' || final_field_name_with_casting || ' < ' || final_single_value_with_casting ||
                   ' or ' || final_field_name ||
                   ' is NULL)';
        ELSEIF (type = 'IN') THEN
            IF multiple_value IS NULL OR JSON_ARRAY_LENGTH(multiple_value::json) = 0 THEN
                RETURN '';
            END IF;
            WITH data AS (SELECT multiple_value::json js)
            SELECT '''' || CASE
                               WHEN should_replace_json_elem THEN STRING_AGG(
                                       make_string_suitable_for_fuzzy_search(elem), ''',''')
                               ELSE STRING_AGG(elem, ''',''') END || ''''
            INTO in_condition
            FROM data,
                 JSON_ARRAY_ELEMENTS_TEXT(js) elem;
            RETURN ' and ' || final_field_name_with_casting || ' in (' || in_condition || ')';
        ELSEIF (type = 'NOT_IN') THEN
            IF multiple_value IS NULL OR JSON_ARRAY_LENGTH(multiple_value::json) = 0 THEN
                RETURN '';
            END IF;

            WITH data AS (SELECT multiple_value::json js)
            SELECT '''' || CASE
                               WHEN should_replace_json_elem THEN STRING_AGG(
                                       make_string_suitable_for_fuzzy_search(elem), ''',''')
                               ELSE STRING_AGG(elem, ''',''') END || ''''
            INTO in_condition
            FROM data,
                 JSON_ARRAY_ELEMENTS_TEXT(js) elem;

            RETURN ' and ' || final_field_name_with_casting || ' not in (' || in_condition || ')';
        ELSEIF (type = 'BETWEEN') THEN
            IF multiple_value IS NULL OR JSON_ARRAY_LENGTH(multiple_value::json) != 2 THEN
                RETURN '';
            END IF;

            WITH data AS (SELECT multiple_value::json js)
            SELECT '''' || STRING_AGG(elem, ''' and ''') || ''''
            INTO in_condition
            FROM data,
                 JSON_ARRAY_ELEMENTS_TEXT(js) elem;

            RETURN ' and (' || final_field_name_with_casting || ' between ' || in_condition || ')';
        ELSE
            RAISE 'invalid filter type: %', type;
        END IF;
    ELSE
        RAISE 'singleValue and multipleValue are both null';
    END IF;

END;
$$;
