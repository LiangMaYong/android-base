package com.liangmayong.base.basic.expands.web.webkit;

import android.webkit.WebView;

/**
 * Created by LiangMaYong on 2016/11/1.
 */

public abstract class WebKitInterceptor {

    private final String scheme;
    private final int toffset;

    public WebKitInterceptor(String scheme, int toffset) {
        this.scheme = scheme.toLowerCase();
        this.toffset = toffset;
    }

    public WebKitInterceptor(String scheme) {
        this.scheme = scheme;
        this.toffset = 0;
    }

    public String getScheme() {
        return scheme;
    }

    public int getToffset() {
        return toffset;
    }

    public abstract boolean interceptorUrlLoading(WebView web, String url);
}
