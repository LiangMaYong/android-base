package com.liangmayong.android_base.demo;

import android.view.View;

import com.liangmayong.base.sub.BaseSubListFragment;
import com.liangmayong.base.support.binding.annotations.BindTitle;
import com.liangmayong.base.support.builder.BundleBuilder;
import com.liangmayong.base.utils.DimenUtils;
import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.interfaces.IRefreshLayout;
import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.widget.superlistview.SuperListView;
import com.liangmayong.base.widget.superlistview.item.DefaultSuperData;
import com.liangmayong.base.widget.superlistview.item.DefaultSuperItem;

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
    protected void initListView(SuperListView listView, IRefreshLayout refreshLayout) {
        getDefaultToolbar().rightOne().text("Blog").clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo("Blog", "http://www.baidu.com");
            }
        });
        //添加数据
        add_data();
        initSkin();
        getListView().setColumnCount(index);
        listView.setStaggeredEnable(true);
        listView.setDecorationSize(DimenUtils.dip2px(getActivity(), 2));
        refreshLayout.setOnRefreshListener(new IRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRefreshLayout().setRefreshing(false);
                changeSkin();
                getListView().setColumnCount(index + 1);
            }
        });
    }

    private void initSkin() {
        for (int i = 0; i < colors.length; i++) {
            if (SkinManager.get().getThemeColor() == colors[i]) {
                index = i;
            }
        }
    }

    private void changeSkin() {
        index++;
        if (index > colors.length - 1) {
            index = 0;
        }
        SkinManager.editor().setThemeColor(colors[index], 0xffffffff).commit();
    }

    private void add_data() {
        getListView().getPool().add(new DefaultSuperItem("AndroidBase Item", "default_type view", "11:22", null).setIcon(Icon.icon_camera).setOnItemClickListener(new SuperListView.OnItemClickListener<DefaultSuperData>() {
            @Override
            public void onClick(SuperListView.Item<DefaultSuperData> item, int position, View itemView) {
                showToast("sssssssssssss");
            }
        }));
        for (int i = 0; i < 500; i++) {
            if (i % 3 == 0) {
                getListView().getPool().add(new DemoItemView("Item" + (i + 1)));
            } else {
                getListView().getPool().add(new DemoItem2View("Item" + (i + 1)).setOnItemClickListener(new SuperListView.OnItemClickListener<String>() {
                    @Override
                    public void onClick(SuperListView.Item<String> item, int position, View itemView) {
                        openFragment(new DemoContentFragment().initArguments(new BundleBuilder().put("title", item.getData()).builder()));
                    }
                }));
            }
        }
        getListView().getPool().notifyDataSetChanged();
    }
}
