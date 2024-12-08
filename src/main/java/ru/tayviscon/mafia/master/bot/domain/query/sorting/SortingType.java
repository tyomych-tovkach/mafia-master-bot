package ru.tayviscon.mafia.master.bot.domain.query.sorting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortingType {

    ASC_NULLS_FIRST(SortingDirectionLiteral.ASC, SortingNullHandlingLiteral.NULLS_FIRST),
    ASC_NULLS_LAST(SortingDirectionLiteral.ASC, SortingNullHandlingLiteral.NULLS_LAST),
    DESC_NULL_LAST(SortingDirectionLiteral.DESC, SortingNullHandlingLiteral.NULLS_LAST),
    DESC_NULL_FIRST(SortingDirectionLiteral.DESC, SortingNullHandlingLiteral.NULLS_FIRST);

    private final SortingDirectionLiteral directionLiteral;
    private final SortingNullHandlingLiteral nullHandlingLiteral;

}
