package com.liangmayong.base.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.liangmayong.base.BaseActivity;
import com.liangmayong.base.R;
import com.liangmayong.base.bind.annotations.BindPresenter;
import com.liangmayong.base.presenters.WebPresenter;
import com.liangmayong.base.presenters.interfaces.BaseInterfaces;
import com.liangmayong.base.presenters.interfaces.WebInterfaces;
import com.liangmayong.base.widget.iconfont.Icon;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
@BindPresenter({WebPresenter.class})
public class WebActivity extends BaseActivity implements WebInterfaces.IView {

    //base_webview
    private WebView base_webview;
    //title
    private String title = "";
    //url
    private String url = "";
    //headers
    private HashMap<String, String> headers = null;
    //isLockTitle
    private boolean isLockTitle = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.base_activity_web);
        super.onCreate(savedInstanceState);
        base_webview = (WebView) findViewById(R.id.base_webview);
        if (Build.VERSION.SDK_INT >= 9) {
            base_webview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
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
        initData();
        initView();
    }

    /**
     * initData
     */
    private void initData() {
        url = getIntent().getStringExtra(BaseInterfaces.WEB_EXTRA_URL);
        title = getIntent().getStringExtra(BaseInterfaces.WEB_EXTRA_TITLE);
        try {
            headers = (HashMap<String, String>) getIntent().getSerializableExtra(BaseInterfaces.WEB_EXTRA_HEADERS);
        } catch (Exception e) {
        }
        if (url == null || "".equals(url) || "null".equals(url)) {
            url = "about:blank";
        }
        if (title != null && !"".equals(title) && !"null".equals(title)) {
            isLockTitle = true;
        }
        getDefualtToolbar().setTitle(title);
    }

    /**
     * setToolbarTitle
     *
     * @param title title
     */
    private void setToolbarTitle(String title) {
        if (isLockTitle) {
            return;
        }
        getDefualtToolbar().setTitle(title);
    }

    private void initView() {
        base_webview.setWebViewClient(new BaseWebViewClient());
        base_webview.getSettings().setJavaScriptEnabled(true);
        base_webview.getSettings().setAppCacheEnabled(true);
        base_webview.addJavascriptInterface(new BaseWebViewInterface(), BaseInterfaces.WEB_JAVASCRIPT_INTERFACE_NAME);
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
        base_webview.setDownloadListener(new BaseWebViewDownLoadListener());
        base_webview.loadUrl(url, getHeaders());
        setToolbarTitle(title);
    }


    /**
     * getHeaders
     *
     * @return headers
     */
    private Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * BaseWebViewClient
     */
    private class BaseWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url, getHeaders());
                return false;
            }
            // Otherwise allow the OS to handle things like tel, mailto, etc.
            if (url.startsWith("tel:") || url.startsWith("email:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
            return true;
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
     * BaseWebViewInterface
     */
    private class BaseWebViewInterface {
    }

    @Override
    public void onBackPressed() {
        if (base_webview != null && base_webview.canGoBack()) {
            base_webview.goBack();
        } else {
            finish();
        }
    }

    /**
     * backClose
     *
     * @param v v
     */
    public void backClose(View v) {
        finish();
    }

}
