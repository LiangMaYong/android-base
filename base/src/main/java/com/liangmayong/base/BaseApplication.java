package com.liangmayong.base;

import android.app.Application;

import com.liangmayong.base.utils.FrescoUtils;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initialize(this);
    }

    public static void initialize(Application application) {
        FrescoUtils.initialize(application);
    }

}