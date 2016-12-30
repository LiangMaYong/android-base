package com.liangmayong.base.basic.expands.web.webkit;

import android.webkit.DownloadListener;

/**
 * Created by LiangMaYong on 2016/11/3.
 */

public interface WebKitDownloadListener extends DownloadListener {
    @Override
    @Deprecated
    void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength);
}
