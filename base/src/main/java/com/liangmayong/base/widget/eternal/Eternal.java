package com.liangmayong.base.widget.eternal;

import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Process;

import com.liangmayong.base.utils.ContextUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * Created by LiangMaYong on 2016/11/28.
 */
public class Eternal {

    public static final String ACTION = "com.liangmayong.base.eternal_action";

    private static boolean isInited = false;

    /**
     * init
     *
     * @param broadcastReceiver broadcastReceiver
     */
    public static void init(BroadcastReceiver broadcastReceiver) {
        if (!getApplication().getPackageName().equals(getCurrentProcessName(getApplication()))) {
            return;
        }
        if (broadcastReceiver == null) {
            return;
        }
        if (isInited) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter(ACTION);
        getApplication().registerReceiver(broadcastReceiver, intentFilter);
    }

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

    private Eternal() {
    }

    // application
    private static WeakReference<Application> application = null;

    /**
     * getApplication
     *
     * @return application
     */
    private static Application getApplication() {
        if (application == null || application.get() == null) {
            synchronized (ContextUtils.class) {
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
}