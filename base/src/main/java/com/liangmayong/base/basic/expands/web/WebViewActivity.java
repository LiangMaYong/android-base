package com.liangmayong.base.basic.expands.web;

import com.liangmayong.base.basic.expands.web.fragments.FlowWebViewFragment;
import com.liangmayong.base.basic.expands.web.webkit.WebKitInterceptor;
import com.liangmayong.base.basic.flow.FlowBaseActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/10/17.
 */

public class WebViewActivity extends FlowBaseActivity {

    private static final Map<String, String> WEB_VIEW_PARAMS = new HashMap<String, String>();
    // WEB_VIEW_HEADERS
    private static final Map<String, String> WEB_VIEW_HEADERS = new HashMap<String, String>();
    // WEB_VIEW_INTERCEPTORS
    private static final List<WebKitInterceptor> WEB_VIEW_INTERCEPTORS = new ArrayList<WebKitInterceptor>();

    /**
     * interceptor
     *
     * @param interceptor interceptor
     */
    public static void addWebViewInterceptor(WebKitInterceptor interceptor) {
        if (interceptor != null) {
            removeWebViewInterceptor(interceptor.getScheme());
            WEB_VIEW_INTERCEPTORS.add(interceptor);
        }
    }

    /**
     * removeWebWidget
     *
     * @param scheme scheme
     */
    public static void removeWebViewInterceptor(String scheme) {
        for (int i = 0; i < WEB_VIEW_INTERCEPTORS.size(); i++) {
            if (WEB_VIEW_INTERCEPTORS.get(i).getScheme().equals(scheme)) {
                WEB_VIEW_INTERCEPTORS.remove(i);
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
            if (WEB_VIEW_HEADERS.containsKey(key)) {
                WEB_VIEW_HEADERS.remove(key);
            }
        } else {
            WEB_VIEW_HEADERS.put(key, value);
        }
    }

    /**
     * addHeaders
     *
     * @param headers headers
     */
    public static void addHeaders(Map<String, String> headers) {
        if (headers != null) {
            WEB_VIEW_HEADERS.putAll(headers);
        }
    }


    /**
     * addHeader
     *
     * @param key   key
     * @param value value
     */
    public static void addParam(String key, String value) {
        if (value == null || "".equals(value)) {
            if (WEB_VIEW_PARAMS.containsKey(key)) {
                WEB_VIEW_PARAMS.remove(key);
            }
        } else {
            WEB_VIEW_PARAMS.put(key, value);
        }
    }

    /**
     * getParam
     *
     * @param key      key
     * @param defvalue defvalue
     * @return param
     */
    public static String getParam(String key, String defvalue) {
        if (WEB_VIEW_PARAMS.containsKey(key)) {
            return WEB_VIEW_PARAMS.get(key);
        }
        return defvalue;
    }

    /**
     * setJsBridgeName
     *
     * @param name name
     */
    public static void setJsBridgeName(String name) {
        addParam("js_bridge_name", name);
    }


    /**
     * setJsBridgeName
     *
     * @param defname defname
     */
    public static String getJsBridgeName(String defname) {
        return getParam("js_bridge_name", defname);
    }

    /**
     * clearHeaders
     */
    public static void clearHeaders() {
        WEB_VIEW_HEADERS.clear();
    }

    @Override
    protected FlowBaseFragment getFristFragment() {
        return new WebFragment().initArguments(getIntent().getExtras());
    }

    public static class WebFragment extends FlowWebViewFragment {

        @Override
        protected Map<String, String> getHeaders() {
            Map<String, String> headers = super.getHeaders();
            headers.putAll(WEB_VIEW_HEADERS);
            return headers;
        }

        @Override
        protected String getJsBridgeName() {
            return getParam("js_bridge_name", "jsBridge");
        }

        @Override
        protected List<WebKitInterceptor> getWebKitInterceptors() {
            List<WebKitInterceptor> list = super.getWebKitInterceptors();
            list.addAll(WEB_VIEW_INTERCEPTORS);
            return list;
        }
    }
}
