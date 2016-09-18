package com.liangmayong.base.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private static Toast mToast = null;
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
            mToast = new Toast(getApplication());
        }
        LinearLayout linearLayout = new LinearLayout(getApplication());
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(20, 10, 20, 10);
        linearLayout.setBackgroundDrawable(new RoundColorDrawable(15, 0x99333333));

        TextView tv = new TextView(getApplication());
        linearLayout.addView(tv);
        tv.setTextColor(0xffffffff);
        tv.setTextSize(14);
        tv.setText(text);
        mToast.setView(linearLayout);
        mHandler.postDelayed(run, duration);
        mToast.show();
    }

    /**
     * RoundColorDrawable
     *
     * @author LiangMaYong
     * @version 1.0
     */
    private static class RoundColorDrawable extends ColorDrawable {

        private int round;

        public RoundColorDrawable(int round, int color) {
            super(color);
            this.round = round;
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(new RoundCanvas(canvas, round));
        }

        private class RoundCanvas extends Canvas {

            private int round;
            private Canvas canvas;

            public RoundCanvas(Canvas canvas, int round) {
                this.round = round;
                this.canvas = canvas;
            }

            @Override
            public void drawRect(Rect r, Paint paint) {
                RectF rectF = new RectF(r);
                paint.setAntiAlias(true);
                canvas.drawRoundRect(rectF, round, round, paint);
            }

        }

    }
}