package com.liangmayong.android_base;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.liangmayong.base.sub.BaseSubReListFragment;
import com.liangmayong.base.utils.DimenUtils;
import com.liangmayong.base.widget.superlistview.SuperListView;
import com.liangmayong.skin.Skin;
import com.liangmayong.base.viewbinding.annotations.BindTitle;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@BindTitle("TestSub")
public class SubFrag extends BaseSubReListFragment {

    // colors
    private int[] colors = {0xff336666, 0xff663366, 0xff3399ff, 0xffff6858, 0xfffcb815};
    // index
    private int index = 0;

    @Override
    protected void initListView(SuperListView listView, SwipeRefreshLayout refreshLayout) {
        getDefualtToolbar().rightOne().text("百度").clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("百度一下");
                goTo("百度一下", "http://www.baidu.com");
               // open(new WebFragment("百度一下", "http://www.baidu.com"));
            }
        });
        for (int i = 0; i < 50; i++) {
            listView.getPool().add(new ViewItem(""));
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
