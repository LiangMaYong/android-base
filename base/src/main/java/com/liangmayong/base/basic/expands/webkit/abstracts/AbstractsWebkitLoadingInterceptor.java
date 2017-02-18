package com.liangmayong.base.basic.expands.webkit.abstracts;

import com.liangmayong.base.basic.expands.webkit.abstracts.AbstractsWebKit;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/11/1.
 */

public abstract class AbstractsWebkitLoadingInterceptor {

    private final String scheme;

    public AbstractsWebkitLoadingInterceptor(String scheme) {
        this.scheme = scheme.toLowerCase();
    }

    public String getScheme() {
        return scheme;
    }

    public abstract boolean interceptorUrlLoading(AbstractsWebKit web, UrlParam url);

    /**
     * Created by LiangMaYong on 2017/1/3.
     */

    public static class UrlParam {

        private String url;
        private final String scheme;
        private final String content;
        private final Map<String, String> params = new HashMap<String, String>();

        public UrlParam(String url, String scheme) {
            try {
                this.url = URLDecoder.decode(url, "utf-8");
            } catch (Exception e) {
                this.url = url;
            }
            this.scheme = scheme;
            String[] us = this.url.substring(scheme.length(), this.url.length()).split("\\?");
            this.content = us[0];
            if (us.length >= 2) {
                String paramsStr = us[1];
                String[] pas = paramsStr.split("&");
                for (int i = 0; i < pas.length; i++) {
                    String[] vs = pas[i].split("=");
                    if (vs.length >= 2) {
                        params.put(vs[0], vs[1]);
                    }
                }
            }
        }

        public String getUrl() {
            return url;
        }

        public String getScheme() {
            return scheme;
        }

        public String getContent() {
            return content;
        }

        public Map<String, String> getParams() {
            return params;
        }
    }
}
