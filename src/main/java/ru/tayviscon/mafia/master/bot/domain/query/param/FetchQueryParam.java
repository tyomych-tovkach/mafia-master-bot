package ru.tayviscon.mafia.master.bot.domain.query.param;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import ru.tayviscon.mafia.master.bot.domain.query.pagination.Pagination;
import ru.tayviscon.mafia.master.bot.domain.query.sorting.Sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Log4j2
@Accessors(chain = true)
public class FetchQueryParam<F> {

    private F filter;
    private Pagination pagination;
    private List<Sorting> sorting;

    public Pagination getPagination() {
        return Optional.ofNullable(pagination)
            .orElseThrow(
                () -> new IllegalStateException(
                    "It's forbidden to use query parameters without defining pagination"
                )
            );
    }

    public List<Sorting> getSorting() {
        return Optional.ofNullable(sorting).orElseGet(ArrayList::new);
    }

}
