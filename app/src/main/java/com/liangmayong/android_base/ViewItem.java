package com.liangmayong.android_base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangmayong.base.widget.superlistview.SuperListView;

/**
 * Created by LiangMaYong on 2016/9/25.
 */
public class ViewItem extends SuperListView.Item<String> {

    public ViewItem(String s) {
        super(s);
    }

    @Override
    protected View newView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.item_view, null);
    }

    @Override
    protected void bindView(View itemView, String s) {

    }
}
