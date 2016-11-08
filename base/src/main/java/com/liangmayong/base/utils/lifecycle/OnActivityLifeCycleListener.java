package com.liangmayong.base.utils.lifecycle;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by liangmayong on 2016/9/18.
 */
public interface OnActivityLifeCycleListener {
    /**
     * onCreate
     *
     * @param target             target
     * @param savedInstanceState savedInstanceState
     */
    void onCreate(Activity target, Bundle savedInstanceState);

    /**
     * onStart
     *
     * @param target target
     */
    void onStart(Activity target);

    /**
     * onRestart
     *
     * @param target target
     */
    void onRestart(Activity target);

    /**
     * onDestroy
     *
     * @param target target
     */
    void onDestroy(Activity target);

    /**
     * onPause
     *
     * @param target target
     */
    void onPause(Activity target);

    /**
     * onStop
     *
     * @param target target
     */
    void onStop(Activity target);

    /**
     * onResume
     *
     * @param target target
     */
    void onResume(Activity target);
}
