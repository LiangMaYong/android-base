package com.liangmayong.base;

import android.annotation.SuppressLint;
import android.view.View;

import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.widget.interfaces.IRefreshLayout;
import com.liangmayong.base.widget.recyclerbox.RecyclerBox;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@SuppressLint("ValidFragment")
public abstract class BaseListFragment extends BaseFragment {


    private RecyclerBox recyclerBox = null;
    private IRefreshLayout refreshLayout = null;

    /**
     * getRecyclerBox
     *
     * @return listView
     */
    public RecyclerBox getRecyclerBox() {
        return recyclerBox;
    }

    /**
     * getRefreshLayout
     *
     * @return refreshLayout
     */
    public IRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    @Override
    protected final void initView(View rootView) {
        recyclerBox = (RecyclerBox) rootView.findViewById(R.id.base_recyclerbox);
        refreshLayout = (IRefreshLayout) rootView.findViewById(R.id.base_refreshLayout);
        refreshLayout.setEnabled(refreshEnabled());
        refreshLayout.setChildView(recyclerBox);
        initListView(recyclerBox, refreshLayout);
    }

    @Override
    public void onSkinRefresh(ISkin skin) {
        super.onSkinRefresh(skin);
    }

    protected abstract void initListView(RecyclerBox listView, IRefreshLayout refreshLayout);

    /**
     * refreshEnabled
     *
     * @return refresh enabled
     */
    protected boolean refreshEnabled() {
        return true;
    }

    @Override
    protected int generateContainerViewId() {
        return R.layout.base_default_fragment_recyclerbox_swipe;
    }
}
