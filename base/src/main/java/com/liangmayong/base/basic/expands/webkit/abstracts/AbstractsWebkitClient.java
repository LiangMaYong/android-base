package com.liangmayong.base.basic.expands.webkit.abstracts;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.liangmayong.base.basic.expands.webkit.config.WebConfig;

/**
 * Created by LiangMaYong on 2017/2/18.
 */
public class AbstractsWebkitClient extends android.webkit.WebViewClient {

    private AbstractsWebkitInterceptor interceptor = null;
    private AbstractsWebkitHeaders headers = null;
    private AbstractsWebkitDeviceListener deviceListener = null;
    private boolean mPageFinished = false;

    public AbstractsWebkitClient(AbstractsWebkitInterceptor interceptor, AbstractsWebkitHeaders headers, AbstractsWebkitDeviceListener deviceListener) {
        this.interceptor = interceptor;
        this.headers = headers;
        this.deviceListener = deviceListener;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (!mPageFinished) {
            mPageFinished = true;
            if (url != null && !"about:blank".equals(url)) {
                if (view instanceof AbstractsWebKit) {
                    AbstractsWebKit webKit = (AbstractsWebKit) view;
                    String jsName = WebConfig.getConfig("jsBridgeName", "jsBridge");
                    webKit.injectionAssetsJS("javascript/android_js_bridge.js", jsName);
                    try {
                        webKit.call(jsName + ".init", deviceListener.generateDevice(view.getContext()));
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mPageFinished = false;
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String url = request.getUrl().toString();
            if (view instanceof AbstractsWebKit) {
                boolean flag = false;
                if (interceptor != null) {
                    flag = interceptor.shouldOverrideUrlLoading((AbstractsWebKit) view, url);
                }
                if (flag) {
                    return true;
                }
            }
            String lowurl = url.toLowerCase();
            if (lowurl.startsWith("http:") || lowurl.startsWith("https:")) {
                view.loadUrl(url, headers != null ? headers.generateHeaders() : null);
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (view instanceof AbstractsWebKit) {
            boolean flag = false;
            if (interceptor != null) {
                flag = interceptor.shouldOverrideUrlLoading((AbstractsWebKit) view, url);
            }
            if (flag) {
                return true;
            }
        }
        String lowurl = url.toLowerCase();
        if (lowurl.startsWith("http:") || lowurl.startsWith("https:")) {
            view.loadUrl(url, headers != null ? headers.generateHeaders() : null);
            return true;
        }
        return true;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }
}
