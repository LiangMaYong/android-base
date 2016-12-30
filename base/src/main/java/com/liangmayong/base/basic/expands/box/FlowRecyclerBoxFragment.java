package com.liangmayong.base.basic.expands.box;

import android.view.View;

import com.liangmayong.base.R;
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.widget.interfaces.IRefresh;
import com.liangmayong.base.widget.recyclerbox.RecyclerBox;

/**
 * Created by LiangMaYong on 2016/12/29.
 */
public abstract class FlowRecyclerBoxFragment extends FlowBaseFragment {

    private RecyclerBox recyclerBox = null;
    private IRefresh refresh = null;

    @Override
    protected void initViews(View containerView) {
        recyclerBox = (RecyclerBox) containerView.findViewById(R.id.base_recyclerbox);
        refresh = (IRefresh) containerView.findViewById(R.id.base_refreshLayout);
        refresh.setEnabled(isRefreshEnabled());
        refresh.setChildView(recyclerBox);
        initBoxView(recyclerBox, refresh);
    }

    @Override
    protected int getContaierLayoutId() {
        return R.layout.base_default_fragment_recyclerbox_swipe;
    }

    /**
     * initBoxView
     *
     * @param recyclerBox recyclerBox
     * @param refresh     refresh
     */
    protected abstract void initBoxView(RecyclerBox recyclerBox, IRefresh refresh);

    /**
     * getRecyclerBox
     *
     * @return listView
     */
    protected RecyclerBox getRecyclerBox() {
        return recyclerBox;
    }

    /**
     * getRefresh
     *
     * @return refresh
     */
    protected IRefresh getRefresh() {
        return refresh;
    }

    /**
     * isRefreshEnabled
     *
     * @return refresh enabled
     */
    protected boolean isRefreshEnabled() {
        return true;
    }
}
