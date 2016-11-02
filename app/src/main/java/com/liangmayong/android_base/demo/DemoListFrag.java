package com.liangmayong.android_base.demo;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.liangmayong.base.sub.BaseSubListFragment;
import com.liangmayong.base.utils.BundleBuider;
import com.liangmayong.base.utils.DimenUtils;
import com.liangmayong.base.widget.binding.annotations.BindTitle;
import com.liangmayong.base.widget.skin.Skin;
import com.liangmayong.base.widget.superlistview.SuperListView;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@BindTitle("AndroidBase")
public class DemoListFrag extends BaseSubListFragment {

    // colors
    private int[] colors = {0xff333333, 0xff663366, 0xff3399ff, 0xffff6858, 0xfffcb815};
    // index
    private int index = 0;

    @Override
    protected void initListView(SuperListView listView, SwipeRefreshLayout refreshLayout) {
        getDefualtToolbar().rightOne().text("Blog").clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo("LiangMaYong", "http://blog.csdn.net/stepalone");
            }
        });
        for (int i = 0; i < 500; i++) {
            listView.getPool().add(new DemoItemView("Item" + (i + 1)).setOnItemClickListener(new SuperListView.OnItemClickListener<String>() {
                @Override
                public void onClick(SuperListView.Item<String> item, int position, View itemView) {
                    open(new DemoContentFragment().initArguments(new BundleBuider().put("title", item.getData()).buider()));
                }
            }));
        }
        listView.getPool().notifyDataSetChanged();
        listView.setDecorationSize(DimenUtils.dip2px(getActivity(), 2));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                index++;
                if (index > colors.length - 1) {
                    index = 0;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRefreshLayout().setRefreshing(false);
                        Skin.editor().setThemeColor(colors[index], 0xffffffff).commit();
                    }
                }, 0);
                getListView().setColumnCount(index + 1);
            }
        });
    }

}
