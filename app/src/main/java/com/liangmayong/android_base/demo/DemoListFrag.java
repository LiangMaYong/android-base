package com.liangmayong.android_base.demo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;

import com.liangmayong.android_base.R;
import com.liangmayong.base.sub.BaseSubListFragment;
import com.liangmayong.base.utils.BundleBuider;
import com.liangmayong.base.utils.DimenUtils;
import com.liangmayong.base.widget.binding.annotations.BindTitle;
import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.skin.Skin;
import com.liangmayong.base.widget.superlistview.SuperListView;
import com.liangmayong.base.widget.superlistview.item.DefualtSuperData;
import com.liangmayong.base.widget.superlistview.item.DefualtSuperItem;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@BindTitle("标题")
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
                goTo("Blog", "http://blog.csdn.net/stepalone");
            }
        });
        //添加头部
        add_head();
        //添加尾部
        add_footer();
        //添加数据
        add_data();
        initSkin();
        getListView().setColumnCount(index);
        listView.setStaggeredEnable(true);
        listView.setDecorationSize(DimenUtils.dip2px(getActivity(), 2));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRefreshLayout().setRefreshing(false);
                changeSkin();
                getListView().setColumnCount(index + 1);
            }
        });
    }

    private void add_head() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.header_view, null);
        getListView().setHeaderView(view);
    }

    private void add_footer() {
        View footView = LayoutInflater.from(getContext()).inflate(R.layout.footer_view, null);
        getListView().setFooterView(footView);
    }

    private void initSkin() {
        for (int i = 0; i < colors.length; i++) {
            if (Skin.get().getThemeColor() == colors[i]) {
                index = i;
            }
        }
    }

    private void changeSkin() {
        index++;
        if (index > colors.length - 1) {
            index = 0;
        }
        Skin.editor().setThemeColor(colors[index], 0xffffffff).commit();
    }

    private void add_data() {
        getListView().getPool().add(new DefualtSuperItem("AndroidBase Item", "defualt view").setIcon(Icon.icon_camera).setOnItemClickListener(new SuperListView.OnItemClickListener<DefualtSuperData>() {
            @Override
            public void onClick(SuperListView.Item<DefualtSuperData> item, int position, View itemView) {

            }
        }));
        for (int i = 0; i < 500; i++) {
            if (i % 3 == 0) {
                getListView().getPool().add(new DemoItemView("Item" + (i + 1)));
            } else {
                getListView().getPool().add(new DemoItem2View("Item" + (i + 1)).setOnItemClickListener(new SuperListView.OnItemClickListener<String>() {
                    @Override
                    public void onClick(SuperListView.Item<String> item, int position, View itemView) {
                        open(new DemoContentFragment().initArguments(new BundleBuider().put("title", item.getData()).buider()));
                    }
                }));
            }
        }
        getListView().getPool().notifyDataSetChanged();
    }
}
