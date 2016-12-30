package com.liangmayong.base.basic.expands.web;

import android.annotation.SuppressLint;

import com.liangmayong.base.basic.expands.web.fragments.FlowWebViewFragment;
import com.liangmayong.base.basic.expands.web.webkit.WebKitInterceptor;
import com.liangmayong.base.basic.flow.FlowBaseActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.basic.interfaces.IBasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/10/17.
 */

public class WebViewActivity extends FlowBaseActivity {

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
        if (value == null) {
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
     * clearHeaders
     */
    public static void clearHeaders() {
        WEB_VIEW_HEADERS.clear();
    }

    @Override
    protected FlowBaseFragment getFristFragment() {
        String title = getIntent().getStringExtra(IBasic.WEB_EXTRA_TITLE);
        String url = getIntent().getStringExtra(IBasic.WEB_EXTRA_URL);
        return new WebFragment(title, url);
    }

    @SuppressLint("ValidFragment")
    public static class WebFragment extends FlowWebViewFragment {

        public WebFragment(String title, String url) {
            super(title, url);
        }

        @Override
        protected Map<String, String> getHeaders() {
            return WEB_VIEW_HEADERS;
        }

        @Override
        protected List<WebKitInterceptor> getWebKitInterceptors() {
            return WEB_VIEW_INTERCEPTORS;
        }
    }

}
