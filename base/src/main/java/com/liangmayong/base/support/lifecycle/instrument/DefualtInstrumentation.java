package com.liangmayong.base.support.lifecycle.instrument;

import android.app.Activity;
import android.os.Bundle;

import com.liangmayong.base.support.lifecycle.ActivityLifeCycle;

/**
 * Created by LiangMaYong on 2016/11/8.
 */

public class DefualtInstrumentation extends ProxyInstrumentation {

    @Override
    public void callActivityOnCreate(Activity target, Bundle icicle) {
        ActivityLifeCycle.onCreate(target, icicle);
        super.callActivityOnCreate(target, icicle);
    }

    @Override
    public void callActivityOnDestroy(Activity target) {
        ActivityLifeCycle.onDestroy(target);
        super.callActivityOnDestroy(target);
    }


    @Override
    public void callActivityOnPause(Activity target) {
        ActivityLifeCycle.onPause(target);
        super.callActivityOnPause(target);
    }

    @Override
    public void callActivityOnRestart(Activity target) {
        ActivityLifeCycle.onRestart(target);
        super.callActivityOnRestart(target);
    }

    @Override
    public void callActivityOnResume(Activity target) {
        ActivityLifeCycle.onResume(target);
        super.callActivityOnResume(target);
    }

    @Override
    public void callActivityOnStart(Activity target) {
        ActivityLifeCycle.onStart(target);
        super.callActivityOnStart(target);
    }

    @Override
    public void callActivityOnStop(Activity target) {
        ActivityLifeCycle.onStop(target);
        super.callActivityOnStop(target);
    }
}
