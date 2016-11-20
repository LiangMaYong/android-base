package com.liangmayong.base.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.widget.Toast;

import com.liangmayong.base.utils.toast.IToast;
import com.liangmayong.base.utils.toast.ToastCompat;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * ToastUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
@SuppressLint("InflateParams")
public class ToastUtils {

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