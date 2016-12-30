package com.liangmayong.base.support.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.widget.Toast;

import com.liangmayong.base.support.toast.IToast;
import com.liangmayong.base.support.toast.ToastCompat;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * ToastUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
@SuppressLint("InflateParams")
public final class ToastUtils {


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
            synchronized (ToastUtils.class) {
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

    private static IToast mToast = null;

    /**
     * showToast
     *
     * @param text text
     */
    public static void showToast(CharSequence text) {
        ToastCompat.makeText(getApplication(), text + "", Toast.LENGTH_SHORT).show();
    }

    /**
     * showToast
     *
     * @param stringId stringId
     */
    public static void showToast(int stringId) {
        ToastCompat.makeText(getApplication(), getApplication().getString(stringId) + "", Toast.LENGTH_SHORT).show();
    }

    /**
     * show toast
     *
     * @param text     text
     * @param duration duration
     */
    @SuppressWarnings("deprecation")
    public static void showToast(CharSequence text, int duration) {
        ToastCompat.makeText(getApplication(), text + "", duration).show();
    }

}