package com.liangmayong.base.basic.expands.web.webkit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.liangmayong.base.support.utils.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LiangMaYong on 2016/11/3.
 */

public class WebKit extends android.webkit.WebView {

    /**
     * Java call js function format with parameters
     */
    private static final String FUNC_FORMAT_WITH_PARAMETERS = "javascript:window.%s('%s')";

    /**
     * Java call js function format without parameters
     */
    private static final String FUNC_FORMAT = "javascript:window.%s()";

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
            String wholeJS = fromFile.toString().replaceAll("#jsBridge#", jsName);
            if (android.os.Build.VERSION.SDK_INT < 19) {
                loadUrl("javascript: " + wholeJS);
            } else {
                evaluateJavascript(wholeJS, null);
            }
            fromFile.close();
        } catch (IOException e) {
        }
    }

    @SuppressLint("NewApi")
    private void removeSearchBoxJavaBridgeInterface() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)) {
            removeJavascriptInterface("searchBoxJavaBridge_");
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
            loadUrl(String.format(FUNC_FORMAT, functionName));
        } else {
            jsonString = jsonString.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
            jsonString = jsonString.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
            loadUrl(String.format(FUNC_FORMAT_WITH_PARAMETERS, functionName, jsonString));
        }
    }

}
