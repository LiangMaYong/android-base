package com.liangmayong.base.basic.expands.list;

import android.view.View;
import android.widget.ListView;

import com.liangmayong.base.R;
import com.liangmayong.base.basic.BaseFragment;
import com.liangmayong.base.basic.flow.FlowBaseFragment;

/**
 * Created by LiangMaYong on 2017/3/1.
 */
public abstract class BaseListFragment extends BaseFragment {
    // listView
    private ListView listView = null;

    @Override
    protected final void initViews(View containerView) {
        listView = (ListView) containerView.findViewById(R.id.base_default_listview);
        initListViews(listView);
    }

    @Override
    protected int getContaierLayoutId() {
        return R.layout.base_default_fragment_list;
    }

    /**
     * initListViews
     *
     * @param listView listView
     */
    protected abstract void initListViews(ListView listView);

    /**
     * getListView
     *
     * @return listView
     */
    public ListView getListView() {
        return listView;
    }
}
