package ru.tayviscon.mafia.master.bot.domain.query.sorting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortingNullHandlingLiteral {

    NULLS_FIRST,
    NULLS_LAST

}