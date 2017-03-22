package com.liangmayong.base.basic.expands.webkit.abstracts;

/**
 * Created by LiangMaYong on 2017/3/22.
 */
public interface AbstractWebkitDownloadListener {
    void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength);
}
