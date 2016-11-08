package com.liangmayong.base.utils.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.os.Bundle;

import com.liangmayong.base.utils.ReflectUtils;
import com.liangmayong.base.utils.lifecycle.instrument.DefualtInstrumentation;
import com.liangmayong.base.utils.lifecycle.instrument.ProxyInstrumentation;

import java.util.ArrayList;
import java.util.List;

/**
 * ActivityLifeCycle
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class ActivityLifeCycle {


    private static boolean isInited = false;

    /**
     * isInited
     *
     * @return isInited
     */
    public static boolean isInited() {
        return isInited;
    }

    /**
     * initialize
     *
     * @param application application
     * @return true or false
     */
    public static boolean initialize(Application application) {
        if (isInited) {
            return true;
        }
        if (application == null) {
            return false;
        }
        try {
            Object loadedApk = ReflectUtils.on(application).get("mLoadedApk");
            if (loadedApk != null) {
                Object activityThread = ReflectUtils.on(loadedApk).get("mActivityThread");
                if (activityThread != null) {
                    ProxyInstrumentation instrumentation = new DefualtInstrumentation();
                    Instrumentation instrument = ReflectUtils.on(activityThread).get("mInstrumentation");
                    instrumentation.proxyInstrumentation(instrument);
                    ReflectUtils.on(activityThread).set("mInstrumentation", instrumentation);
                }
            }
            isInited = true;
            return true;
        } catch (Exception e) {
        }
        return false;
    }


    //currentActivity
    private static Activity currentActivity = null;

    /**
     * getCurrentActivity
     *
     * @return currentActivity
     */
    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    /**
     * exit
     */
    public static void exit() {
        if (!getActivities().isEmpty()) {
            for (int i = 0; i < getActivities().size(); i++) {
                try {
                    getActivities().get(i).finish();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * getActivities
     *
     * @return ACTIVITIES
     */
    public static List<Activity> getActivities() {
        return ACTIVITIES;
    }

    // ACTIVITIES
    private static final List<Activity> ACTIVITIES = new ArrayList<Activity>();
    // LIFE_CYCLE_LISTENERS
    private static final List<OnActivityLifeCycleListener> LIFE_CYCLE_LISTENERS = new ArrayList<OnActivityLifeCycleListener>();

    /**
     * registerActivityLifeCycleListener
     *
     * @param lifeCycleListener lifeCycleListener
     */
    public static void registerActivityLifeCycleListener(OnActivityLifeCycleListener lifeCycleListener) {
        if (!LIFE_CYCLE_LISTENERS.contains(lifeCycleListener)) {
            LIFE_CYCLE_LISTENERS.add(lifeCycleListener);
        }
    }

    /**
     * unregisterActivityLifeCycleListener
     *
     * @param lifeCycleListener lifeCycleListener
     */
    public static void unregisterActivityLifeCycleListener(OnActivityLifeCycleListener lifeCycleListener) {
        if (LIFE_CYCLE_LISTENERS.contains(lifeCycleListener)) {
            LIFE_CYCLE_LISTENERS.remove(lifeCycleListener);
        }
    }

    private ActivityLifeCycle() {
    }

    /**
     * onCreate
     *
     * @param target             target
     * @param savedInstanceState savedInstanceState
     */
    protected static void onCreate(Activity target, Bundle savedInstanceState) {
        if (!ACTIVITIES.contains(target)) {
            ACTIVITIES.add(target);
        }
        if (!LIFE_CYCLE_LISTENERS.isEmpty()) {
            for (int i = 0; i < LIFE_CYCLE_LISTENERS.size(); i++) {
                LIFE_CYCLE_LISTENERS.get(i).onCreate(target, savedInstanceState);
            }
        }
    }

    /**
     * onStart
     *
     * @param target target
     */
    protected static void onStart(Activity target) {
        if (!LIFE_CYCLE_LISTENERS.isEmpty()) {
            for (int i = 0; i < LIFE_CYCLE_LISTENERS.size(); i++) {
                LIFE_CYCLE_LISTENERS.get(i).onStart(target);
            }
        }
    }

    /**
     * onRestart
     *
     * @param target target
     */
    protected static void onRestart(Activity target) {
        if (!LIFE_CYCLE_LISTENERS.isEmpty()) {
            for (int i = 0; i < LIFE_CYCLE_LISTENERS.size(); i++) {
                LIFE_CYCLE_LISTENERS.get(i).onRestart(target);
            }
        }
    }

    /**
     * onDestroy
     *
     * @param target target
     */
    protected static void onDestroy(Activity target) {
        if (ACTIVITIES.contains(target)) {
            ACTIVITIES.remove(target);
        }
        if (!LIFE_CYCLE_LISTENERS.isEmpty()) {
            for (int i = 0; i < LIFE_CYCLE_LISTENERS.size(); i++) {
                LIFE_CYCLE_LISTENERS.get(i).onDestroy(target);
            }
        }
    }

    /**
     * onPause
     *
     * @param target target
     */
    protected static void onPause(Activity target) {
        if (!LIFE_CYCLE_LISTENERS.isEmpty()) {
            for (int i = 0; i < LIFE_CYCLE_LISTENERS.size(); i++) {
                LIFE_CYCLE_LISTENERS.get(i).onPause(target);
            }
        }
    }

    /**
     * onStop
     *
     * @param target target
     */
    protected static void onStop(Activity target) {
        if (!LIFE_CYCLE_LISTENERS.isEmpty()) {
            for (int i = 0; i < LIFE_CYCLE_LISTENERS.size(); i++) {
                LIFE_CYCLE_LISTENERS.get(i).onStop(target);
            }
        }
    }

    /**
     * onResume
     *
     * @param target target
     */
    protected static void onResume(Activity target) {
        if (!LIFE_CYCLE_LISTENERS.isEmpty()) {
            for (int i = 0; i < LIFE_CYCLE_LISTENERS.size(); i++) {
                LIFE_CYCLE_LISTENERS.get(i).onResume(target);
            }
        }
        currentActivity = target;
    }
}
