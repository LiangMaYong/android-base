package com.liangmayong.android_base;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.liangmayong.base.sub.BaseSubReListFragment;
import com.liangmayong.base.utils.DimenUtils;
import com.liangmayong.base.viewbinding.annotations.BindTitle;
import com.liangmayong.base.widget.superlistview.SuperListView;
import com.liangmayong.skin.Skin;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@BindTitle("TestSub")
public class SubFrag extends BaseSubReListFragment {

    // colors
    private int[] colors = {0xff333333, 0xff663366, 0xff3399ff, 0xffff6858, 0xfffcb815};
    // index
    private int index = 0;

    @Override
    protected void initListView(SuperListView listView, SwipeRefreshLayout refreshLayout) {
        getDefualtToolbar().rightOne().text("Github").clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo("Github", "https://github.com/LiangMaYong/android-base");
            }
        });
        for (int i = 0; i < 50; i++) {
            ViewItem item = new ViewItem("Item" + (i + 1));
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    open(new ItemFrag());
                }
            });
            listView.getPool().add(item);
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
