package com.liangmayong.base.widget.eternal;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by LiangMaYong on 2016/11/28.
 */
public class EternalService extends Service {

    private Handler handler = new Handler();
    private int delay = 3000;
    private Runnable eternal = new Runnable() {
        @Override
        public void run() {
            sendBroadcast(new Intent(Eternal.ACTION));
            handler.postDelayed(this, delay);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
    }
}
