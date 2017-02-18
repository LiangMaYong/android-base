package com.liangmayong.base.basic.expands.webkit.abstracts;

import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;

/**
 * Created by LiangMaYong on 2017/2/18.
 */
public interface AbstractsWebkitJsListener {

    boolean onJsAlert(WebView view, String url, String message, JsResult result);

    boolean onJsConfirm(WebView view, String url, String message, JsResult result);

    boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result);

    boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result);
}
