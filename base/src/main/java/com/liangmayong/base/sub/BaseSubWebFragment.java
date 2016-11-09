package com.liangmayong.base.sub;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.liangmayong.base.ui.fragments.DefualtMoreFragment;
import com.liangmayong.base.ui.fragments.items.MoreItem;
import com.liangmayong.base.utils.ClipboardUtils;
import com.liangmayong.base.utils.ShareUtils;
import com.liangmayong.base.utils.StringUtils;
import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.layout.SwipeLayout;
import com.liangmayong.base.widget.skin.Skin;
import com.liangmayong.base.widget.superlistview.SuperListView;

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

    //base_refresh_layout
    private SwipeLayout base_refresh_layout;
    //base_webview
    private BaseWebView base_webview;
    //title
    private String title = "";
    //url
    private String url = "";
    //refreshEnabled
    private boolean refreshEnabled = false;
    //closeEnabled
    private boolean closeEnabled = true;
    //copyEnabled
    private boolean copyEnabled = true;
    //showMoreEnabled
    private boolean showMoreEnabled = true;

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
        base_refresh_layout = (SwipeLayout) rootView.findViewById(R.id.base_swipeLayout);
        base_refresh_layout.addView(base_webview);
        base_refresh_layout.setEnabled(refreshEnabled);
        base_refresh_layout.setColorSchemeColors(Skin.get().getThemeColor());
        base_refresh_layout.setViewGroup(base_webview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            base_webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        base_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                base_refresh_layout.setRefreshing(false);
                reload();
            }
        });
        if (Build.VERSION.SDK_INT >= 9) {
            base_webview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        initDefualtToolbar();
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
        base_webview.getSettings().setUseWideViewPort(true);
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
                    getDefualtToolbar().setProgress(0);
                    setToolbarTitle(view.getTitle());
                    // injection ja
                    base_webview.injectionAssetsJS("javascript/base.js");
                } else {
                    getDefualtToolbar().setProgress(newProgress);
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
     * getUrlHost
     *
     * @param url
     * @return
     */
    public static String getUrlHost(String url) {
    }

    /**
     * showMoreDialog
     *
     * @param view view
     */
    protected void showMoreDialog(final View view) {
        List<SuperListView.Item> menus = new ArrayList<>();

        menus.add(new MoreItem(new MoreItem.Menu(Icon.icon_refresh, getString(R.string.base_web_refresh))).setOnItemClickListener(new SuperListView.OnItemClickListener<MoreItem.Menu>() {
            @Override
            public void onClick(SuperListView.Item<MoreItem.Menu> item, int position, View itemView) {
                reload();
                DefualtMoreFragment.cancel(getActivity());
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
            menus.add(new MoreItem(new MoreItem.Menu(Icon.icon_copy, getString(R.string.base_web_share))).setOnItemClickListener(new SuperListView.OnItemClickListener<MoreItem.Menu>() {
                @Override
                public void onClick(SuperListView.Item<MoreItem.Menu> item, int position, View itemView) {
                    String mUrl = url;
                    if (!StringUtils.isEmpty(base_webview.getUrl())) {
                        mUrl = base_webview.getUrl();
                    }
                    ClipboardUtils.copyText(getContext(), mUrl);
                    showToast(getString(R.string.base_web_copylink_success));
                    DefualtMoreFragment.cancel(getActivity());
                }
            }));
        }
        menus.add(new MoreItem(new MoreItem.Menu(Icon.icon_share, getString(R.string.base_web_share))).setOnItemClickListener(new SuperListView.OnItemClickListener<MoreItem.Menu>() {
            @Override
            public void onClick(SuperListView.Item<MoreItem.Menu> item, int position, View itemView) {
                String mUrl = url;
                if (!StringUtils.isEmpty(base_webview.getUrl())) {
                    mUrl = base_webview.getUrl();
                }
                showShareDialog(view, base_webview.getTitle(), mUrl);
                DefualtMoreFragment.cancel(getActivity());
            }
        }));
        DefualtMoreFragment.show(getActivity(), mUrl, menus);
    }

    /**
     * showShareDialog
     *
     * @param view  view
     * @param title title
     * @param url   url
     */
    public void showShareDialog(View view, String title, String url) {
        ShareUtils.shareText(getContext(), getString(R.string.base_web_share), url);
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
     * @return refreshEnabled
     */
    public boolean isRefreshEnabled() {
        return refreshEnabled;
    }

    /**
     * isShowMoreEnabled
     *
     * @return showMoreEnabled
     */
    public boolean isShowMoreEnabled() {
        return showMoreEnabled;
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
     * @param refreshEnabled refreshEnabled
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
        if (getDefualtToolbar() != null) {
            if (!closeEnabled) {
                getDefualtToolbar().rightTwo().gone();
            }
        }
    }

    public void setShowMoreEnabled(boolean showMoreEnabled) {
        this.showMoreEnabled = showMoreEnabled;
        initDefualtToolbar();
    }

    private void initDefualtToolbar() {
        if (getDefualtToolbar() != null) {
            getDefualtToolbar().leftOne().iconToLeft(Icon.icon_back).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            if (showMoreEnabled) {
                getDefualtToolbar().rightOne().iconToLeft(Icon.icon_more).clicked(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMoreDialog(v);
                    }
                });
            }
        }
    }

    @Override
    public void onSkinRefresh(Skin skin) {
        super.onSkinRefresh(skin);
        if (base_refresh_layout != null) {
            base_refresh_layout.setColorSchemeColors(skin.getThemeColor());
        }
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
            if (closeEnabled && getDefualtToolbar() != null) {
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
