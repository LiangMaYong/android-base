package com.liangmayong.base.basic.expands.web.fragments;

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
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.basic.interfaces.IBasic;
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

    //base_refresh_layout
    private IRefresh base_refresh_layout;
    //base_webview
    private WebKit base_webview;
    //title
    private String title = "";
    //url
    private String url = "";
    //isRefreshEnabled
    private boolean refreshEnabled = false;
    private int progress = 0;
    private WebKitJsListener webKitJsListener;

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
     * @return base_webview
     */
    public WebKit getWebView() {
        return base_webview;
    }

    @Override
    protected final void initViews(View rootView) {
        base_webview = new WebKit(getActivity());
        base_webview.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        base_refresh_layout = (IRefresh) rootView.findViewById(R.id.base_web_refreshLayout);
        base_refresh_layout.addView(base_webview);
        base_refresh_layout.setEnabled(refreshEnabled);
        base_refresh_layout.setChildView(base_webview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            base_webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        base_refresh_layout.setOnRefreshListener(new IRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                base_refresh_layout.setRefreshing(false);
                reload();
            }
        });
        if (Build.VERSION.SDK_INT >= 9) {
            base_webview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        base_webview.setWebViewClient(new WebKitClient() {
            @Override
            protected Map<String, String> generateHeaders() {
                return FlowWebViewFragment.this.getHeaders();
            }

            @Override
            protected List<WebKitInterceptor> generateInterceptors() {
                return FlowWebViewFragment.this.getWebKitInterceptors();
            }
        });
        base_webview.getSettings().setJavaScriptEnabled(true);
        base_webview.getSettings().setDomStorageEnabled(true);
        base_webview.getSettings().setDatabaseEnabled(true);
        base_webview.getSettings().setBuiltInZoomControls(false);
        base_webview.getSettings().setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= 19) {
            base_webview.getSettings().setLoadsImagesAutomatically(true);
        } else {
            base_webview.getSettings().setLoadsImagesAutomatically(false);
        }
        base_webview.loadData("", "text/html", null);
        base_webview.getSettings().setDefaultTextEncodingName("UTF-8");
        base_webview.setWebChromeClient(new WebKitChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progress != newProgress && newProgress == 100) {
                    // injection ja
                    base_webview.injectionAssetsJS("javascript/jsBridge.js", getJsBridgeName());
                    setTitle(view.getTitle());
                    setProgress(0);
                } else {
                    setProgress(newProgress);
                }
                progress = newProgress;
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
        base_webview.loadUrl(url, getHeaders());
        initData();
        initWebView(base_webview);
    }


    /**
     * setProgress
     *
     * @param progress progress
     */
    private void setProgress(int progress) {
        if (getDefaultToolbar() != null) {
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
        base_webview.reload();
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
        if (base_refresh_layout != null) {
            base_refresh_layout.setEnabled(refreshEnabled);
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
            public boolean interceptorUrlLoading(WebView web, String url) {
                String msg = url.substring(getScheme().length(), url.length());
                showToast(msg);
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
        if (base_webview != null && base_webview.canGoBack()) {
            base_webview.goBack();
            if (getDefaultToolbar() != null) {
                getDefaultToolbar().rightOne().icon(Icon.icon_close).click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            base_webview.stopLoading();
                            base_webview.loadUrl("about:blank");
                            base_refresh_layout.removeAllViews();
                            base_webview.removeAllViews();
                            base_webview.destroy();
                        } catch (Exception e) {
                        }
                        closeSelf();
                    }
                });
            }
        } else {
            try {
                base_webview.stopLoading();
                base_webview.loadUrl("about:blank");
                base_refresh_layout.removeAllViews();
                base_webview.removeAllViews();
                base_webview.destroy();
            } catch (Exception e) {
            }
            closeSelf();
        }
        return true;
    }
}
