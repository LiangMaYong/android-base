package com.liangmayong.base.utils;

import android.app.Application;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2016/9/9.
 */
public class ContextUtils {

    private ContextUtils() {
    }

    // application
    private static WeakReference<Application> application = null;

    /**
     * getApplication
     *
     * @return application
     */
    public static Application getApplication() {
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
