--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

DROP FUNCTION IF EXISTS make_string_suitable_for_fuzzy_search CASCADE;
CREATE FUNCTION make_string_suitable_for_fuzzy_search(string_value varchar)
    RETURNS varchar
    LANGUAGE plpgsql
AS
$$
BEGIN
    -- Проверка на NULL
    IF string_value IS NULL THEN
        RETURN NULL;
    END IF;

    string_value := LOWER(string_value);

    -- Замена  'ё' и е
    string_value := REGEXP_REPLACE(string_value, 'ё', 'е', 'cg');

    -- Удаление всех символов, кроме букв и цифр
    string_value := REGEXP_REPLACE(string_value, '[^a-z0-9а-я]', '', 'cg');

    RETURN string_value;
END;
$$;