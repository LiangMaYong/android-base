package com.liangmayong.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

import com.liangmayong.base.utils.FrescoUtils;
import com.liangmayong.base.utils.lifecycle.ActivityLifeCycle;
import com.liangmayong.base.widget.eternal.EternalService;

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
    public static void initialize(Application application) {
        ActivityLifeCycle.initialize(application);
        FrescoUtils.initialize(application);
        if (application.getPackageName().equals(getCurrentProcessName(application))) {
            application.startService(new Intent(application, EternalService.class));
        }
    }


    /**
     * getCurrentProcessName
     *
     * @param context
     * @return
     */
    public static String getCurrentProcessName(Context context) {
        int pid = Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        Iterator i$ = mActivityManager.getRunningAppProcesses().iterator();
        ActivityManager.RunningAppProcessInfo appProcess;
        do {
            if (!i$.hasNext()) {
                return null;
            }
            appProcess = (ActivityManager.RunningAppProcessInfo) i$.next();
        } while (appProcess.pid != pid);
        return appProcess.processName;
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