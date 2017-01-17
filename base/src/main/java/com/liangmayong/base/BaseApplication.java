package com.liangmayong.base;

import android.app.Application;

import com.liangmayong.base.support.crash.CrashHandler;
import com.liangmayong.base.support.utils.FrescoUtils;
import com.liangmayong.base.support.utils.ThreadPoolUtils;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
public class BaseApplication extends Application {

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
                FrescoUtils.initialize(application);
                CrashHandler.init(application);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplication.initialize(this);
    }

    /**
     * getBaseVersion
     *
     * @return base
     */
    public static String getBaseVersion() {
        return "2.0.0";
    }

}