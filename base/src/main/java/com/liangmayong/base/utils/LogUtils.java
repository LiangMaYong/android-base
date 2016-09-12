package com.liangmayong.base.utils;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * LogUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class LogUtils {


    private static volatile LogUtils defualt_log = null;

    private static LogUtils getDefualt() {
        if (defualt_log == null) {
            synchronized (LogUtils.class) {
                defualt_log = new LogUtils("ANDROID-BASE-TAG");
            }
        }
        return defualt_log;
    }

    public static LogUtils get(String tag) {
        return new LogUtils(tag);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // application
    private static WeakReference<Application> application = null;

    /**
     * getApplication
     *
     * @return application
     */
    private static Application getApplication() {
        if (application == null || application.get() == null) {
            synchronized (LogUtils.class) {
                if (application == null) {
                    try {
                        Class<?> clazz = Class.forName("android.app.ActivityThread");
                        Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
                        if (currentActivityThread != null) {
                            Object object = currentActivityThread.invoke(null);
                            if (object != null) {
                                Method getApplication = object.getClass().getDeclaredMethod("getApplication");
                                if (getApplication != null) {
                                    application = new WeakReference<Application>((Application) getApplication.invoke(object));
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return application.get();
    }

    /**
     * isDebugable
     *
     * @return true or false
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    public static boolean isDebugable() {
        try {
            ApplicationInfo info = getApplication().getApplicationInfo();
            boolean debugable = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            return debugable;
        } catch (Exception e) {
            return false;
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private final String TAG;

    private LogUtils(String tag) {
        if (tag == null) {
            if (getApplication() != null) {
                tag = getApplication().getPackageName();
            } else {
                tag = "android-base-log";
            }
        }
        TAG = tag;
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public void d(String msg) {
        if (isDebugable()) {
            Log.d(TAG, msg);
        }
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public void d(String msg, Throwable tr) {
        if (isDebugable()) {
            Log.d(TAG, msg, tr);
        }
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public void e(String msg) {
        if (isDebugable()) {
            Log.e(TAG, msg);
        }
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public void e(String msg, Throwable tr) {
        if (isDebugable()) {
            Log.e(TAG, msg, tr);
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public void i(String msg) {
        if (isDebugable()) {
            Log.i(TAG, msg);
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public void i(String msg, Throwable tr) {
        if (isDebugable()) {
            Log.i(TAG, msg, tr);
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public void v(String msg) {
        if (isDebugable()) {
            Log.v(TAG, msg);
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public void v(String msg, Throwable tr) {
        if (isDebugable()) {
            Log.v(TAG, msg, tr);
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public void w(String msg) {
        if (isDebugable()) {
            Log.w(TAG, msg);
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public void w(String msg, Throwable tr) {
        if (isDebugable()) {
            Log.w(TAG, msg, tr);
        }
    }
}
