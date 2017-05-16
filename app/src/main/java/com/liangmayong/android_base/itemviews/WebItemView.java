package com.liangmayong.android_base.itemviews;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.liangmayong.android_base.R;
import com.liangmayong.base.binding.view.annotations.BindLayout;
import com.liangmayong.base.binding.view.annotations.BindView;
import com.liangmayong.base.support.adapter.view.BindingSuperItemView;

/**
 * Created by LiangMaYong on 2016/9/25.
 */
@BindLayout(R.layout.item_web_view)
public class WebItemView extends BindingSuperItemView<String> {
    @BindView(R.id.tv_txt)
    private TextView tv_txt;
    @BindView(R.id.webview)
    private WebView webview;

    public WebItemView(String string) {
        super(string);
    }

    @Override
    protected void onBindView(View itemView, String string) {
        tv_txt.setText(string + "-----------");
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient());
        if (!string.equals(webview.getUrl())) {
            webview.loadUrl(string);
        }
    }
}

