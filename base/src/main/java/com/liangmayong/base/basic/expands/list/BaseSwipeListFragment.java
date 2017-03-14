package com.liangmayong.base.basic.expands.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.liangmayong.base.R;
import com.liangmayong.base.basic.BaseFragment;

/**
 * Created by LiangMaYong on 2017/3/1.
 */

public abstract class BaseSwipeListFragment extends BaseFragment {
    // listView
    private ListView listView = null;
    private SwipeRefreshLayout swipeLayout = null;

    @Override
    protected final void initViews(View containerView) {
        listView = (ListView) containerView.findViewById(R.id.base_default_listview);
        swipeLayout = (SwipeRefreshLayout) containerView.findViewById(R.id.base_default_swipe_layout);
        initListViews(swipeLayout, listView);
    }

    @Override
    protected int getContaierLayoutId() {
        return R.layout.base_default_fragment_list_swipe;
    }

    /**
     * initListViews
     *
     * @param listView listView
     */
    protected abstract void initListViews(SwipeRefreshLayout swipeLayout, ListView listView);

    /**
     * getListView
     *
     * @return listView
     */
    public ListView getListView() {
        return listView;
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
