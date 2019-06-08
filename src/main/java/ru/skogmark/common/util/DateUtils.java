package ru.skogmark.common.util;

import javax.annotation.Nullable;
import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateUtils {
    private DateUtils() {
    }

    @Nullable
    public static ZonedDateTime toZonedDateTime(@Nullable Date date) {
        return date != null
                ? ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                : null;
    }
}
