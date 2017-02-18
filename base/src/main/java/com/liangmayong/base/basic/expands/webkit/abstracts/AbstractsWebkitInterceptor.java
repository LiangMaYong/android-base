package com.liangmayong.base.basic.expands.webkit.abstracts;

import java.util.List;

/**
 * Created by LiangMaYong on 2017/2/18.
 */

public interface AbstractsWebkitInterceptor {
    boolean shouldOverrideUrlLoading(AbstractsWebKit view, String url);
}
