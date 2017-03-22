package com.liangmayong.base.basic.expands.webkit.abstracts;

import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by LiangMaYong on 2017/2/18.
 */
public class AbstractWebkitChromeClient extends WebChromeClient {

    private AbstractWebkitJsListener jsListener = null;
    private AbstractWebkitProgressListener progressListener = null;

    public AbstractWebkitChromeClient(AbstractWebkitJsListener jsListener, AbstractWebkitProgressListener progressListener) {
        this.jsListener = jsListener;
        this.progressListener = progressListener;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (progressListener != null) {
            if (view instanceof AbstractWebKit) {
                progressListener.onProgressChanged((AbstractWebKit) view, newProgress);
                if (newProgress == 100) {
                    ((AbstractWebKit) view).onPageFinished();
                }
            }
        }
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin,
                                                   GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        if (jsListener != null) {
            jsListener.onJsAlert(view, url, message, result);
        }
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        if (jsListener != null) {
            jsListener.onJsConfirm(view, url, message, result);
        }
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        if (jsListener != null) {
            jsListener.onJsBeforeUnload(view, url, message, result);
        }
        return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        if (jsListener != null) {
            jsListener.onJsPrompt(view, url, message, defaultValue, result);
        }
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }
}
