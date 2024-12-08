package ru.tayviscon.mafia.master.bot.domain.query.pagination;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PageData<T> {

    private List<T> content;

    private PageMetaData metaData;

}
