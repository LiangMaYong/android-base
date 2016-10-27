package com.liangmayong.base.sub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.liangmayong.base.R;
import com.liangmayong.base.interfaces.BaseWebJavascriptInterface;
import com.liangmayong.base.utils.LogUtils;
import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.layouts.SwipeLayout;
import com.liangmayong.skin.Skin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/10/17.
 */

@SuppressLint("ValidFragment")
public class BaseSubWebFragment extends BaseSubFragment {

    public BaseSubWebFragment(String title, String url) {
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
        base_webview = new WebView(getActivity());
        base_refresh_layout = (SwipeLayout) rootView.findViewById(R.id.base_swipeLayout);
        base_refresh_layout.addView(base_webview);
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
                    onBackPressed();
                }
            });
        }
        base_webview.setWebViewClient(new BaseWebViewClient());
        base_webview.getSettings().setJavaScriptEnabled(true);
        base_webview.getSettings().setDomStorageEnabled(true);
        base_webview.getSettings().setUseWideViewPort(true);
        base_webview.getSettings().setDatabaseEnabled(true);
        base_webview.getSettings().setBuiltInZoomControls(false);
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

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                LogUtils.get("WebConsole").i(consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                super.onConsoleMessage(message, lineNumber, sourceID);
                LogUtils.get("WebConsole").i(message);
            }

        });
        DownloadListener downloadListener = generateDownLoadListener();
        if (downloadListener != null) {
            base_webview.setDownloadListener(new BaseWebViewDownLoadListener());
        }
        base_webview.loadUrl(url, generateHeaders());
        setToolbarTitle(title);
        removeSearchBoxJavaBridgeInterface();
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

    @SuppressLint("NewApi")
    private void removeSearchBoxJavaBridgeInterface() {
        if (hasHoneycomb() && !hasJellyBeanMR1()) {
            base_webview.removeJavascriptInterface("searchBoxJavaBridge_");
        }
    }

    private boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    private boolean hasJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * BaseWebViewClient
     */
    private class BaseWebViewClient extends WebViewClient {
        private boolean loadingFinished = true;
        private boolean redirect = false;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            redirect = true;
            loadingFinished = false;
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
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (shouldIntercept(request.getUrl().toString())) {
                    return new WebResourceResponse(null, null, null);
                }
            }
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                if (shouldIntercept(url)) {
                    return new WebResourceResponse(null, null, null);
                }
            }
            return super.shouldInterceptRequest(view, url);
        }

        private boolean shouldIntercept(String requestUrl) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            injectionAssetsJS("javascript/base.js");
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
        }
    }

    /**
     * injectionAssetsJS
     *
     * @param filename filename
     */
    public void injectionAssetsJS(String filename) {
        try {
            InputStream in = getActivity().getAssets().open(filename);
            byte buff[] = new byte[1024];
            int numread = 0;
            ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
            do {
                numread = in.read(buff);
                if (numread <= 0) {
                    break;
                }
                fromFile.write(buff, 0, numread);
            } while (true);
            String wholeJS = fromFile.toString();
            base_webview.loadUrl("javascript:" + wholeJS);
            fromFile.close();
        } catch (IOException e) {
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
                        try {
                            base_webview.stopLoading();
                            base_webview.loadUrl("about:blank");
                            base_refresh_layout.removeAllViews();
                            base_webview.removeAllViews();
                            base_webview.destroy();
                        } catch (Exception e) {
                        }
                        finish();
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
            finish();
        }
        return true;
    }
}
