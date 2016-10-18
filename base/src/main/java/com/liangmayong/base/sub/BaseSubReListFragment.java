package com.liangmayong.base.sub;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.liangmayong.base.R;
import com.liangmayong.base.widget.layouts.SwipeLayout;
import com.liangmayong.base.widget.relistview.ReListView;
import com.liangmayong.skin.Skin;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@SuppressLint("ValidFragment")
public abstract class BaseSubReListFragment extends BaseSubFragment {


    private ReListView reListView = null;
    private SwipeRefreshLayout refreshLayout = null;

    /**
     * getListView
     *
     * @return listView
     */
    public ReListView getListView() {
        return reListView;
    }

    /**
     * getRefreshLayout
     *
     * @return refreshLayout
     */
    public SwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    @Override
    protected final void initSubView(View rootView) {
        reListView = (ReListView) rootView.findViewById(R.id.base_reListView);
        refreshLayout = (SwipeLayout) rootView.findViewById(R.id.base_refreshLayout);
        refreshLayout.setColorSchemeColors(Skin.get().getThemeColor());
        refreshLayout.setEnabled(refreshEnabled());
        ((SwipeLayout) refreshLayout).setViewGroup(reListView);
        initListView(reListView, refreshLayout);
    }

    @Override
    public void onRefreshSkin(Skin skin) {
        super.onRefreshSkin(skin);
        if (refreshLayout != null) {
            refreshLayout.setColorSchemeColors(skin.getThemeColor());
        }
    }

    protected abstract void initListView(ReListView listView, SwipeRefreshLayout refreshLayout);

    /**
     * refreshEnabled
     *
     * @return refresh enabled
     */
    protected boolean refreshEnabled() {
        return true;
    }

    @Override
    protected final int generateContainerViewId() {
        return R.layout.base_defualt_fragment_relist;
    }
}
