package com.liangmayong.base.basic.expands.webkit.abstracts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebViewClient;

import com.liangmayong.base.basic.expands.webkit.callback.WebCallback;
import com.liangmayong.base.basic.expands.webkit.config.WebConfig;
import com.liangmayong.base.support.utils.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LiangMaYong on 2017/2/18.
 */
public class AbstractWebKit extends android.webkit.WebView implements WebCallback.Callback {

    /**
     * Java call js function format with parameters
     */
    private static final String FUNC_FORMAT_WITH_PARAMETERS = "javascript:window.%s('%s')";
    /**
     * Java call js function format without parameters
     */
    private static final String FUNC_FORMAT = "javascript:window.%s()";
    // client
    private WebViewClient client;

    public AbstractWebKit(Context context) {
        super(context);
        initWebKit();
    }

    protected void initWebKit() {
        removeSearchBoxJavaBridgeInterface();
        setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (WebConfig.getDownloadListener() != null) {
                    WebConfig.getDownloadListener().onDownloadStart(url, userAgent, contentDisposition, mimetype, contentLength);
                } else {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    getContext().startActivity(intent);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= 9) {
            setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true);
        getSettings().setDatabaseEnabled(true);
        getSettings().setBuiltInZoomControls(false);
        getSettings().setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= 19) {
            getSettings().setLoadsImagesAutomatically(true);
        } else {
            getSettings().setLoadsImagesAutomatically(false);
        }
        getSettings().setDefaultTextEncodingName("UTF-8");
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        super.setWebViewClient(client);
        this.client = client;
    }

    public void onPageFinished() {
        if (client != null) {
            client.onPageFinished(this, getUrl());
        }
    }


    /**
     * injectionAssetsJS
     *
     * @param filename filename
     */
    public void injectionAssetsJS(String filename, String jsName) {
        try {
            InputStream in = getContext().getAssets().open(filename);
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
            String wholeJS = fromFile.toString().replaceAll("&jsBridge&", jsName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                evaluateJavascript(wholeJS, null);
            } else {
                loadUrl("javascript: " + wholeJS);
            }
            fromFile.close();
        } catch (IOException e) {
        }
    }


    /**
     * callFunction
     *
     * @param functionName functionName
     */
    public void call(String functionName) {
        call(functionName, null);
    }

    /**
     * callFunction
     *
     * @param functionName functionName
     * @param jsonString   jsonString
     */
    public void call(String functionName, String jsonString) {
        if (StringUtils.isEmpty(functionName)) {
            return;
        }
        if (StringUtils.isEmpty(jsonString)) {
            loadUrl(String.format(FUNC_FORMAT, functionName));
        } else {
            jsonString = jsonString.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
            jsonString = jsonString.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
            loadUrl(String.format(FUNC_FORMAT_WITH_PARAMETERS, functionName, jsonString));
        }
    }

    @Override
    public void onCall(String url, String functionName, String jsonString) {
        if (getUrl().equals(url)) {
            call(functionName, jsonString);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////  Private
    ///////////////////////////////////////////////////////////////////////////////////////////////


    @SuppressLint("NewApi")
    private void removeSearchBoxJavaBridgeInterface() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)) {
            removeJavascriptInterface("searchBoxJavaBridge_");
        }
    }
}
