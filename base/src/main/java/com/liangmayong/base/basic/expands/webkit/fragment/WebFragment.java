package com.liangmayong.base.basic.expands.webkit.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.liangmayong.base.R;
import com.liangmayong.base.basic.expands.webkit.abstracts.AbstractWebKit;
import com.liangmayong.base.basic.expands.webkit.abstracts.AbstractWebkitChromeClient;
import com.liangmayong.base.basic.expands.webkit.abstracts.AbstractWebkitClient;
import com.liangmayong.base.basic.expands.webkit.abstracts.AbstractWebkitDeviceListener;
import com.liangmayong.base.basic.expands.webkit.abstracts.AbstractWebkitHeaders;
import com.liangmayong.base.basic.expands.webkit.abstracts.AbstractWebkitInterceptor;
import com.liangmayong.base.basic.expands.webkit.abstracts.AbstractWebkitJsListener;
import com.liangmayong.base.basic.expands.webkit.abstracts.AbstractWebkitLoadingInterceptor;
import com.liangmayong.base.basic.expands.webkit.abstracts.AbstractWebkitProgressListener;
import com.liangmayong.base.basic.expands.webkit.config.WebConfig;
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.basic.interfaces.IBase;
import com.liangmayong.base.support.theme.ThemeManager;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.support.utils.DeviceUtils;
import com.liangmayong.base.support.utils.GoToUtils;
import com.liangmayong.base.widget.iconfont.IconFont;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/2/18.
 */

public class WebFragment extends FlowBaseFragment implements AbstractWebkitHeaders
        , AbstractWebkitInterceptor
        , AbstractWebkitJsListener
        , AbstractWebkitProgressListener
        , AbstractWebkitDeviceListener {

    // base_web_frame
    private FrameLayout base_web_frame;
    // abstractsWebKit
    private AbstractWebKit abstractsWebKit;
    // title
    private String title = "";
    // url
    private String url = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.title = getArguments().getString(IBase.WEB_EXTRA_TITLE);
            this.url = getArguments().getString(IBase.WEB_EXTRA_URL);
        }
    }

    @Override
    protected void initViews(View containerView) {
        base_web_frame = (FrameLayout) containerView.findViewById(R.id.base_default_web_frame);
        abstractsWebKit = new AbstractWebKit(getActivity());
        abstractsWebKit.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        base_web_frame.addView(abstractsWebKit);
        abstractsWebKit.setWebViewClient(new AbstractWebkitClient(this, this, this) {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setTitle(view.getTitle());
            }
        });
        abstractsWebKit.setWebChromeClient(new AbstractWebkitChromeClient(this, this));
        if (url == null || "".equals(url) || "null".equals(url)) {
            url = "about:blank";
        }
        abstractsWebKit.loadUrl(url, generateHeaders());
        setTitle(title);
    }

    public AbstractWebKit getAbstractsWebKit() {
        return abstractsWebKit;
    }

    @Override
    protected int getContaierLayoutId() {
        return R.layout.base_default_fragment_web;
    }

    @Override
    public void onInitDefaultToolbar(DefaultToolbar defaultToolbar) {
        defaultToolbar.leftOne().icon(IconFont.base_icon_back).click(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void setTitle(CharSequence title) {
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

    /**
     * setProgress
     *
     * @param progress progress
     */
    protected void setProgress(int progress) {
        if (getDefaultToolbar() != null) {
            if (progress >= 98) {
                progress = 0;
            }
            getDefaultToolbar().setProgress(progress);
        }
    }

    @Override
    public boolean onBackPressed() {
        if (getAbstractsWebKit() != null && getAbstractsWebKit().canGoBack()) {
            getAbstractsWebKit().goBack();
            if (getDefaultToolbar() != null) {
                getDefaultToolbar().rightOne().icon(IconFont.base_icon_close).click(new View.OnClickListener() {
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

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    private void action_reload() {
        getAbstractsWebKit().reload();
    }

    private void action_back() {
        if (getAbstractsWebKit() != null && getAbstractsWebKit().canGoBack()) {
            getAbstractsWebKit().goBack();
        }
    }

    private void action_forward() {
        if (getAbstractsWebKit() != null && getAbstractsWebKit().canGoForward()) {
            getAbstractsWebKit().goForward();
        }
    }

    private void action_back_finish() {
        onBackPressed();
    }

    private void action_show_toolbar() {
        if (getDefaultToolbar() != null) {
            getDefaultToolbar().visible();
        }
    }

    private void action_hide_toolbar() {
        if (getDefaultToolbar() != null) {
            getDefaultToolbar().gone();
        }
    }

    private void action_open(String url) {
        if (url != null && !"".equals(url)) {
            getAbstractsWebKit().loadUrl(url, generateHeaders());
        }
    }

    private void action_toast(String toast) {
        showToast(toast);
    }

    private void action_logcat(String tag) {
        GoToUtils.goLogcat(getContext(), tag);
    }

    private void action_success(String msg) {
        getDefaultToolbar().message().show(IconFont.base_icon_circle_yes, " " + msg, ThemeManager.getTheme().getSuccessTextColor(), ThemeManager.getTheme().getSuccessColor(), 1500);
    }

    private void action_warning(String msg) {
        getDefaultToolbar().message().show(IconFont.base_icon_circle_warning, " " + msg, ThemeManager.getTheme().getWarningTextColor(), ThemeManager.getTheme().getWarningColor(), 1500);
    }

    private void action_danger(String msg) {
        getDefaultToolbar().message().show(IconFont.base_icon_circle_error, " " + msg, ThemeManager.getTheme().getDangerTextColor(), ThemeManager.getTheme().getDangerColor(), 1500);
    }

    private void action_finish() {
        try {
            getAbstractsWebKit().stopLoading();
            getAbstractsWebKit().loadUrl("about:blank");
            base_web_frame.removeAllViews();
            getAbstractsWebKit().removeAllViews();
            getAbstractsWebKit().destroy();
        } catch (Exception e) {
        }
        closeSelf();
    }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    /**
     * generateLoadingInterceptors
     *
     * @return generateLoadingInterceptors
     */
    protected List<AbstractWebkitLoadingInterceptor> generateLoadingInterceptors() {
        List<AbstractWebkitLoadingInterceptor> interceptors = new ArrayList<AbstractWebkitLoadingInterceptor>();
        interceptors.add(new AbstractWebkitLoadingInterceptor("action:") {
            @Override
            public boolean interceptorUrlLoading(AbstractWebKit web, UrlParam url) {
                if ("finish".equals(url.getContent())) {
                    action_finish();
                } else if ("reload".equals(url.getContent())) {
                    action_reload();
                } else if ("forward".equals(url.getContent())) {
                    action_forward();
                } else if ("back".equals(url.getContent())) {
                    action_back();
                } else if ("back_finish".equals(url.getContent())) {
                    action_back_finish();
                } else if ("hide_toolbar".equals(url.getContent())) {
                    action_hide_toolbar();
                } else if ("show_toolbar".equals(url.getContent())) {
                    action_show_toolbar();
                } else if ("toast".equals(url.getContent())) {
                    String toast = url.getParams().get("toast");
                    if (toast != null && !"".equals(toast)) {
                        action_toast(toast);
                    }
                } else if ("success".equals(url.getContent())) {
                    String msg = url.getParams().get("msg");
                    if (msg != null && !"".equals(msg)) {
                        action_success(msg);
                    }
                } else if ("warning".equals(url.getContent())) {
                    String msg = url.getParams().get("msg");
                    if (msg != null && !"".equals(msg)) {
                        action_warning(msg);
                    }
                } else if ("danger".equals(url.getContent())) {
                    String msg = url.getParams().get("msg");
                    if (msg != null && !"".equals(msg)) {
                        action_danger(msg);
                    }
                } else if ("open".equals(url.getContent())) {
                    String openUrl = url.getParams().get("url");
                    if (openUrl != null && !"".equals(openUrl)) {
                        action_open(openUrl);
                    }
                } else if ("logcat".equals(url.getContent())) {
                    action_logcat(url.getParams().get("tag"));
                }
                return true;
            }
        });
        interceptors.addAll(WebConfig.getInterceptors());
        return interceptors;
    }

    @Override
    public Map<String, String> generateHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("webview_client_type", "Android");
        headers.putAll(WebConfig.getHeaders());
        return headers;
    }

    @Override
    public boolean shouldOverrideUrlLoading(AbstractWebKit view, String url) {
        List<AbstractWebkitLoadingInterceptor> interceptors = generateLoadingInterceptors();
        if (interceptors != null && interceptors.size() > 0) {
            for (int i = 0; i < interceptors.size(); i++) {
                String lowurl = url.toLowerCase();
                if (lowurl.startsWith(interceptors.get(i).getScheme())) {
                    boolean flag = interceptors.get(i).interceptorUrlLoading(view, new AbstractWebkitLoadingInterceptor.UrlParam(url, interceptors.get(i).getScheme()));
                    if (flag) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return false;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return false;
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        return false;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return false;
    }

    @Override
    public void onProgressChanged(AbstractWebKit view, int newProgress) {
        setProgress(newProgress);
    }

    @Override
    public Map<String,String> generateDeviceInfo(Context context) {
        Map<String,String> deviceInfo = DeviceUtils.getDeviceInfo(context);
        if (WebConfig.getDeviceListener() != null) {
            deviceInfo.putAll(WebConfig.getDeviceListener().generateDeviceInfo(context));
        }
        return deviceInfo;
    }
}
