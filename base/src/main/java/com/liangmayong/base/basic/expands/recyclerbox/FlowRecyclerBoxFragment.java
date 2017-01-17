package com.liangmayong.base.basic.expands.recyclerbox;

import android.view.View;

import com.liangmayong.base.R;
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.widget.recyclerbox.RecyclerBox;
import com.liangmayong.base.widget.refresh.RefreshView;

/**
 * Created by LiangMaYong on 2016/12/29.
 */
public abstract class FlowRecyclerBoxFragment extends FlowBaseFragment {

    private RecyclerBox recyclerBox = null;
    private RefreshView refreshView = null;

    @Override
    protected void initViews(View containerView) {
        recyclerBox = (RecyclerBox) containerView.findViewById(R.id.base_recyclerbox);
        refreshView = (RefreshView) containerView.findViewById(R.id.base_refreshLayout);
        refreshView.setEnabled(isRefreshEnabled());
        refreshView.setChildView(recyclerBox);
        initBoxView(recyclerBox, refreshView);
    }

    @Override
    protected int getContaierLayoutId() {
        return R.layout.base_default_fragment_recyclerbox_swipe;
    }

    /**
     * initBoxView
     *
     * @param recyclerBox recyclerBox
     * @param refreshView refreshView
     */
    protected abstract void initBoxView(RecyclerBox recyclerBox, RefreshView refreshView);

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
     * @return refreshView
     */
    protected RefreshView getRefreshView() {
        return refreshView;
    }

    /**
     * isRefreshEnabled
     *
     * @return refreshView enabled
     */
    protected boolean isRefreshEnabled() {
        return true;
    }
}
