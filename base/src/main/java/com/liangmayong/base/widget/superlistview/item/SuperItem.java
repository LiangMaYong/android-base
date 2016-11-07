package com.liangmayong.base.widget.superlistview.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangmayong.base.widget.binding.ViewBinding;
import com.liangmayong.base.widget.superlistview.SuperListView;

/**
 * Created by LiangMaYong on 2016/11/7.
 */
public abstract class SuperItem<Data> extends SuperListView.Item<Data> {

    public SuperItem(Data data) {
        super(data);
    }

    @Override
    protected final View newView(LayoutInflater inflater, ViewGroup parent) {
        View rootView = onNewView(inflater, parent);
        if (rootView == null) {
            rootView = ViewBinding.parserLayout(this, inflater.getContext());
        }
        return rootView;
    }

    @Override
    protected final void bindView(View itemView, Data data) {
        ViewBinding.parserClassByView(this, itemView);
        onBindView(itemView, data);
    }

    /**
     * onBindView
     *
     * @param itemView itemView
     * @param data     data
     */
    protected abstract void onBindView(View itemView, Data data);

    /**
     * onNewView
     *
     * @param inflater inflater
     * @param parent   parent
     * @return view
     */
    protected View onNewView(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }
}
