package ru.tayviscon.mafia.master.bot.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtils {

    private DateTimeUtils() {

    }

    public static final DateTimeFormatter WEEKDAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("d MMMM (EEEE)");
    public static final DateTimeFormatter TIME_FORMATTER_WITHOUT_SECONDS = DateTimeFormatter.ofPattern("HH:mm");

    public static String getGameSessionDateTimeRange(LocalDateTime startedAt, LocalDateTime endedAt) {
        return String.format(
            "%s, %s - %s",
            startedAt.format(WEEKDAY_DATE_FORMATTER),
            startedAt.format(TIME_FORMATTER_WITHOUT_SECONDS),
            endedAt.format(TIME_FORMATTER_WITHOUT_SECONDS)
        );
    }

}
