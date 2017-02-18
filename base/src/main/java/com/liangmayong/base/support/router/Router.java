package com.liangmayong.base.support.router;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.liangmayong.base.support.router.rule.Rule;
import com.liangmayong.base.support.router.rule.RuleInternal;

import java.util.Map;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public class Router {

    private Router() {
    }

    public static final String ACTIVITY_SCHEME = "activity://";
    public static final String RECEIVER_SCHEME = "receiver://";
    public static final String SERVICE_SCHEME = "service://";


    public static void addRouterProvider(RouterProvider routerProvider) {
        if (!routerProvider.getActivityRouters().isEmpty()) {
            for (Map.Entry<String, Class<? extends Activity>> entry : routerProvider.getActivityRouters().entrySet()) {
                routerActivity(entry.getKey(), entry.getValue());
            }
        }
        if (!routerProvider.getServiceRouters().isEmpty()) {
            for (Map.Entry<String, Class<? extends Service>> entry : routerProvider.getServiceRouters().entrySet()) {
                routerService(entry.getKey(), entry.getValue());
            }
        }
        if (!routerProvider.getReceiverRouters().isEmpty()) {
            for (Map.Entry<String, Class<? extends BroadcastReceiver>> entry : routerProvider.getReceiverRouters().entrySet()) {
                routerReceiver(entry.getKey(), entry.getValue());
            }
        }
    }

    public static RuleInternal addRule(Rule rule) {
        RuleInternal router = RuleInternal.get();
        if (rule != null) {
            router.addRule(rule.getScheme(), rule);
        }
        return router;
    }

    public static <T> RuleInternal router(String scheme, String name, Class<T> klass) {
        return RuleInternal.get().router(scheme + name, klass);
    }

    public static <V> V invoke(Context ctx, String pattern) {
        return RuleInternal.get().invoke(ctx, pattern);
    }

    public static <T extends Activity> RuleInternal routerActivity(String name, Class<T> klass) {
        return RuleInternal.get().router(ACTIVITY_SCHEME + name, klass);
    }

    public static Intent invokeActivity(Context ctx, String name) {
        return RuleInternal.get().invoke(ctx, ACTIVITY_SCHEME + name);
    }

    public static <T extends Service> RuleInternal routerService(String name, Class<T> klass) {
        return RuleInternal.get().router(SERVICE_SCHEME + name, klass);
    }

    public static Intent invokeService(Context ctx, String name) {
        return RuleInternal.get().invoke(ctx, SERVICE_SCHEME + name);
    }

    public static <T extends BroadcastReceiver> RuleInternal routerReceiver(String name, Class<T> klass) {
        return RuleInternal.get().router(RECEIVER_SCHEME + name, klass);
    }

    public static Intent invokeReceiver(Context ctx, String name) {
        return RuleInternal.get().invoke(ctx, RECEIVER_SCHEME + name);
    }
}
