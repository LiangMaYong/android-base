package com.liangmayong.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by LiangMaYong on 2016/11/8.
 */

public class TimeUtils {

    private static final int MSEC = 1;
    public static final int SEC = 1000;
    public static final int MIN = 60000;
    public static final int HOUR = 3600000;
    public static final int DAY = 86400000;

    public enum TimeUnit {
        MSEC,
        SEC,
        MIN,
        HOUR,
        DAY
    }

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


    /**
     * milliseconds2String
     *
     * @param milliseconds milliseconds
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String milliseconds2String(long milliseconds) {
        return milliseconds2String(milliseconds, DEFAULT_SDF);
    }

    /**
     * milliseconds2String
     *
     * @param milliseconds milliseconds
     * @param format       format
     * @return format time
     */
    public static String milliseconds2String(long milliseconds, SimpleDateFormat format) {
        return format.format(new Date(milliseconds));
    }

    /**
     * string2Milliseconds
     *
     * @param time time
     * @return milliseconds
     */
    public static long string2Milliseconds(String time) {
        return string2Milliseconds(time, DEFAULT_SDF);
    }

    /**
     * string2Milliseconds
     *
     * @param time   time
     * @param format format
     * @return milliseconds
     */
    public static long string2Milliseconds(String time, SimpleDateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * string2Date
     *
     * @param time time
     * @return date
     */
    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_SDF);
    }

    /**
     * string2Date
     *
     * @param time   time
     * @param format format
     * @return date
     */
    public static Date string2Date(String time, SimpleDateFormat format) {
        return new Date(string2Milliseconds(time, format));
    }

    /**
     * date2String
     *
     * @param time time
     * @return format time
     */
    public static String date2String(Date time) {
        return date2String(time, DEFAULT_SDF);
    }

    /**
     * date2String
     *
     * @param time   time
     * @param format format
     * @return format time
     */
    public static String date2String(Date time, SimpleDateFormat format) {
        return format.format(time);
    }

    /**
     * date2Milliseconds
     *
     * @param time time
     * @return date
     */
    public static long date2Milliseconds(Date time) {
        return time.getTime();
    }

    /**
     * milliseconds2Date
     *
     * @param milliseconds milliseconds
     * @return date
     */
    public static Date milliseconds2Date(long milliseconds) {
        return new Date(milliseconds);
    }

    /**
     * 毫秒时间戳单位转换（单位：unit）
     *
     * @param milliseconds 毫秒时间戳
     * @param unit         <ul>
     *                     <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *                     <li>{@link TimeUnit#SEC }: 秒</li>
     *                     <li>{@link TimeUnit#MIN }: 分</li>
     *                     <li>{@link TimeUnit#HOUR}: 小时</li>
     *                     <li>{@link TimeUnit#DAY }: 天</li>
     *                     </ul>
     * @return unit时间戳
     */
    private static long milliseconds2Unit(long milliseconds, TimeUnit unit) {
        switch (unit) {
            case MSEC:
                return milliseconds / MSEC;
            case SEC:
                return milliseconds / SEC;
            case MIN:
                return milliseconds / MIN;
            case HOUR:
                return milliseconds / HOUR;
            case DAY:
                return milliseconds / DAY;
        }
        return -1;
    }

    /**
     * getIntervalTime
     *
     * @param time0 time0
     * @param time1 time1
     * @param unit  unit
     * @return interval
     */
    public static long getIntervalTime(String time0, String time1, TimeUnit unit) {
        return getIntervalTime(time0, time1, unit, DEFAULT_SDF);
    }

    /**
     * getIntervalTime
     *
     * @param time0  time0
     * @param time1  time1
     * @param unit   unit
     * @param format format
     * @return interval
     */
    public static long getIntervalTime(String time0, String time1, TimeUnit unit, SimpleDateFormat format) {
        return milliseconds2Unit(Math.abs(string2Milliseconds(time0, format)
                - string2Milliseconds(time1, format)), unit);
    }

    /**
     * getIntervalTime
     *
     * @param time0 time0
     * @param time1 time1
     * @param unit  unit
     * @return interval
     */
    public static long getIntervalTime(Date time0, Date time1, TimeUnit unit) {
        return milliseconds2Unit(Math.abs(date2Milliseconds(time1)
                - date2Milliseconds(time0)), unit);
    }

    /**
     * getCurTimeMills
     *
     * @return timemills
     */
    public static long getCurTimeMills() {
        return System.currentTimeMillis();
    }

    /**
     * getCurTimeString
     *
     * @return date
     */
    public static String getCurTimeString() {
        return date2String(new Date());
    }

    /**
     * getCurTimeString
     *
     * @param format format
     * @return date
     */
    public static String getCurTimeString(SimpleDateFormat format) {
        return date2String(new Date(), format);
    }

    /**
     * getCurTimeDate
     *
     * @return date
     */
    public static Date getCurTimeDate() {
        return new Date();
    }

    /**
     * getIntervalByNow
     *
     * @param time time
     * @param unit unit
     * @return interval
     */
    public static long getIntervalByNow(String time, TimeUnit unit) {
        return getIntervalByNow(time, unit, DEFAULT_SDF);
    }


    /**
     * getIntervalByNow
     *
     * @param time   time
     * @param unit   unit
     * @param format format
     * @return interval
     */
    public static long getIntervalByNow(String time, TimeUnit unit, SimpleDateFormat format) {
        return getIntervalTime(getCurTimeString(), time, unit, format);
    }

    /**
     * getIntervalByNow
     *
     * @param time time
     * @param unit unit
     * @return interval
     */
    public static long getIntervalByNow(Date time, TimeUnit unit) {
        return getIntervalTime(getCurTimeDate(), time, unit);
    }

    /**
     * isLeapYear
     *
     * @param year year
     * @return leap year
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * getWeek
     *
     * @param time time
     * @return week
     */
    public static String getWeek(String time) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time));
    }

    /**
     * getWeek
     *
     * @param time   time
     * @param format format
     * @return week
     */
    public static String getWeek(String time, SimpleDateFormat format) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time, format));
    }

    /**
     * getWeek
     *
     * @param time time
     * @return week
     */
    public static String getWeek(Date time) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(time);
    }

    /**
     * getWeekIndex
     *
     * @param time time
     * @return week index
     */
    public static int getWeekIndex(String time) {
        Date date = string2Date(time);
        return getWeekIndex(date);
    }

    /**
     * getWeekIndex
     *
     * @param time   time
     * @param format format
     * @return week index
     */
    public static int getWeekIndex(String time, SimpleDateFormat format) {
        Date date = string2Date(time, format);
        return getWeekIndex(date);
    }

    /**
     * getWeekIndex
     *
     * @param time time
     * @return week index
     */
    public static int getWeekIndex(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * getWeekOfMonth
     *
     * @param time time
     * @return week
     */
    public static int getWeekOfMonth(String time) {
        Date date = string2Date(time);
        return getWeekOfMonth(date);
    }

    /**
     * getWeekOfMonth
     *
     * @param time   time
     * @param format format
     * @return week
     */
    public static int getWeekOfMonth(String time, SimpleDateFormat format) {
        Date date = string2Date(time, format);
        return getWeekOfMonth(date);
    }

    /**
     * getWeekOfMonth
     *
     * @param time time
     * @return week
     */
    public static int getWeekOfMonth(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * getWeekOfYear
     *
     * @param time time
     * @return week
     */
    public static int getWeekOfYear(String time) {
        Date date = string2Date(time);
        return getWeekOfYear(date);
    }

    /**
     * getWeekOfYear
     *
     * @param time   time
     * @param format format
     * @return week
     */
    public static int getWeekOfYear(String time, SimpleDateFormat format) {
        Date date = string2Date(time, format);
        return getWeekOfYear(date);
    }

    /**
     * getWeekOfYear
     *
     * @param time time
     * @return week
     */
    public static int getWeekOfYear(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
}
