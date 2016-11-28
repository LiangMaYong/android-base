package com.liangmayong.android_base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.liangmayong.base.BaseApplication;
import com.liangmayong.base.utils.ToastUtils;
import com.liangmayong.base.widget.eternal.Eternal;

/**
 * Created by LiangMaYong on 2016/11/28.
 */

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Eternal.init(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ToastUtils.showToast("Eternal onReceive");
            }
        });
    }
}
