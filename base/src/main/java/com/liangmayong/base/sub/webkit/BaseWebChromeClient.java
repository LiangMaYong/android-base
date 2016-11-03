package com.liangmayong.base.sub.webkit;

import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by LiangMaYong on 2016/11/3.
 */
public class BaseWebChromeClient extends WebChromeClient {

    @Override
    public boolean onJsAlert(WebView view, String url, String message,
                             JsResult result) {
        result.confirm();
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url,
                               String message, JsResult result) {
        result.confirm();
        return true;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url,
                              String message, String defaultValue,
                              JsPromptResult result) {
        result.confirm();
        return true;
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin,
                                                   GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);
    }
}
