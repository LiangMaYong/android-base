package com.liangmayong.base.basic.expands.recycler;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liangmayong.base.R;
import com.liangmayong.base.basic.BaseFragment;

/**
 * Created by LiangMaYong on 2017/3/1.
 */
public abstract class BaseSwipeRecyclerFragment extends BaseFragment {
    // listView
    private RecyclerView recyclerView = null;
    private SwipeRefreshLayout swipeLayout = null;

    @Override
    protected void initViews(View containerView) {
        recyclerView = (RecyclerView) containerView.findViewById(R.id.base_default_recyclerview);
        swipeLayout = (SwipeRefreshLayout) containerView.findViewById(R.id.base_default_swipe_layout);
        initRecyclerViews(swipeLayout, recyclerView);
    }

    @Override
    protected int getContaierLayoutId() {
        return R.layout.base_default_fragment_recycler_swipe;
    }

    /**
     * initRecyclerViews
     *
     * @param recyclerView recyclerView
     */
    protected abstract void initRecyclerViews(SwipeRefreshLayout swipeLayout, RecyclerView recyclerView);

    /**
     * getRecyclerView
     *
     * @return recyclerView
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * getSwipeLayout
     *
     * @return swipeLayout
     */
    public SwipeRefreshLayout getSwipeLayout() {
        return swipeLayout;
    }
}
