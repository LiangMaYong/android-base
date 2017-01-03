package com.liangmayong.base.basic.expands.web.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.liangmayong.base.R;
import com.liangmayong.base.basic.expands.web.webkit.WebKit;
import com.liangmayong.base.basic.expands.web.webkit.WebKitChromeClient;
import com.liangmayong.base.basic.expands.web.webkit.WebKitClient;
import com.liangmayong.base.basic.expands.web.webkit.WebKitInterceptor;
import com.liangmayong.base.basic.expands.web.webkit.WebKitJsListener;
import com.liangmayong.base.basic.expands.web.webkit.WebKitUrl;
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.basic.interfaces.IBasic;
import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.interfaces.IRefresh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
public class FlowWebViewFragment extends FlowBaseFragment {

    //refresh
    private IRefresh refresh;
    //webKit
    private WebKit webKit;
    //title
    private String title = "";
    //url
    private String url = "";
    //isRefreshEnabled
    private boolean refreshEnabled = false;
    // webKitJsListener
    private WebKitJsListener webKitJsListener;
    // mPageFinished
    private boolean mPageFinished = false;

    /**
     * newInstance
     *
     * @param title title
     * @param url   url
     * @return FlowWebViewFragment
     */
    public static FlowWebViewFragment newInstance(String title, String url) {
        Bundle extras = new Bundle();
        extras.putString(IBasic.WEB_EXTRA_TITLE, title);
        extras.putString(IBasic.WEB_EXTRA_URL, url);
        return (FlowWebViewFragment) new FlowWebViewFragment().initArguments(extras);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.title = getArguments().getString(IBasic.WEB_EXTRA_TITLE);
        this.url = getArguments().getString(IBasic.WEB_EXTRA_URL);
    }

    /**
     * initWebView
     *
     * @param rootView rootView
     */
    protected void initWebView(WebKit rootView) {
    }

    /**
     * getWebView
     *
     * @return webKit
     */
    public WebKit getWebView() {
        return webKit;
    }

    @Override
    protected final void initViews(View rootView) {
        webKit = new WebKit(getActivity());
        webKit.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        refresh = (IRefresh) rootView.findViewById(R.id.base_web_refreshLayout);
        refresh.addView(webKit);
        refresh.setEnabled(refreshEnabled);
        refresh.setChildView(webKit);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webKit.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        refresh.setOnRefreshListener(new IRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
                reload();
            }
        });
        if (Build.VERSION.SDK_INT >= 9) {
            webKit.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        webKit.setWebViewClient(new WebKitClient() {
            @Override
            protected Map<String, String> generateHeaders() {
                return FlowWebViewFragment.this.getHeaders();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!mPageFinished) {
                    mPageFinished = true;
                    webKit.injectionAssetsJS("javascript/jsBridge.js", getJsBridgeName());
                    setTitle(view.getTitle());
                }
            }

            @Override
            protected List<WebKitInterceptor> generateInterceptors() {
                return FlowWebViewFragment.this.getWebKitInterceptors();
            }
        });
        webKit.getSettings().setJavaScriptEnabled(true);
        webKit.getSettings().setDomStorageEnabled(true);
        webKit.getSettings().setDatabaseEnabled(true);
        webKit.getSettings().setBuiltInZoomControls(false);
        webKit.getSettings().setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= 19) {
            webKit.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webKit.getSettings().setLoadsImagesAutomatically(false);
        }
        webKit.loadData("", "text/html", null);
        webKit.getSettings().setDefaultTextEncodingName("UTF-8");
        webKit.setWebChromeClient(new WebKitChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    webKit.onPageFinished();
                } else {
                    mPageFinished = false;
                }
                setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                if (webKitJsListener != null) {
                    webKitJsListener.onJsAlert(view, url, message, result);
                }
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                if (webKitJsListener != null) {
                    webKitJsListener.onJsConfirm(view, url, message, result);
                }
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                if (webKitJsListener != null) {
                    webKitJsListener.onJsBeforeUnload(view, url, message, result);
                }
                return super.onJsBeforeUnload(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                if (webKitJsListener != null) {
                    webKitJsListener.onJsPrompt(view, url, message, defaultValue, result);
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
        webKit.loadUrl(url, getHeaders());
        initData();
        initWebView(webKit);
    }


    /**
     * setProgress
     *
     * @param progress progress
     */
    private void setProgress(int progress) {
        if (getDefaultToolbar() != null) {
            if (progress >= 98) {
                progress = 0;
            }
            getDefaultToolbar().setProgress(progress);
        }
    }

    /**
     * initData
     */
    private void initData() {
        if (url == null || "".equals(url) || "null".equals(url)) {
            url = "about:blank";
        }
        setTitle(title);
    }

    /**
     * setWebKitJsListener
     *
     * @param webKitJsListener webKitJsListener
     */
    public void setWebKitJsListener(WebKitJsListener webKitJsListener) {
        this.webKitJsListener = webKitJsListener;
    }

    /**
     * reload
     */
    public void reload() {
        webKit.reload();
    }

    /**
     * setTitle
     *
     * @param title title
     */
    private void setTitle(String title) {
        if (getDefaultToolbar() == null) {
            return;
        }
        if (this.title != null && !"".equals(this.title) && !"null".equals(this.title)) {
            getDefaultToolbar().leftTwo().getIconView().setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            getDefaultToolbar().leftTwo().getIconView().setPadding(0, 0, 0, 0);
            getDefaultToolbar().leftTwo().text(this.title);
            return;
        }
        getDefaultToolbar().leftTwo().getIconView().setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        getDefaultToolbar().leftTwo().getIconView().setPadding(0, 0, 0, 0);
        getDefaultToolbar().leftTwo().text(title);
    }

    @Override
    protected int getContaierLayoutId() {
        return R.layout.base_default_fragment_web;
    }

    /**
     * isRefreshEnabled
     *
     * @return isRefreshEnabled
     */
    public boolean isRefreshEnabled() {
        return refreshEnabled;
    }

    /**
     * setRefreshEnabled
     *
     * @param refreshEnabled isRefreshEnabled
     */
    public void setRefreshEnabled(boolean refreshEnabled) {
        this.refreshEnabled = refreshEnabled;
        if (refresh != null) {
            refresh.setEnabled(refreshEnabled);
        }
    }

    @Override
    public void initDefaultToolbar(DefaultToolbar defaultToolbar) {
        defaultToolbar.leftOne().icon(Icon.icon_back).click(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        defaultToolbar.rightTwo().text("");
    }

    /**
     * getJsBridgeName
     *
     * @return jsBridgeName
     */
    protected String getJsBridgeName() {
        return "jsBridge";
    }

    /**
     * generateWeigets
     *
     * @return weiget
     */
    protected List<WebKitInterceptor> getWebKitInterceptors() {
        List<WebKitInterceptor> interceptors = new ArrayList<WebKitInterceptor>();
        interceptors.add(new WebKitInterceptor("toast:") {
            @Override
            public boolean interceptorUrlLoading(WebKit web, WebKitUrl url) {
                showToast(url.getContent());
                return true;
            }
        });
        interceptors.add(new WebKitInterceptor("toolbar-message:") {
            @Override
            public boolean interceptorUrlLoading(WebKit web, WebKitUrl url) {
                ISkin skin = SkinManager.get();
                int textColor = skin.getThemeTextColor();
                int bgColor = skin.getThemeColor();
                try {
                    textColor = Color.parseColor(url.getParams().get("textColor"));
                } catch (Exception e) {
                }
                try {
                    bgColor = Color.parseColor(url.getParams().get("bgColor"));
                } catch (Exception e) {
                }
                getDefaultToolbar().message().show(url.getContent(), textColor, bgColor, 1500);
                return true;
            }
        });
        interceptors.add(new WebKitInterceptor("action:") {
            @Override
            public boolean interceptorUrlLoading(WebKit web, WebKitUrl url) {
                if ("finish".equals(url.getContent())) {
                    action_finish();
                } else if ("reload".equals(url.getContent())) {
                    action_reload();
                } else if ("forward".equals(url.getContent())) {
                    action_forward();
                } else if ("back".equals(url.getContent())) {
                    action_back();
                } else if ("back_finish".equals(url.getContent())) {
                    onBackPressed();
                } else if ("hide_toolbar".equals(url.getContent())) {
                    if (getDefaultToolbar() != null) {
                        getDefaultToolbar().gone();
                    }
                } else if ("show_toolbar".equals(url.getContent())) {
                    if (getDefaultToolbar() != null) {
                        getDefaultToolbar().visible();
                    }
                } else if ("open".equals(url.getContent())) {
                    String adds = url.getParams().get("url");
                    if (adds != null && !"".equals(adds)) {
                        webKit.loadUrl(adds, getHeaders());
                    }
                }
                return true;
            }
        });
        return interceptors;
    }

    /**
     * getHeaders
     *
     * @return headers
     */
    protected Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("device", "jsAndroid");
        return headers;
    }

    @Override
    public boolean onBackPressed() {
        if (webKit != null && webKit.canGoBack()) {
            webKit.goBack();
            if (getDefaultToolbar() != null) {
                getDefaultToolbar().rightOne().icon(Icon.icon_close).click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action_finish();
                    }
                });
            }
        } else {
            action_finish();
        }
        return true;
    }

    private void action_reload() {
        reload();
    }

    private void action_back() {
        if (webKit != null && webKit.canGoBack()) {
            webKit.goBack();
        }
    }

    private void action_forward() {
        if (webKit != null && webKit.canGoForward()) {
            webKit.goForward();
        }
    }

    private void action_finish() {
        try {
            webKit.stopLoading();
            webKit.loadUrl("about:blank");
            refresh.removeAllViews();
            webKit.removeAllViews();
            webKit.destroy();
        } catch (Exception e) {
        }
        closeSelf();
    }
}
