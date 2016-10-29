package com.liangmayong.base.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Handler;
import android.view.Gravity;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liangmayong.base.utils.toast.RadiusDrawable;
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

    // mToast
    private static ToastCompat mToast = null;
    private static Handler mHandler = new Handler();
    private static Runnable run = new Runnable() {
        @Override
        public void run() {
            mToast.cancel();
        }
    };

    /**
     * showToast
     *
     * @param text text
     */
    public static void showToast(CharSequence text) {
        showToast(text, 1500);
    }

    /**
     * show toast
     *
     * @param text     text
     * @param duration duration
     */
    @SuppressWarnings("deprecation")
    public static void showToast(CharSequence text, int duration) {
        mHandler.removeCallbacks(run);
        if (mToast == null) {
            mToast = new ToastCompat(getApplication());
        }
        LinearLayout linearLayout = new LinearLayout(getApplication());
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(25, 15, 25, 15);
        linearLayout.setBackgroundDrawable(new RadiusDrawable(50, 0x99333333));

        TextView tv = new TextView(getApplication());
        linearLayout.addView(tv);
        tv.setTextColor(0xffffffff);
        tv.setTextSize(16);
        tv.setText(text);
        mToast.setView(linearLayout);
        mHandler.postDelayed(run, duration);
        mToast.show();
    }

}