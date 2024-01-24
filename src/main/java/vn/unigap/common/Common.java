package vn.unigap.common;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

public class Common {
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static Date currentTime() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime currentDateTime() {
        return LocalDateTime.now();
    }
}
