package ru.tayviscon.mafia.master.bot.util;

import java.util.UUID;

public final class ValueUtils {

    private ValueUtils() {

    }

    public static boolean isValidUuid(String value) {
        try {
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


}
