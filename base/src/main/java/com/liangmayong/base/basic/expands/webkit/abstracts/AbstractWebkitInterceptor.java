package com.liangmayong.base.basic.expands.webkit.abstracts;

/**
 * Created by LiangMaYong on 2017/2/18.
 */

public interface AbstractWebkitInterceptor {
    boolean shouldOverrideUrlLoading(AbstractWebKit view, String url);
}
