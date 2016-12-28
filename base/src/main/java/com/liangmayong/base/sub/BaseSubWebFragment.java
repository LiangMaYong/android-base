package com.liangmayong.base.sub;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.liangmayong.base.R;
import com.liangmayong.base.sub.webkit.BaseWebChromeClient;
import com.liangmayong.base.sub.webkit.BaseWebView;
import com.liangmayong.base.sub.webkit.BaseWebViewClient;
import com.liangmayong.base.sub.webkit.BaseWebWidget;
import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.utils.ClipboardUtils;
import com.liangmayong.base.utils.ShareUtils;
import com.liangmayong.base.utils.StringUtils;
import com.liangmayong.base.web.dialogs.DefaultMoreDialog;
import com.liangmayong.base.web.items.MoreItem;
import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.interfaces.IRefreshLayout;
import com.liangmayong.base.widget.recyclerbox.RecyclerBox;
import com.liangmayong.base.widget.toolbar.DefaultToolbar;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    public BaseSubWebFragment(String title, String url, boolean moreEnabled) {
        this.title = title;
        this.url = url;
        this.moreEnabled = moreEnabled;
    }

    //base_refresh_layout
    private IRefreshLayout base_refresh_layout;
    //base_webview
    private BaseWebView base_webview;
    //title
    private String title = "";
    //url
    private String url = "";
    //isRefreshEnabled
    private boolean refreshEnabled = false;
    //closeEnabled
    private boolean closeEnabled = true;
    //copyEnabled
    private boolean copyEnabled = true;
    //moreEnabled
    private boolean moreEnabled = false;

    /**
     * initWebView
     *
     * @param rootView rootView
     */
    protected void initWebView(BaseWebView rootView) {
    }

    /**
     * getWebView
     *
     * @return base_webview
     */
    public WebView getWebView() {
        return base_webview;
    }

    @Override
    protected final void initSubView(View rootView) {
        base_webview = new BaseWebView(getActivity());
        base_webview.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        base_refresh_layout = (IRefreshLayout) rootView.findViewById(R.id.base_web_refreshLayout);
        base_refresh_layout.addView(base_webview);
        base_refresh_layout.setEnabled(refreshEnabled);
        base_refresh_layout.setChildView(base_webview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            base_webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        base_refresh_layout.setOnRefreshListener(new IRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                base_refresh_layout.setRefreshing(false);
                reload();
            }
        });
        if (Build.VERSION.SDK_INT >= 9) {
            base_webview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        initDefaultToolbar();
        base_webview.setWebViewClient(new BaseWebViewClient() {
            @Override
            protected Map<String, String> generateHeaders() {
                return BaseSubWebFragment.this.getHeaders();
            }

            @Override
            protected List<BaseWebWidget> generateWidgets() {
                return BaseSubWebFragment.this.getWidgets();
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
        base_webview.setWebChromeClient(new BaseWebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    getDefaultToolbar().setProgress(0);
                    setToolbarTitle(view.getTitle());
                    // injection ja
                    base_webview.injectionAssetsJS("javascript/base.js");
                } else {
                    getDefaultToolbar().setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        base_webview.loadUrl(url, getHeaders());
        initData();
        initWebView(base_webview);
    }

    /**
     * initData
     */
    private void initData() {
        if (url == null || "".equals(url) || "null".equals(url)) {
            url = "about:blank";
        }
        setToolbarTitle(title);
    }

    /**
     * reload
     */
    public void reload() {
        base_webview.reload();
    }

    /**
     * showMoreDialog
     *
     * @param view view
     */
    protected void showMoreDialog(final View view) {
        List<RecyclerBox.Item> menus = new ArrayList<>();

        menus.add(new MoreItem(new MoreItem.MoreData(Icon.icon_refresh, getString(R.string.base_web_refresh))).setOnItemClickListener(new RecyclerBox.OnRecyclerBoxItemClickListener<MoreItem.MoreData>() {
            @Override
            public void onClick(RecyclerBox.Item<MoreItem.MoreData> item, int position, View itemView) {
                reload();
                DefaultMoreDialog.cancel(getActivity());
            }
        }));
        String mUrl = url;
        if (!StringUtils.isEmpty(base_webview.getUrl())) {
            mUrl = base_webview.getUrl();
        }
        try {
            mUrl = new URL(mUrl).getHost();
        } catch (MalformedURLException e) {
        }
        if (copyEnabled) {
            menus.add(new MoreItem(new MoreItem.MoreData(Icon.icon_copy, getString(R.string.base_web_copylink))).setOnItemClickListener(new RecyclerBox.OnRecyclerBoxItemClickListener<MoreItem.MoreData>() {
                @Override
                public void onClick(RecyclerBox.Item<MoreItem.MoreData> item, int position, View itemView) {
                    DefaultMoreDialog.cancel(getActivity());
                    String mUrl = url;
                    if (!StringUtils.isEmpty(base_webview.getUrl())) {
                        mUrl = base_webview.getUrl();
                    }
                    ClipboardUtils.copyText(getContext(), mUrl);
                    getDefaultToolbar().message().show(Icon.icon_circle_yes, getString(R.string.base_web_copylink_success), 0xff333333, 0xffffffff, 1500);
                }
            }));
        }
        menus.add(new MoreItem(new MoreItem.MoreData(Icon.icon_share, getString(R.string.base_web_share))).setOnItemClickListener(new RecyclerBox.OnRecyclerBoxItemClickListener<MoreItem.MoreData>() {
            @Override
            public void onClick(RecyclerBox.Item<MoreItem.MoreData> item, int position, View itemView) {
                DefaultMoreDialog.cancel(getActivity());
                String mUrl = url;
                if (!StringUtils.isEmpty(base_webview.getUrl())) {
                    mUrl = base_webview.getUrl();
                }
                ShareUtils.shareText(getContext(), getString(R.string.base_web_share), mUrl);
            }
        }));
        DefaultMoreDialog.show(getActivity(), mUrl, menus);
    }

    /**
     * setToolbarTitle
     *
     * @param title title
     */
    private void setToolbarTitle(String title) {
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
    protected int generateContainerViewId() {
        return R.layout.base_default_fragment_web;
    }


    /**
     * isCloseEnabled
     *
     * @return closeEnabled
     */
    public boolean isCloseEnabled() {
        return closeEnabled;
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
     * isMoreEnabled
     *
     * @return moreEnabled
     */
    public boolean isMoreEnabled() {
        return moreEnabled;
    }

    /**
     * isCopyEnabled
     *
     * @return isCopyEnabled
     */
    public boolean isCopyEnabled() {
        return copyEnabled;
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

    /**
     * setCopyEnabled
     *
     * @param copyEnabled copyEnabled
     */
    public void setCopyEnabled(boolean copyEnabled) {
        this.copyEnabled = copyEnabled;
    }

    /**
     * setCloseEnabled
     *
     * @param closeEnabled closeEnabled
     */
    public void setCloseEnabled(boolean closeEnabled) {
        this.closeEnabled = closeEnabled;
        if (getDefaultToolbar() != null) {
            if (!closeEnabled) {
                getDefaultToolbar().rightTwo().gone();
            }
        }
    }

    public void setMoreEnabled(boolean moreEnabled) {
        this.moreEnabled = moreEnabled;
        initDefaultToolbar();
    }

    private void initDefaultToolbar() {
        if (getDefaultToolbar() != null) {
            getDefaultToolbar().leftOne().iconToLeft(Icon.icon_back).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            if (isMoreEnabled()) {
                getDefaultToolbar().rightOne().iconToLeft(Icon.icon_more).clicked(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMoreDialog(v);
                    }
                });
            }
            getDefaultToolbar().rightTwo().text("");
        }
    }

    @Override
    public void onSkinRefresh(ISkin skin) {
        super.onSkinRefresh(skin);
    }

    /**
     * generateWeigets
     *
     * @return weiget
     */
    protected List<BaseWebWidget> getWidgets() {
        return null;
    }

    /**
     * getHeaders
     *
     * @return headers
     */
    protected Map<String, String> getHeaders() {
        return null;
    }

    @Override
    public boolean onBackPressed() {
        if (base_webview != null && base_webview.canGoBack()) {
            base_webview.goBack();
            if (closeEnabled && getDefaultToolbar() != null) {
                DefaultToolbar.ToolbarItem item = null;
                if (isMoreEnabled()) {
                    item = getDefaultToolbar().rightTwo();
                } else {
                    item = getDefaultToolbar().rightOne();
                }
                item.iconToLeft(Icon.icon_close).clicked(new View.OnClickListener() {
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
