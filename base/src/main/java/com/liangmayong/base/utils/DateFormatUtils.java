package com.liangmayong.base.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateFormatUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
@SuppressLint("SimpleDateFormat")
public final class DateFormatUtils {

    private DateFormatUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_JUST_NOW = "just now";
    private static final String ONE_MINUTE_AGO = " minute ago";
    private static final String ONE_HOUR_AGO = " hour ago";
    private static final String ONE_DAY_AGO = " day ago";
    private static final String ONE_MONTH_AGO = " month ago";
    private static final String ONE_YEAR_AGO = " year ago";

    /**
     * format
     *
     * @param date   date
     * @param format format
     * @return date
     */
    public static String format(long date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(date));
    }

    /**
     * format
     *
     * @param date   date
     * @param format format
     * @return date
     */
    public static String format(long date, SimpleDateFormat format) {
        return format.format(new Date(date));
    }

    /**
     * formatYearMonthDayTime
     *
     * @param date date
     * @return date
     */
    public static String formatYearMonthDayTime(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    /**
     * formatYearMonthDay
     *
     * @param date date
     * @return date
     */
    public static String formatYearMonthDay(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * formatHousAndMin
     *
     * @param date date
     * @return int[]
     */
    public static int[] formatHousAndMin(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String datatemp = sdf.format(date);
        String datestemp[] = datatemp.split(":");
        return new int[]{Integer.valueOf(datestemp[0]), Integer.valueOf(datestemp[1])};
    }

    /**
     * formatRelative
     *
     * @param date date
     * @return date
     */
    public static String relativeDate(long date) {
        return relativeDate(date, ONE_JUST_NOW, ONE_MINUTE_AGO, ONE_HOUR_AGO, ONE_DAY_AGO, ONE_MONTH_AGO, ONE_YEAR_AGO);
    }

    /**
     * formatRelative
     *
     * @param date      date
     * @param secondAgo secondAgo
     * @param minuteAgo minuteAgo
     * @param hourAgo   hourAgo
     * @param dayAgo    dayAgo
     * @param monthAgo  monthAgo
     * @param yearAgo   yearAgo
     * @return date
     */
    public static String relativeDate(long date, String secondAgo, String minuteAgo, String hourAgo, String dayAgo, String monthAgo, String yearAgo) {
        long delta = new Date().getTime() - date;
        if (delta < 1L * ONE_MINUTE) {
            return secondAgo;
        }
        if (delta < 60L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + minuteAgo;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + hourAgo;
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + dayAgo;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + monthAgo;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + yearAgo;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

}
