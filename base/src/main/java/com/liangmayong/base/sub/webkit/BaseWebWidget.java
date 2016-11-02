package com.liangmayong.base.sub.webkit;

import android.webkit.WebView;

/**
 * Created by LiangMaYong on 2016/11/1.
 */

public abstract class BaseWebWidget {

    private String schemeName = "";
    private int toffset = 0;

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        if (schemeName != null) {
            this.schemeName = schemeName.toLowerCase();
        } else {
            this.schemeName = "";
        }
    }

    public int getToffset() {
        return toffset;
    }

    public void setToffset(int toffset) {
        this.toffset = toffset;
    }

    public abstract boolean overrideUrlLoading(WebView web, String url);
}
