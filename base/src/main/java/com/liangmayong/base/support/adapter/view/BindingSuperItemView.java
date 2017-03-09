package com.liangmayong.base.support.adapter.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangmayong.base.binding.view.ViewBinding;
import com.liangmayong.base.support.adapter.SuperItemView;

/**
 * Created by LiangMaYong on 2016/11/7.
 */
public abstract class BindingSuperItemView<Data> extends SuperItemView<Data> {

    public BindingSuperItemView(Data data) {
        super(data);
    }

    @Override
    protected final View newView(LayoutInflater inflater, ViewGroup parent) {
        View rootView = onNewView(inflater, parent);
        if (rootView == null) {
            rootView = ViewBinding.parserClassByLayout(this, parent);
        }
        return rootView;
    }

    @Override
    public final void bindView(View itemView, final Data data) {
        ViewBinding.parserClassByView(BindingSuperItemView.this, itemView);
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
