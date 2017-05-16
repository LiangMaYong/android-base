package com.liangmayong.apkbox.bundle.api;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LiangMaYong on 2017/5/15.
 */
public interface ApkBundleViewAdapter {

    View newView(Context context, ViewGroup parent);

    void bindView(Context context, View view, Listener listener);

}
