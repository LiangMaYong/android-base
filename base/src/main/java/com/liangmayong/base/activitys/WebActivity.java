package com.liangmayong.base.activitys;

import android.annotation.SuppressLint;

import com.liangmayong.base.fragments.WebFragment;
import com.liangmayong.base.interfaces.BaseInterface;
import com.liangmayong.base.sub.BaseSubActivity;
import com.liangmayong.base.sub.BaseSubFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/10/17.
 */

public class WebActivity extends BaseSubActivity {

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
    public BaseSubFragment generateSubFragment() {
        String title = getIntent().getStringExtra(BaseInterface.WEB_EXTRA_TITLE);
        String url = getIntent().getStringExtra(BaseInterface.WEB_EXTRA_URL);
        return new WebActFragment(title, url);
    }

    @SuppressLint("ValidFragment")
    private static class WebActFragment extends WebFragment {

        public WebActFragment(String title, String url) {
            super(title, url);
        }

        @Override
        protected Map<String, String> generateHeaders() {
            return HEADERS;
        }
    }
}
