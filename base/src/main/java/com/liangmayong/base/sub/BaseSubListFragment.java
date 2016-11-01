package com.liangmayong.base.sub;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.liangmayong.base.R;
import com.liangmayong.base.widget.layouts.SwipeLayout;
import com.liangmayong.base.widget.skin.Skin;
import com.liangmayong.base.widget.superlistview.SuperListView;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@SuppressLint("ValidFragment")
public abstract class BaseSubListFragment extends BaseSubFragment {


    private SuperListView reListView = null;
    private SwipeRefreshLayout refreshLayout = null;

    /**
     * getListView
     *
     * @return listView
     */
    public SuperListView getListView() {
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
        reListView = (SuperListView) rootView.findViewById(R.id.base_superListView);
        refreshLayout = (SwipeLayout) rootView.findViewById(R.id.base_swipeLayout);
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

    protected abstract void initListView(SuperListView listView, SwipeRefreshLayout refreshLayout);

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
        return R.layout.base_defualt_fragment_superlist;
    }
}
