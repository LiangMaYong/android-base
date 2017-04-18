package com.liangmayong.base;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.support.annotation.NonNull;

import com.liangmayong.base.basic.expands.crash.CrashActivity;
import com.liangmayong.base.basic.expands.crash.CrashManager;
import com.liangmayong.base.basic.expands.logcat.LogcatActivity;
import com.liangmayong.base.basic.expands.webkit.WebActivity;
import com.liangmayong.base.support.router.Router;
import com.liangmayong.base.support.router.RouterProvider;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
public class BaseApplication extends Application {

    private static Application mApplication;

    /**
     * getApplication
     *
     * @return mApplication
     */
    public static Application getApplication() {
        return mApplication;
    }

    /**
     * initialize
     *
     * @param application mApplication
     */
    public static void initialize(final Application application) {
        BaseApplication.mApplication = application;
        CrashManager.init(application);
        Router.addRouterProvider(new BaseRouterProvider("Base"));
    }

    /**
     * BaseRouterProvider
     */
    private static class BaseRouterProvider extends RouterProvider {
        public BaseRouterProvider(@NonNull String providerName) {
            super(providerName);
            routerActivity("Logcat", LogcatActivity.class);
            routerActivity("Crash", CrashActivity.class);
            routerActivity("Web", WebActivity.class);
        }
    }

    /**
     * isDebugable
     *
     * @return true or false
     */
    public static boolean isDebugable(Application application) {
        try {
            ApplicationInfo info = application.getApplicationInfo();
            boolean debugable = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            return debugable;
        } catch (Exception e) {
            return false;
        }
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
        return "2.3.0";
    }
}