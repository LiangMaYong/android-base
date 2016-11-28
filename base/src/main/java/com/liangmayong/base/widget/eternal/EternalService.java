package com.liangmayong.base.widget.eternal;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.liangmayong.base.utils.ToastUtils;

/**
 * Created by LiangMaYong on 2016/11/28.
 */
public class EternalService extends Service {

    private static EternalService mEternalService = null;
    private Handler handler = new Handler();
    private int delay = 3000;
    private Runnable eternal = new Runnable() {
        @Override
        public void run() {
            sendBroadcast(new Intent(Eternal.ACTION));
            handler.postDelayed(this, delay);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mEternalService = this;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startService(new Intent(this, InnerService.class));
        ToastUtils.showToast("onStartCommand");
        try {
            while (true) {
                handler.postDelayed(eternal, delay);
            }
        } catch (Exception e) {
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(eternal);
        handler = null;
        mEternalService = null;
    }

    public static class InnerService extends Service {

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Eternal.setForeground(mEternalService, this);
            return super.onStartCommand(intent, flags, startId);
        }
    }
}
