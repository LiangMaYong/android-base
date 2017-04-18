package com.liangmayong.android_base.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.liangmayong.android_base.ImageActivity;
import com.liangmayong.android_base.itemviews.TextItemView;
import com.liangmayong.android_base.itemviews.ImageItemView;
import com.liangmayong.base.basic.expands.list.FlowListFragment;
import com.liangmayong.base.binding.view.annotations.BindTitle;
import com.liangmayong.base.support.adapter.SuperItemView;
import com.liangmayong.base.support.adapter.SuperListAdapter;
import com.liangmayong.base.support.transitions.ActivityTransitionLauncher;
import com.liangmayong.base.support.utils.GoToUtils;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@BindTitle("Main")
public class ListFragment extends FlowListFragment {

    private SuperListAdapter listAdapter = null;

    @Override
    protected void initListViews(final ListView listView) {
        listAdapter = new SuperListAdapter();
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SuperItemView superItemView = listAdapter.getItem(position);
                if (superItemView instanceof TextItemView) {
                    final Intent intent = new Intent(getContext(), ImageActivity.class);
                    ActivityTransitionLauncher.with(getActivity()).from(view).launch(intent);
                } else {
                    GoToUtils.goWeb(getActivity(), "", "file:///android_asset/demo.html");
                }
            }
        });
        refreshList();
    }

    protected void refreshList() {
        listAdapter.removeAll();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 1) {
                listAdapter.add(new TextItemView(i));
            } else {
                listAdapter.add(new ImageItemView(i));
            }
        }
        listAdapter.notifyDataSetChanged();
    }

}
