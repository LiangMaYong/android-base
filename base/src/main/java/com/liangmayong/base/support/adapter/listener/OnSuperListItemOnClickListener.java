package com.liangmayong.base.support.adapter.listener;

import android.view.View;
import android.widget.AdapterView;

import com.liangmayong.base.support.adapter.SuperItemView;
import com.liangmayong.base.support.adapter.SuperListAdapter;

/**
 * Created by LiangMaYong on 2017/4/1.
 */
public abstract class OnSuperListItemOnClickListener implements AdapterView.OnItemClickListener {

    private final SuperListAdapter listAdapter;

    public OnSuperListItemOnClickListener(SuperListAdapter listAdapter) {
        this.listAdapter = listAdapter;
    }

    public abstract void onItemClick(int position, Object data);

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (listAdapter != null) {
            SuperItemView superItemView = listAdapter.getItem(position);
            if (superItemView != null) {
                onItemClick(position, superItemView.getData());
            }
        }
    }
}
