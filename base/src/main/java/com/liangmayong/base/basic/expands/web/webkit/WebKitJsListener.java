package com.liangmayong.base.basic.expands.web.webkit;

import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;

/**
 * Created by liangmayong on 2017/1/2.
 */
public interface WebKitJsListener {

    boolean onJsAlert(WebView view, String url, String message, JsResult result);

    boolean onJsConfirm(WebView view, String url, String message, JsResult result);

    boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result);

    boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result);
}
