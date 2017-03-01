package com.liangmayong.android_base.demo;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.liangmayong.android_base.R;
import com.liangmayong.base.basic.expands.list.FlowListFragment;
import com.liangmayong.base.binding.view.annotations.BindLayout;
import com.liangmayong.base.binding.view.annotations.BindTitle;
import com.liangmayong.base.support.adapter.SuperListAdapter;
import com.liangmayong.base.support.toolbar.DefaultToolbar;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@BindLayout(R.layout.activity_item2)
@BindTitle("标题")
public class DemoListFrag extends FlowListFragment {

    private SuperListAdapter listAdapter = null;

    @Override
    protected void initListViews(ListView listView) {
        listAdapter = new SuperListAdapter();
        listAdapter.add(new DemoItem2View("111111111111111111111"));
        listAdapter.add(new DemoItemView(1));
        listView.setAdapter(listAdapter);
        listAdapter.add(new DemoItem2View("33333333333333333333333333333"));
        listAdapter.add(new DemoItem2View("33333333333333333333333333333"));
        listAdapter.add(new DemoItemView(3));
        listAdapter.add(new DemoItemView(4));
        listAdapter.add(new DemoItemView(5));
        listAdapter.add(new DemoItem2View("33333333333333333333333333333"));
    }

    @Override
    public void initDefaultToolbar(DefaultToolbar defaultToolbar) {
        super.initDefaultToolbar(defaultToolbar);
        defaultToolbar.rightOne().text("ADD").click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAdapter.add(new DemoItem2View("33333333333333333333333333333"));
                listAdapter.add(new DemoItem2View("33333333333333333333333333333"));
                listAdapter.add(new DemoItem2View("33333333333333333333333333333"));
                listAdapter.add(new DemoItemView(1));
                listAdapter.add(new DemoItemView(2));
                listAdapter.add(new DemoItem2View("33333333333333333333333333333"));
                listAdapter.add(new DemoItemView(3));
                listAdapter.add(new DemoItemView(4));
                listAdapter.add(new DemoItemView(5));
                listAdapter.add(new DemoItem2View("33333333333333333333333333333"));
            }
        });
        defaultToolbar.rightTwo().text("RA").click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAdapter.notifyDataSetChanged();
            }
        });
        defaultToolbar.rightFour().text("R2").click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAdapter.notifyItemChanged(2);
            }
        });
        defaultToolbar.rightThree().text("C").click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoItem2View item2View = (DemoItem2View) listAdapter.getItem(2);
                item2View.setData("333333333333333333");
                DemoItem2View item3View = (DemoItem2View) listAdapter.getItem(3);
                item3View.setData("1111111111111111111");
                item3View.notifyItemChanged();
            }
        });
    }
}
