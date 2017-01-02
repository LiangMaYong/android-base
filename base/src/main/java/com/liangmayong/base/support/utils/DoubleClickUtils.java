package com.liangmayong.base.support.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * DoubleClickUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class DoubleClickUtils {
    /***
     * OnDoubleClickListener
     *
     * @author LiangMaYong
     * @version 1.0
     */
    public interface OnDoubleClickListener {
        void OnSingleClick(View v);

        void OnDoubleClick(View v);
    }

    /**
     * registerDoubleClickListener
     *
     * @param view     view
     * @param listener listener
     */
    public static void registerDoubleClickListener(View view, final OnDoubleClickListener listener) {
        if (listener == null)
            return;
        view.setOnClickListener(new View.OnClickListener() {
            private static final int DOUBLE_CLICK_TIME = 350;
            private boolean waitDouble = true;
            @SuppressLint("HandlerLeak")
            private Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    listener.OnSingleClick((View) msg.obj);
                }
            };

            public void onClick(final View v) {
                if (waitDouble) {
                    waitDouble = false;
                    new Thread() {
                        public void run() {
                            try {
                                Thread.sleep(DOUBLE_CLICK_TIME);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (!waitDouble) {
                                waitDouble = true;
                                Message msg = handler.obtainMessage();
                                msg.obj = v;
                                handler.sendMessage(msg);
                            }
                        }

                    }.start();
                } else {
                    waitDouble = true;
                    listener.OnDoubleClick(v);
                }
            }
        });
    }
}
