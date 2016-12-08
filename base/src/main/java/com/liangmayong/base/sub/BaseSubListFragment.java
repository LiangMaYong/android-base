package com.liangmayong.base.sub;

import android.annotation.SuppressLint;
import android.view.View;

import com.liangmayong.base.R;
import com.liangmayong.base.widget.interfaces.IRefreshLayout;
import com.liangmayong.base.widget.skin.Skin;
import com.liangmayong.base.widget.superlistview.SuperListView;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@SuppressLint("ValidFragment")
public abstract class BaseSubListFragment extends BaseSubFragment {


    private SuperListView reListView = null;
    private IRefreshLayout refreshLayout = null;

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
    public IRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    @Override
    protected final void initSubView(View rootView) {
        reListView = (SuperListView) rootView.findViewById(R.id.base_superListView);
        refreshLayout = (IRefreshLayout) rootView.findViewById(R.id.base_refreshLayout);
        refreshLayout.setEnabled(refreshEnabled());
        refreshLayout.setChildView(reListView);
        initListView(reListView, refreshLayout);
    }

    @Override
    public void onSkinRefresh(Skin skin) {
        super.onSkinRefresh(skin);
    }

    protected abstract void initListView(SuperListView listView, IRefreshLayout refreshLayout);

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
        return R.layout.base_defualt_fragment_superlist_swipe;
    }
}
