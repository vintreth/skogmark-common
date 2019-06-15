package ru.skogmark.common.util;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateUtils {
    private DateUtils() {
    }

    @Nullable
    public static ZonedDateTime toZonedDateTime(@Nullable Timestamp timestamp) {
        return timestamp != null
                ? ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault())
                : null;
    }
}
