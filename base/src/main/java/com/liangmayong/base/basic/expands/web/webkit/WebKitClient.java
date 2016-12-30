package com.liangmayong.base.basic.expands.web.webkit;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import java.util.List;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/11/1.
 */
public class WebKitClient extends android.webkit.WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String url = request.getUrl().toString().toLowerCase();
            List<WebKitInterceptor> widgets = generateInterceptors();
            if (widgets != null && widgets.size() > 0) {
                for (int i = 0; i < widgets.size(); i++) {
                    if (url.startsWith(widgets.get(i).getScheme(), widgets.get(i).getToffset())) {
                        boolean flag = widgets.get(i).interceptorUrlLoading(view, url);
                        if (flag) {
                            return true;
                        }
                    }
                }
            }
            if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url, generateHeaders());
                return true;
            }
            // Otherwise allow the OS to handle things like tel, mailto, etc.
            if (url.startsWith("tel:") || url.startsWith("email:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        url = url.toLowerCase();
        List<WebKitInterceptor> interceptors = generateInterceptors();
        if (interceptors != null && interceptors.size() > 0) {
            for (int i = 0; i < interceptors.size(); i++) {
                if (url.startsWith(interceptors.get(i).getScheme(), interceptors.get(i).getToffset())) {
                    boolean flag = interceptors.get(i).interceptorUrlLoading(view, url);
                    if (flag) {
                        return true;
                    }
                }
            }
        }
        if (url.startsWith("http:") || url.startsWith("https:")) {
            view.loadUrl(url, generateHeaders());
            return true;
        }
        // Otherwise allow the OS to handle things like tel, mailto, etc.
        if (url.startsWith("tel:") || url.startsWith("email:")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);
            return true;
        }
        return true;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (shouldInterceptRequest(request.getUrl().toString())) {
                return new WebResourceResponse(null, null, null);
            }
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (shouldInterceptRequest(url)) {
                return new WebResourceResponse(null, null, null);
            }
        }
        return super.shouldInterceptRequest(view, url);
    }

    private boolean shouldInterceptRequest(String requestUrl) {
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (!view.getSettings().getLoadsImagesAutomatically()) {
            view.getSettings().setLoadsImagesAutomatically(true);
        }
    }

    /**
     * getHeaders
     *
     * @return headers
     */
    protected Map<String, String> generateHeaders() {
        return null;
    }

    /**
     * getHeaders
     *
     * @return headers
     */
    protected List<WebKitInterceptor> generateInterceptors() {
        return null;
    }
}
