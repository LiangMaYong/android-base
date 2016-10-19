package com.liangmayong.base.interfaces;

/**
 * Created by LiangMaYong on 2016/10/19.
 */
public interface BaseWebJavascriptInterface {

    void toast(String msg);

    void setPrefercens(String key, String value);

    String getPrefercens(String key);

    void open(String title,String url);

    void setHeader(String key, String value);

    String getHeader(String key);

    void finish();

    void close();

}
