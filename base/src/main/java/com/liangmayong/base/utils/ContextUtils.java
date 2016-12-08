package com.liangmayong.base.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import com.liangmayong.base.compat.lifecycle.ActivityLifeCycle;
import com.liangmayong.base.compat.database.DataUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2016/9/9.
 */
public class ContextUtils {


    private ContextUtils() {
    }

    //superContext
    private static volatile SuperContext superContext = null;

    /**
     * getContext
     *
     * @return context
     */
    public static Context getContext() {
        if (ActivityLifeCycle.getCurrentActivity() != null) {
            return ActivityLifeCycle.getCurrentActivity();
        }
        if (superContext == null) {
            synchronized (ContextUtils.class) {
                superContext = new SuperContext();
                superContext.attach(getApplication());
            }
        }
        return superContext;
    }

    /**
     * SuperContext
     */
    private static final class SuperContext extends ContextThemeWrapper {

        @Override
        public void startActivities(Intent[] intents) {
            if ((intents[0].getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK) == 0) {
                intents[0].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            super.startActivities(intents);
        }

        @Override
        public void startActivities(Intent[] intents, Bundle options) {
            if ((intents[0].getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK) == 0) {
                intents[0].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            super.startActivities(intents, options);
        }

        @Override
        public void startActivity(Intent intent) {
            if ((intent.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK) == 0) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            super.startActivity(intent);
        }

        @Override
        public void startActivity(Intent intent, Bundle options) {
            if ((intent.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK) == 0) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            super.startActivity(intent, options);
        }

        public void attach(Context newBase) {
            attachBaseContext(newBase);
        }
    }


    // application
    private static WeakReference<Application> application = null;
    private static Class<?> activityThreadClass = null;
    private static Method currentActivityThreadMethod = null;
    private static Method getApplicationMethod = null;

    /**
     * getApplication
     *
     * @return application
     */
    public static Application getApplication() {
        if (application == null || application.get() == null) {
            synchronized (DataUtils.class) {
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

    /**
     * isDebugable
     *
     * @return true or false
     */
    public static boolean isDebugable() {
        try {
            ApplicationInfo info = ContextUtils.getApplication().getApplicationInfo();
            boolean debugable = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            return debugable;
        } catch (Exception e) {
            return false;
        }
    }
}
