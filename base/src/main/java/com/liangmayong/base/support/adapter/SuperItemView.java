package com.liangmayong.base.support.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangmayong.base.support.adapter.interfaces.ISuperAdapter;

/**
 * Created by LiangMaYong on 2017/3/1.
 */

public abstract class SuperItemView<Data> {

    private Data data = null;
    private int position = 0;
    private Object tag = null;
    private ISuperAdapter adapter = null;

    public SuperItemView(Data data) {
        this.data = data;
    }

    /**
     * getData
     *
     * @return data
     */
    public Data getData() {
        return data;
    }

    /**
     * setData
     *
     * @param data data
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * getTag
     *
     * @return tag
     */
    public Object getTag() {
        return tag;
    }

    /**
     * setTag
     *
     * @param tag tag
     */
    public void setTag(Object tag) {
        this.tag = tag;
    }

    /**
     * getPosition
     *
     * @return position
     */
    public int getPosition() {
        return position;
    }

    /**
     * getViewType
     *
     * @return viewType
     */
    public String getViewType() {
        return getClass().getName();
    }

    /**
     * getAdapter
     *
     * @return adapter
     */
    public ISuperAdapter getAdapter() {
        return adapter;
    }

    /**
     * callBindView
     *
     * @param position position
     * @param view     view
     */
    final void callBindView(int position, View view) {
        this.position = position;
        bindView(view, data);
    }

    /**
     * callNewView
     *
     * @param parent parent
     * @return view
     */
    final View callNewView(ISuperAdapter listAdapter, int position, ViewGroup parent) {
        this.position = position;
        this.adapter = listAdapter;
        return newView(LayoutInflater.from(parent.getContext()), parent);
    }

    /**
     * newView
     *
     * @param parent parent
     * @return view
     */
    protected abstract View newView(LayoutInflater inflater, ViewGroup parent);

    /**
     * bindView
     *
     * @param view view
     * @param data data
     */
    protected abstract void bindView(View view, Data data);


    /**
     * notifyDataSetChanged
     */
    public void notifyItemChanged() {
        if (adapter != null) {
            adapter.notifyItemChanged(getPosition());
        }
    }
}
