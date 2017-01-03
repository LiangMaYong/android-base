package com.liangmayong.base.basic.expands.web.webkit;

/**
 * Created by LiangMaYong on 2016/11/1.
 */

public abstract class WebKitInterceptor {

    private final String scheme;

    public WebKitInterceptor(String scheme) {
        this.scheme = scheme.toLowerCase();
    }

    public String getScheme() {
        return scheme;
    }

    public abstract boolean interceptorUrlLoading(WebKit web, WebKitUrl url);
}
