package com.liangmayong.base.basic.expands.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liangmayong.base.R;
import com.liangmayong.base.basic.BaseFragment;

/**
 * Created by LiangMaYong on 2017/3/1.
 */
public abstract class BaseRecyclerFragment extends BaseFragment {
    // listView
    private RecyclerView recyclerView = null;

    @Override
    protected void initViews(View containerView) {
        recyclerView = (RecyclerView) containerView.findViewById(R.id.base_default_recyclerview);
        initRecyclerViews(recyclerView);
    }

    @Override
    protected int getContaierLayoutId() {
        return R.layout.base_default_fragment_recycler;
    }

    /**
     * initRecyclerViews
     *
     * @param recyclerView recyclerView
     */
    protected abstract void initRecyclerViews(RecyclerView recyclerView);

    /**
     * getRecyclerView
     *
     * @return recyclerView
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
