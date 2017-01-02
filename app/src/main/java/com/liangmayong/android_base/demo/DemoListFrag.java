package com.liangmayong.android_base.demo;

import android.view.View;

import com.liangmayong.base.basic.expands.recyclerbox.FlowRecyclerBoxFragment;
import com.liangmayong.base.binding.view.annotations.BindTitle;
import com.liangmayong.base.support.builder.BundleBuilder;
import com.liangmayong.base.support.utils.DimenUtils;
import com.liangmayong.base.widget.interfaces.IRefresh;
import com.liangmayong.base.widget.recyclerbox.RecyclerBox;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@BindTitle("标题")
public class DemoListFrag extends FlowRecyclerBoxFragment {

    private void inserData() {
        for (int i = 0; i < 20; i++) {
            if (i % 3 == 0) {
                getRecyclerBox().getPool().add(new DemoItemView("Item" + (i + 1)));
            } else {
                getRecyclerBox().getPool().add(new DemoItem2View("Item" + (i + 1)).setOnItemClickListener(new RecyclerBox.OnRecyclerBoxItemClickListener<String>() {
                    @Override
                    public void onClick(RecyclerBox.Item<String> item, int position, View itemView) {
                        open(new StackF().initArguments(new BundleBuilder().put("title", item.getData()).builder()));
                    }
                }));
            }
        }
        getRecyclerBox().getPool().notifyDataSetChanged();
    }

    @Override
    protected void initBoxView(RecyclerBox recyclerBox, IRefresh refresh) {
        getDefaultToolbar().rightOne().text("Blog").click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo("Blog", "file:///android_asset/index.html");
            }
        });
        inserData();
        recyclerBox.setStaggeredEnable(true);
        recyclerBox.setDecorationSize(DimenUtils.dip2px(getActivity(), 2));
        refresh.setOnRefreshListener(new IRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                inserData();
                getRefresh().setRefreshing(false);
            }
        });
    }
}
