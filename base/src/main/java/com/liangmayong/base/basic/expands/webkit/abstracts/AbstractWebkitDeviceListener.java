package com.liangmayong.base.basic.expands.webkit.abstracts;

import android.content.Context;

import java.util.Map;

/**
 * Created by LiangMaYong on 2017/2/18.
 */
public interface AbstractWebkitDeviceListener {
    Map<String, String> generateDeviceInfo(Context context);
}
