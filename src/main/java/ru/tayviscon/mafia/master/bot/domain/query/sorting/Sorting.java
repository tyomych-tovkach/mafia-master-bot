package ru.tayviscon.mafia.master.bot.domain.query.sorting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Sorting {
    private String parameter;
    private SortingType sortingType;
}
