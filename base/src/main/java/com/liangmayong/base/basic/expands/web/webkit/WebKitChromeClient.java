package com.liangmayong.base.basic.expands.web.webkit;

import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;

/**
 * Created by LiangMaYong on 2016/11/3.
 */
public class WebKitChromeClient extends WebChromeClient {

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin,
                                                   GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);
    }
}
