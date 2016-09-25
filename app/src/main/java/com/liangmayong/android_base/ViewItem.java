package com.liangmayong.android_base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangmayong.base.widget.relistview.ReListView;

/**
 * Created by LiangMaYong on 2016/9/25.
 */
public class ViewItem extends ReListView.Item<String> {

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
