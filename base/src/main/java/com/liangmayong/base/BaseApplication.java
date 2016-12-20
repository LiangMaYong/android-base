package com.liangmayong.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.liangmayong.base.utils.FrescoUtils;
import com.liangmayong.base.utils.ThreadPoolUtils;
import com.liangmayong.base.support.lifecycle.ActivityLifeCycle;

import java.util.Iterator;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initialize(this);
    }

    /**
     * initialize
     *
     * @param application application
     */
    public static void initialize(final Application application) {
        ThreadPoolUtils threadPoolUtils = new ThreadPoolUtils(ThreadPoolUtils.Type.SingleThread, 1);
        threadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                ActivityLifeCycle.initialize(application);
                FrescoUtils.initialize(application);
            }
        });
    }

    /**
     * getBaseVersion
     *
     * @return base
     */
    public static String getBaseVersion() {
        return "1.2.0";
    }

}