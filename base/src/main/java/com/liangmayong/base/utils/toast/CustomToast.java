package com.liangmayong.base.utils.toast;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ttt on 2016/7/5.
 */
public class CustomToast implements IToast {

    private static Handler mHandler = new Handler();

    private static BlockingQueue<CustomToast> mQueue = new LinkedBlockingDeque<>();

    private static AtomicInteger mAtomicInteger = new AtomicInteger(0);

    private WindowManager mWindowManager;

    private long mDurationMillis;

    private View mView;

    private WindowManager.LayoutParams mParams;

    private Context mContext;

    public static IToast makeText(Context context, String text, long duration) {
        return new CustomToast(context)
                .setText(text)
                .setDuration(duration)
                .setGravity(Gravity.CENTER, 0, 0);
    }

    /**
     * @param context
     */
    public CustomToast(Context context) {
        mContext = context;
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.windowAnimations = android.R.style.Animation_Toast;
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mParams.setTitle("Toast");
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mParams.gravity = Gravity.BOTTOM;
    }

    /**
     * Set the location at which the notification should appear on the screen.
     *
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public IToast setGravity(int gravity, int xOffset, int yOffset) {
        // We can resolve the Gravity here by using the Locale for getting
        // the layout direction
        final int finalGravity;
        if (Build.VERSION.SDK_INT >= 14) {
            final Configuration config = mView.getContext().getResources().getConfiguration();
            finalGravity = Gravity.getAbsoluteGravity(gravity, config.getLayoutDirection());
        } else {
            finalGravity = gravity;
        }
        mParams.gravity = finalGravity;
        if ((finalGravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
            mParams.horizontalWeight = 1.0f;
        }
        if ((finalGravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
            mParams.verticalWeight = 1.0f;
        }
        mParams.y = yOffset;
        mParams.x = xOffset;
        return this;
    }

    @Override
    public IToast setDuration(long durationMillis) {
        if (durationMillis < 0) {
            mDurationMillis = 0;
        }
        if (durationMillis == Toast.LENGTH_SHORT) {
            mDurationMillis = 1500;
        } else if (durationMillis == Toast.LENGTH_LONG) {
            mDurationMillis = 2500;
        } else {
            mDurationMillis = durationMillis;
        }
        return this;
    }

    @Override
    public IToast setView(View view) {
        mView = view;
        return this;
    }

    @Override
    public IToast setMargin(float horizontalMargin, float verticalMargin) {
        mParams.horizontalMargin = horizontalMargin;
        mParams.verticalMargin = verticalMargin;
        return this;
    }

    public IToast setText(String text) {
        View view = Toast.makeText(mContext, text, Toast.LENGTH_SHORT).getView();
        if (view != null) {
            TextView tv = (TextView) view.findViewById(android.R.id.message);
            tv.setText(text);
            setView(view);
        }
        return this;
    }

    @Override
    public void show() {
        mQueue.offer(this);
        if (0 == mAtomicInteger.get()) {
            mAtomicInteger.incrementAndGet();
            mHandler.post(mActivite);
        }
    }

    @Override
    public void cancel() {
        if (0 == mAtomicInteger.get() && mQueue.isEmpty()) return;
        if (this.equals(mQueue.peek())) {
            mHandler.removeCallbacks(mActivite);
            mHandler.post(mHide);
            mHandler.post(mActivite);
        }
    }

    private void handleShow() {
        if (mView != null) {
            if (mView.getParent() != null) {
                mWindowManager.removeView(mView);
            }
            mWindowManager.addView(mView, mParams);
        }
    }

    private void handleHide() {
        if (mView != null) {
            if (mView.getParent() != null) {
                mWindowManager.removeView(mView);
                mQueue.poll();
            }
            mView = null;
        }
    }

    private static void activeQueue() {
        CustomToast toast = mQueue.peek();
        if (toast == null) {
            mAtomicInteger.decrementAndGet();
        } else {
            mHandler.post(toast.mShow);
            mHandler.postDelayed(toast.mHide, toast.mDurationMillis);
            mHandler.postDelayed(mActivite, toast.mDurationMillis);
        }
    }

    private final Runnable mShow = new Runnable() {
        @Override
        public void run() {
            handleShow();
        }
    };

    private final Runnable mHide = new Runnable() {
        @Override
        public void run() {
            handleHide();
        }
    };

    private final static Runnable mActivite = new Runnable() {
        @Override
        public void run() {
            activeQueue();
        }
    };
}
