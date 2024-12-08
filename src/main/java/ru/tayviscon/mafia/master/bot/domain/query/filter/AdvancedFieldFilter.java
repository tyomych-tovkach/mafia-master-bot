package ru.tayviscon.mafia.master.bot.domain.query.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdvancedFieldFilter<T> {

    private AdvancedFieldFilterType type;
    private T singleValue;
    private List<T> multipleValue;

    public static <T> AdvancedFieldFilter<T> in(T value) {
        return in(List.of(value));
    }

    public static <T> AdvancedFieldFilter<T> in(T firstValue, T secondValue) {
        return in(List.of(firstValue, secondValue));
    }

    public static <T> AdvancedFieldFilter<T> in(Collection<T> values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("Filter of type IN must have at least one parameter");
        }
        var filter = new AdvancedFieldFilter<T>();
        filter.setMultipleValue(new ArrayList<>(values));
        filter.setType(AdvancedFieldFilterType.IN);
        return filter;
    }

    public static <T> AdvancedFieldFilter<T> notIn(Collection<T> values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("Filter of type NOT_IN must have at least one parameter");
        }
        var filter = new AdvancedFieldFilter<T>();
        filter.setMultipleValue(new ArrayList<>(values));
        filter.setType(AdvancedFieldFilterType.NOT_IN);
        return filter;
    }

    public static <T> AdvancedFieldFilter<T> eq(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.EQUALS);
        return filter;
    }

    public static <T> AdvancedFieldFilter<T> notEq(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.NOT_EQUALS);
        return filter;
    }

    public static AdvancedFieldFilter<Boolean> eqTrue() {
        var filter = new AdvancedFieldFilter<Boolean>();
        filter.setSingleValue(Boolean.TRUE);
        filter.setType(AdvancedFieldFilterType.EQUALS);
        return filter;
    }

    public static AdvancedFieldFilter<Boolean> eqFalse() {
        var filter = new AdvancedFieldFilter<Boolean>();
        filter.setSingleValue(Boolean.FALSE);
        filter.setType(AdvancedFieldFilterType.EQUALS);
        return filter;
    }

    public static <T> AdvancedFieldFilter<T> isNull() {
        var filter = new AdvancedFieldFilter<T>();
        filter.setType(AdvancedFieldFilterType.IS_NULL);
        return filter;
    }

    public static <T> AdvancedFieldFilter<T> isNotNull() {
        var filter = new AdvancedFieldFilter<T>();
        filter.setType(AdvancedFieldFilterType.IS_NOT_NULL);
        return filter;
    }

    public static <T> AdvancedFieldFilter<T> between(T start, T end) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setType(AdvancedFieldFilterType.BETWEEN);
        filter.setMultipleValue(List.of(start, end));
        return filter;
    }

    public static <T> AdvancedFieldFilter<T> greater(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.GREATER);
        return filter;
    }

    public static <T> AdvancedFieldFilter<T> greaterOrEquals(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.GREATER_OR_EQUALS);
        return filter;
    }

    public static <T> AdvancedFieldFilter<T> lowerOrEquals(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.LOWER_OR_EQUALS);
        return filter;
    }

    public static <T> AdvancedFieldFilter<T> lower(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.LOWER);
        return filter;
    }

    public static <T> AdvancedFieldFilter<T> contains(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.CONTAINS);
        return filter;
    }
}
