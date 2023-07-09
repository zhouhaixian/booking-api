package cn.zhouhaixian.bookingapi.utils;

import java.time.LocalDateTime;

public class TimeUtils {
    public static boolean isBetweenNextSevenDays(LocalDateTime time) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime sevenDaysLater = currentTime.plusDays(7);

        return time.isAfter(currentTime) && time.isBefore(sevenDaysLater);
    }
}
