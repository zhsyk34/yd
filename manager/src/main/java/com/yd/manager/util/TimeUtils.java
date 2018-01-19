package com.yd.manager.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class TimeUtils {

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter SIMPLE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String format(LocalDateTime time) {
        return format(time, DEFAULT_FORMATTER);
    }

    public static String format(LocalDateTime time, DateTimeFormatter formatter) {
        return time.format(formatter);
    }

    public static String format(LocalDate time) {
        return format(time, SIMPLE_FORMATTER);
    }

    public static String format(LocalDate time, DateTimeFormatter formatter) {
        return time.format(formatter);
    }

    public static LocalDateTime parseString(String time, DateTimeFormatter formatter) {
        return LocalDateTime.parse(time, formatter);
    }

    public static LocalDateTime parseString(String time) {
        return LocalDateTime.parse(time, DEFAULT_FORMATTER);
    }

    public static LocalDateTime parseSecond(long seconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault());
    }

    public static LocalDateTime parseMillis(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }

    public static long millis(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static long seconds(LocalDateTime time) {
        return time.atZone(ZoneId.of("Asia/Shanghai")).toInstant().getEpochSecond();
    }

    public static void main(String[] args) {
        Date d = new Date(1516204800 * 1000L);
        System.out.println(d);

        new Date(2018, 1, 1, 0, 0, 0);

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(f.format(d));
    }

}
