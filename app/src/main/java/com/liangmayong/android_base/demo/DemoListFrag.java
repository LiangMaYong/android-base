package com.liangmayong.android_base.demo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liangmayong.android_base.R;
import com.liangmayong.base.basic.expands.recycler.FlowRecyclerFragment;
import com.liangmayong.base.binding.view.annotations.BindLayout;
import com.liangmayong.base.binding.view.annotations.BindTitle;
import com.liangmayong.base.support.adapter.SuperListAdapter;
import com.liangmayong.base.support.adapter.SuperRecyclerAdapter;
import com.liangmayong.base.support.adapter.view.DefaultSuperItemView;
import com.liangmayong.base.support.toolbar.DefaultToolbar;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@BindLayout(R.layout.activity_item2)
@BindTitle("标题")
public class DemoListFrag extends FlowRecyclerFragment {

    private SuperRecyclerAdapter listAdapter = null;

    @Override
    protected void initRecyclerViews(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listAdapter = new SuperRecyclerAdapter();
        listAdapter.setReverse(true);
        listAdapter.add(new DemoItem2View("111111111111111111111"));
        listAdapter.add(new DemoItemView(1));
        recyclerView.setAdapter(listAdapter);
        listAdapter.add(new DemoItem2View("33333333333333333333333333333"));
        listAdapter.add(new DemoItem2View("33333333333333333333333333333"));
        listAdapter.add(new DefaultSuperItemView(new DefaultSuperItemView.Data("Title")));
        listAdapter.add(new DemoItemView(3));
        listAdapter.add(new DemoItemView(4));
        listAdapter.add(new DemoItemView(5));
        listAdapter.add(new DemoItem2View("33333333333333333333333333333"));
    }

    @Override
    public void onInitDefaultToolbar(DefaultToolbar defaultToolbar) {
        super.onInitDefaultToolbar(defaultToolbar);
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
