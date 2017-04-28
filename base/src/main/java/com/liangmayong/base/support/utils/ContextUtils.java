package com.liangmayong.base.support.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Process;

import com.liangmayong.base.BaseApplication;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * Created by LiangMaYong on 2016/9/9.
 */
public final class ContextUtils {

    private ContextUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    // application
    private static WeakReference<Application> application = null;
    // activityThreadClass
    private static Class<?> activityThreadClass = null;
    // currentActivityThreadMethod
    private static Method currentActivityThreadMethod = null;
    // getApplicationMethod
    private static Method getApplicationMethod = null;

    /**
     * getCurrentProcessName
     *
     * @param context
     * @return
     */
    public static String getCurrentProcessName(Context context) {
        int pid = Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        Iterator i$ = mActivityManager.getRunningAppProcesses().iterator();
        ActivityManager.RunningAppProcessInfo appProcess;
        do {
            if (!i$.hasNext()) {
                return null;
            }
            appProcess = (ActivityManager.RunningAppProcessInfo) i$.next();
        } while (appProcess.pid != pid);
        return appProcess.processName;
    }

    /**
     * isDebug
     *
     * @return true or false
     */
    public static boolean isDebug() {
        try {
            ApplicationInfo info = ContextUtils.getApplication().getApplicationInfo();
            boolean debugable = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            return debugable;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * getApplication
     *
     * @return application
     */
    public static Application getApplication() {
        Application mApp = BaseApplication.getApplication();
        if (mApp != null) {
            return mApp;
        }
        if (application == null || application.get() == null) {
            synchronized (ContextUtils.class) {
                if (application == null) {
                    try {
                        if (activityThreadClass == null) {
                            activityThreadClass = Class.forName("android.app.ActivityThread");
                        }
                        if (currentActivityThreadMethod == null) {
                            currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
                        }
                        if (getApplicationMethod == null) {
                            getApplicationMethod = activityThreadClass.getDeclaredMethod("getApplication");
                        }
                        if (currentActivityThreadMethod != null && getApplicationMethod != null) {
                            Object object = currentActivityThreadMethod.invoke(null);
                            if (object != null) {
                                if (getApplicationMethod != null) {
                                    application = new WeakReference<Application>((Application) getApplicationMethod.invoke(object));
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
}
