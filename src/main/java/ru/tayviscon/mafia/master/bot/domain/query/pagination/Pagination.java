package ru.tayviscon.mafia.master.bot.domain.query.pagination;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(staticName = "byDefault")
public class Pagination {

    private Integer pageNo = 0;
    private Integer pageSize = 10;

    public static Pagination getAllRows() {
        return Pagination.of(0, Integer.MAX_VALUE);
    }

    public static Pagination getOneRow() {
        return Pagination.of(0, 1);
    }

    public static Pagination getFirst(int count) {
        return Pagination.of(0, count);
    }

    public static Pagination of(Integer pageNo, Integer pageSize) {
        var pagination = new Pagination();
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);
        return pagination;
    }

}
