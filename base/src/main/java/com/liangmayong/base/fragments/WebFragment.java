package com.liangmayong.base.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.liangmayong.base.R;
import com.liangmayong.base.interfaces.BaseWebJavascriptInterface;
import com.liangmayong.base.sub.BaseSubFragment;
import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.layouts.SwipeLayout;
import com.liangmayong.skin.Skin;

import java.util.Map;

/**
 * Created by LiangMaYong on 2016/10/17.
 */

@SuppressLint("ValidFragment")
public class WebFragment extends BaseSubFragment {

    public WebFragment(String title, String url) {
        this.title = title;
        this.url = url;
    }

    //base_webview
    private SwipeLayout base_refresh_layout;
    //base_webview
    private WebView base_webview;
    //title
    private String title = "";
    //url
    private String url = "";

    @Override
    protected void initSubView(View rootView) {
        base_webview = (WebView) rootView.findViewById(R.id.base_webview);
        base_refresh_layout = (SwipeLayout) rootView.findViewById(R.id.base_refreshLayout);
        base_refresh_layout.setEnabled(generateRefreshEnabled());
        base_refresh_layout.setColorSchemeColors(Skin.get().getThemeColor());
        base_refresh_layout.setViewGroup(base_webview);
        base_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                base_webview.reload();
                base_refresh_layout.setRefreshing(false);
            }
        });
        if (Build.VERSION.SDK_INT >= 9) {
            base_webview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        if (getDefualtToolbar() != null) {
            getDefualtToolbar().leftOne().iconToLeft(Icon.icon_back).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (base_webview != null && base_webview.canGoBack()) {
                        base_webview.goBack();
                        getDefualtToolbar().leftTwo().iconToLeft(Icon.icon_close).clicked(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        });
                    } else {
                        finish();
                    }
                }
            });
        }
        base_webview.setWebViewClient(new BaseWebViewClient());
        base_webview.getSettings().setJavaScriptEnabled(true);
        base_webview.getSettings().setDomStorageEnabled(true);
        base_webview.getSettings().setUseWideViewPort(true);
        base_webview.getSettings().setDatabaseEnabled(true);
        base_webview.getSettings().setAppCacheEnabled(true);
        Object webInterface = generateWebJavascriptInterface();
        if (webInterface != null) {
            base_webview.addJavascriptInterface(webInterface, generateWebJavascriptInterfaceName());
        }
        base_webview.loadData("", "text/html", null);
        base_webview.getSettings().setDefaultTextEncodingName("UTF-8");
        base_webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    getDefualtToolbar().setProgress(0);
                    setToolbarTitle(view.getTitle());
                } else {
                    getDefualtToolbar().setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        DownloadListener downloadListener = generateDownLoadListener();
        if (downloadListener != null) {
            base_webview.setDownloadListener(new BaseWebViewDownLoadListener());
        }
        base_webview.loadUrl(url, generateHeaders());
        setToolbarTitle(title);
    }


    /**
     * setToolbarTitle
     *
     * @param title title
     */
    private void setToolbarTitle(String title) {
        if (this.title != null && !"".equals(this.title) && !"null".equals(this.title)) {
            getDefualtToolbar().setTitle(this.title);
            return;
        }
        getDefualtToolbar().setTitle(title);
    }


    @Override
    protected int generateContainerViewId() {
        return R.layout.base_defualt_fragment_web;
    }

    /**
     * generateRefreshEnabled
     *
     * @return false
     */
    protected boolean generateRefreshEnabled() {
        return false;
    }

    @Override
    public void onRefreshSkin(Skin skin) {
        super.onRefreshSkin(skin);
        if (base_refresh_layout != null) {
            base_refresh_layout.setColorSchemeColors(skin.getThemeColor());
        }
    }

    /**
     * getHeaders
     *
     * @return headers
     */
    protected Map<String, String> generateHeaders() {
        return null;
    }

    /**
     * getWebJavascriptInterface
     *
     * @return web Interface
     */
    protected BaseWebJavascriptInterface generateWebJavascriptInterface() {
        return null;
    }

    /**
     * getWebInterfaceName
     *
     * @return web Interface
     */
    protected String generateWebJavascriptInterfaceName() {
        return "AndroidJS";
    }

    /**
     * getDownLoadListener
     *
     * @return DownloadListener
     */
    protected DownloadListener generateDownLoadListener() {
        return new BaseWebViewDownLoadListener();
    }

    /**
     * getWebViewClient
     *
     * @return WebViewClient
     */
    protected WebViewClient generateWebViewClient() {
        return new BaseWebViewClient();
    }

    /**
     * initData
     */
    private void initData() {
        if (url == null || "".equals(url) || "null".equals(url)) {
            url = "about:blank";
        }
        getDefualtToolbar().setTitle(title);
    }


    /**
     * BaseWebViewClient
     */
    private class BaseWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url, generateHeaders());
                return false;
            }
            // Otherwise allow the OS to handle things like tel, mailto, etc.
            if (url.startsWith("tel:") || url.startsWith("email:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if (isMp4(url)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return new WebResourceResponse(null, null, null);
                }
            }
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (isMp4(request.getUrl().toString())) {
                    return new WebResourceResponse(null, null, null);
                }
            }
            return super.shouldInterceptRequest(view, request);
        }

        private boolean isMp4(String url) {
            return false;
        }

    }

    /**
     * BaseWebViewDownLoadListener
     */
    private class BaseWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    @Override
    public boolean onBackPressed() {
        if (base_webview != null && base_webview.canGoBack()) {
            base_webview.goBack();
            if (getDefualtToolbar() != null) {
                getDefualtToolbar().leftTwo().iconToLeft(Icon.icon_close).clicked(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        } else {
            finish();
        }
        return true;
    }
}
