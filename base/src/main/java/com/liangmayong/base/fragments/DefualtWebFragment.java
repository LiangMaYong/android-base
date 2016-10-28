package com.liangmayong.base.fragments;

import android.annotation.SuppressLint;

import com.liangmayong.base.sub.BaseSubWebFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/10/27.
 */
@SuppressLint("ValidFragment")
public class DefualtWebFragment extends BaseSubWebFragment {


    public DefualtWebFragment(String title, String url) {
        super(title, url);
    }

    // HEADERS
    private static final Map<String, String> HEADERS = new HashMap<String, String>();

    /**
     * addHeader
     *
     * @param key   key
     * @param value value
     */
    public static void addHeader(String key, String value) {
        if (value == null) {
            if (HEADERS.containsKey(key)) {
                HEADERS.remove(key);
            }
        } else {
            HEADERS.put(key, value);
        }
    }

    /**
     * addHeaders
     *
     * @param headers headers
     */
    public static void addHeaders(Map<String, String> headers) {
        if (headers != null) {
            HEADERS.putAll(headers);
        }
    }

    /**
     * clearHeaders
     */
    public static void clearHeaders() {
        HEADERS.clear();
    }

    @Override
    protected Map<String, String> generateHeaders() {
        return HEADERS;
    }

}