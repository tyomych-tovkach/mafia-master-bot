package ru.tayviscon.mafia.master.bot.domain.query.pagination;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PageMetaData {

    private long totalElements;

    private int totalPages;

    private int pageNumber;

    private int pageSize;

}
