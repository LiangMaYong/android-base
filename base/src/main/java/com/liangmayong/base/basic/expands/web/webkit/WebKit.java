package com.liangmayong.base.basic.expands.web.webkit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.WebViewClient;

import com.liangmayong.base.support.utils.DeviceUtils;
import com.liangmayong.base.support.utils.StringUtils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LiangMaYong on 2016/11/3.
 */

public class WebKit extends android.webkit.WebView implements WebKitCallback.OnWebKitCallbackListener {

    /**
     * Java call js function format with parameters
     */
    private static final String FUNC_FORMAT_WITH_PARAMETERS = "javascript:window.%s('%s')";

    /**
     * Java call js function format without parameters
     */
    private static final String FUNC_FORMAT = "javascript:window.%s()";
    private WebViewClient client;

    public WebKit(Context context) {
        super(context);
        initWebView();
    }

    public WebKit(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWebView();
    }

    public WebKit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WebKit(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initWebView();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public WebKit(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        initWebView();
    }

    private void initWebView() {
        removeSearchBoxJavaBridgeInterface();
        setDownloadListener(new WebKitDownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            }
        });
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
            try {
                call(jsName + ".init", new JSONObject(DeviceUtils.getDeviceInfo(getContext())).toString());
            } catch (Exception e) {
            }
        } catch (IOException e) {
        }
    }

    @SuppressLint("NewApi")
    private void removeSearchBoxJavaBridgeInterface() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)) {
            removeJavascriptInterface("searchBoxJavaBridge_");
        }
    }

    public void onPageFinished() {
        if (client != null) {
            client.onPageFinished(this, getUrl());
        }
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        super.setWebViewClient(client);
        this.client = client;
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
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            WebKitCallback.registerCallbackListener(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            WebKitCallback.unregisterCallbackListener(this);
        }
    }

    @Override
    public void onCall(String url, String functionName, String jsonString) {
        if (getUrl().equals(url)) {
            call(functionName, jsonString);
        }
    }
}
