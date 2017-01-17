package com.liangmayong.base.support.logger;

import android.util.Log;

/**
 * Created by LiangMaYong on 2017/1/17.
 */
public final class Logger {

    /**
     * LoggerListener
     */
    public interface LoggerListener {
        /**
         * log tag
         *
         * @return tag
         */
        String getTag();

        /**
         * Send a debug log message and log the exception.
         *
         * @param tag tag
         * @param msg The message you would like logged.
         * @param tr  An exception to log
         */
        void debug(String tag, String msg, Throwable tr);

        /**
         * Send a error log message and log the exception.
         *
         * @param tag tag
         * @param msg The message you would like logged.
         * @param tr  An exception to log
         */
        void error(String tag, String msg, Throwable tr);

        /**
         * Send a info log message and log the exception.
         *
         * @param tag tag
         * @param msg The message you would like logged.
         * @param tr  An exception to log
         */
        void info(String tag, String msg, Throwable tr);

        /**
         * Send a verbose log message and log the exception.
         *
         * @param tag tag
         * @param msg The message you would like logged.
         * @param tr  An exception to log
         */
        void verbose(String tag, String msg, Throwable tr);

        /**
         * Send a warn log message and log the exception.
         *
         * @param tag tag
         * @param msg The message you would like logged.
         * @param tr  An exception to log
         */
        void warn(String tag, String msg, Throwable tr);
    }

    private Logger() {
    }

    // debugable
    private static boolean debugable = true;
    // loggerListener
    private static LoggerListener loggerListener;

    public static void setLoggerListener(LoggerListener loggerListener) {
        Logger.loggerListener = loggerListener;
    }

    public static boolean isDebugable() {
        return debugable;
    }

    public static void setDebugable(boolean debugable) {
        Logger.debugable = debugable;
    }

    static {
        loggerListener = new LoggerListener() {
            @Override
            public String getTag() {
                return Logger.class.getSimpleName();
            }

            @Override
            public void debug(String tag, String msg, Throwable tr) {
                Log.d(tag, msg, tr);
            }

            @Override
            public void error(String tag, String msg, Throwable tr) {
                Log.e(tag, msg, tr);
            }

            @Override
            public void info(String tag, String msg, Throwable tr) {
                Log.i(tag, msg, tr);
            }

            @Override
            public void verbose(String tag, String msg, Throwable tr) {
                Log.v(tag, msg, tr);
            }

            @Override
            public void warn(String tag, String msg, Throwable tr) {
                Log.w(tag, msg, tr);
            }
        };
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public static void d(String msg) {
        if (isDebugable()) {
            if (loggerListener != null) {
                loggerListener.debug(loggerListener.getTag(), msg, null);
            }
        }
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void d(String msg, Throwable tr) {
        if (isDebugable()) {
            if (loggerListener != null) {
                loggerListener.debug(loggerListener.getTag(), msg, tr);
            }
        }
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public static void e(String msg) {
        if (isDebugable()) {
            if (loggerListener != null) {
                loggerListener.error(loggerListener.getTag(), msg, null);
            }
        }
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void e(String msg, Throwable tr) {
        if (isDebugable()) {
            if (loggerListener != null) {
                loggerListener.error(loggerListener.getTag(), msg, tr);
            }
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public static void i(String msg) {
        if (isDebugable()) {
            if (loggerListener != null) {
                loggerListener.info(loggerListener.getTag(), msg, null);
            }
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void i(String msg, Throwable tr) {
        if (isDebugable()) {
            if (loggerListener != null) {
                loggerListener.info(loggerListener.getTag(), msg, tr);
            }
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public static void v(String msg) {
        if (isDebugable()) {
            if (loggerListener != null) {
                loggerListener.verbose(loggerListener.getTag(), msg, null);
            }
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void v(String msg, Throwable tr) {
        if (isDebugable()) {
            if (loggerListener != null) {
                loggerListener.verbose(loggerListener.getTag(), msg, tr);
            }
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public static void w(String msg) {
        if (isDebugable()) {
            if (loggerListener != null) {
                loggerListener.warn(loggerListener.getTag(), msg, null);
            }
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void w(String msg, Throwable tr) {
        if (isDebugable()) {
            if (loggerListener != null) {
                loggerListener.warn(loggerListener.getTag(), msg, tr);
            }
        }
    }
}
