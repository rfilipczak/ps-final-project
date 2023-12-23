package edu.p.lodz.pl.common.utils.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private TimeUtils() {}

    public static String getNextExpirationDate(long time) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime then = now.plusSeconds(time);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return then.format(formatter);
    }

    public static boolean isExpired(String ts) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime that = LocalDateTime.parse(ts, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return now.isAfter(that);
    }
}
