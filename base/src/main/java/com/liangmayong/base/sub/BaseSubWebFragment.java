package com.liangmayong.base.sub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.liangmayong.base.R;
import com.liangmayong.base.interfaces.BaseWebJavascriptInterface;
import com.liangmayong.base.sub.webkit.BaseWebViewClient;
import com.liangmayong.base.sub.webkit.BaseWebWidget;
import com.liangmayong.base.utils.LogUtils;
import com.liangmayong.base.utils.StringUtils;
import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.layouts.SwipeLayout;
import com.liangmayong.base.widget.skin.Skin;

import java.util.List;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/10/17.
 */

@SuppressLint("ValidFragment")
public class BaseSubWebFragment extends BaseSubFragment {

    /**
     * Java call js function format with parameters
     */
    private static final String FUNC_FORMAT_WITH_PARAMETERS = "javascript:window.%s('%s')";

    /**
     * Java call js function format without parameters
     */
    private static final String FUNC_FORMAT = "javascript:window.%s()";

    public BaseSubWebFragment(String title, String url) {
        this.title = title;
        this.url = url;
    }

    //base_refresh_layout
    private SwipeLayout base_refresh_layout;
    //base_webview
    private WebView base_webview;
    //title
    private String title = "";
    //url
    private String url = "";
    //webViewClient
    private BaseWebViewClient webViewClient = null;

    /**
     * initWebView
     *
     * @param rootView rootView
     */
    protected void initWebView(View rootView) {
    }

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
                reload();
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
            if (generateShareEnabled()) {
                getDefualtToolbar().rightOne().iconToLeft(Icon.icon_share).clicked(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showShareDialog(base_webview.getTitle(), base_webview.getUrl());
                    }
                });
            }
        }
        webViewClient = generateWebViewClient();
        if (webViewClient != null) {
            base_webview.setWebViewClient(webViewClient);
        }
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
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                result.confirm();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url,
                                       String message, JsResult result) {
                result.confirm();
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url,
                                      String message, String defaultValue,
                                      JsPromptResult result) {
                result.confirm();
                return true;
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
        initWebView(rootView);
    }

    /**
     * showShareDialog
     *
     * @param title title
     * @param url   url
     */
    public void showShareDialog(String title, String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, null));
    }

    /**
     * reload
     */
    public void reload() {
        base_webview.reload();
    }

    /**
     * setToolbarTitle
     *
     * @param title title
     */
    private void setToolbarTitle(String title) {
        if (this.title != null && !"".equals(this.title) && !"null".equals(this.title)) {
            getDefualtToolbar().leftTwo().getIconView().setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            getDefualtToolbar().leftTwo().getIconView().setPadding(0, 0, 0, 0);
            getDefualtToolbar().leftTwo().text(this.title);
            return;
        }
        getDefualtToolbar().leftTwo().getIconView().setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        getDefualtToolbar().leftTwo().getIconView().setPadding(0, 0, 0, 0);
        getDefualtToolbar().leftTwo().text(title);
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

    /**
     * generateShareEnabled
     *
     * @return true
     */
    protected boolean generateShareEnabled() {
        return true;
    }

    @Override
    public void onRefreshSkin(Skin skin) {
        super.onRefreshSkin(skin);
        if (base_refresh_layout != null) {
            base_refresh_layout.setColorSchemeColors(skin.getThemeColor());
        }
    }

    /**
     * generateWeigets
     *
     * @return weiget
     */
    protected List<BaseWebWidget> generateWidgets() {
        return null;
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
    protected BaseWebViewClient generateWebViewClient() {
        return new BaseWebViewClient() {
            @Override
            protected Map<String, String> generateHeaders() {
                return BaseSubWebFragment.this.generateHeaders();
            }

            @Override
            protected List<BaseWebWidget> generateWidgets() {
                return BaseSubWebFragment.this.generateWidgets();
            }
        };
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
     * injectionAssetsJS
     *
     * @param filename filename
     */
    public void injectionAssetsJS(String filename) {
        if (webViewClient != null) {
            webViewClient.injectionAssetsJS(base_webview, filename);
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


    /**
     * callFunction
     *
     * @param functionName functionName
     */
    public void callFunction(String functionName) {
        callFunction(functionName, null);
    }

    /**
     * callFunction
     *
     * @param functionName functionName
     * @param jsonString   jsonString
     */
    public void callFunction(String functionName, String jsonString) {
        if (StringUtils.isEmpty(functionName)) {
            return;
        }
        if (StringUtils.isEmpty(jsonString)) {
            base_webview.loadUrl(String.format(FUNC_FORMAT, functionName));
        } else {
            jsonString = jsonString.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
            jsonString = jsonString.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
            base_webview.loadUrl(String.format(FUNC_FORMAT_WITH_PARAMETERS, functionName, jsonString));
        }
    }

    @Override
    public boolean onBackPressed() {
        if (base_webview != null && base_webview.canGoBack()) {
            base_webview.goBack();
            if (getDefualtToolbar() != null) {
                getDefualtToolbar().rightTwo().iconToLeft(Icon.icon_close).clicked(new View.OnClickListener() {
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
