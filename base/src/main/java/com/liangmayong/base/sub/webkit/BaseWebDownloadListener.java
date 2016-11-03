package com.liangmayong.base.sub.webkit;

import android.webkit.DownloadListener;

/**
 * Created by LiangMaYong on 2016/11/3.
 */

public interface BaseWebDownloadListener extends DownloadListener {
    @Override
    @Deprecated
    void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength);
}
