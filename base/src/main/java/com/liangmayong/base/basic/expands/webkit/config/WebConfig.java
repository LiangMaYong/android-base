package com.liangmayong.base.basic.expands.webkit.config;

import com.liangmayong.base.basic.expands.webkit.abstracts.AbstractWebkitDeviceListener;
import com.liangmayong.base.basic.expands.webkit.abstracts.AbstractWebkitDownloadListener;
import com.liangmayong.base.basic.expands.webkit.abstracts.AbstractWebkitLoadingInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/2/18.
 */
public class WebConfig {
    // CONFIGS
    private static final Map<String, String> CONFIGS = new HashMap<String, String>();
    // HEADERS
    private static final Map<String, String> HEADERS = new HashMap<String, String>();
    // INTERCEPTORS
    private static final List<AbstractWebkitLoadingInterceptor> INTERCEPTORS = new ArrayList<AbstractWebkitLoadingInterceptor>();

    private static AbstractWebkitDeviceListener deviceListener = null;

    private static AbstractWebkitDownloadListener downloadListener = null;

    /**
     * interceptor
     *
     * @param interceptor interceptor
     */
    public static void addInterceptor(AbstractWebkitLoadingInterceptor interceptor) {
        if (interceptor != null) {
            removeInterceptor(interceptor.getScheme());
            INTERCEPTORS.add(interceptor);
        }
    }

    public static AbstractWebkitDeviceListener getDeviceListener() {
        return deviceListener;
    }

    public static void setDeviceListener(AbstractWebkitDeviceListener deviceListener) {
        WebConfig.deviceListener = deviceListener;
    }

    public static AbstractWebkitDownloadListener getDownloadListener() {
        return downloadListener;
    }

    public static void setDownloadListener(AbstractWebkitDownloadListener downloadListener) {
        WebConfig.downloadListener = downloadListener;
    }

    /**
     * removeWebWidget
     *
     * @param scheme scheme
     */
    public static void removeInterceptor(String scheme) {
        for (int i = 0; i < INTERCEPTORS.size(); i++) {
            if (INTERCEPTORS.get(i).getScheme().equals(scheme)) {
                INTERCEPTORS.remove(i);
            }
        }
    }

    /**
     * addHeader
     *
     * @param key   key
     * @param value value
     */
    public static void addHeader(String key, String value) {
        if (value == null || "".equals(value)) {
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
     * addHeader
     *
     * @param key   key
     * @param value value
     */
    public static void setConfig(String key, String value) {
        if (value == null || "".equals(value)) {
            if (CONFIGS.containsKey(key)) {
                CONFIGS.remove(key);
            }
        } else {
            CONFIGS.put(key, value);
        }
    }

    /**
     * getConfig
     *
     * @param key      key
     * @param defvalue defvalue
     * @return param
     */
    public static String getConfig(String key, String defvalue) {
        if (CONFIGS.containsKey(key)) {
            return CONFIGS.get(key);
        }
        return defvalue;
    }


    /**
     * clearHeaders
     */
    public static void clearHeaders() {
        HEADERS.clear();
    }

    /**
     * getHeaders
     *
     * @return headers
     */
    public static Map<String, String> getHeaders() {
        return HEADERS;
    }

    /**
     * getConfigs
     *
     * @return configs
     */
    public static Map<String, String> getConfigs() {
        return CONFIGS;
    }

    /**
     * getInterceptors
     *
     * @return interceptors
     */
    public static List<AbstractWebkitLoadingInterceptor> getInterceptors() {
        return INTERCEPTORS;
    }
}
