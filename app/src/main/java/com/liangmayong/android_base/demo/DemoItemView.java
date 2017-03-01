package com.liangmayong.android_base.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liangmayong.android_base.R;
import com.liangmayong.base.support.adapter.SuperItemView;

/**
 * Created by LiangMaYong on 2016/9/25.
 */
public class DemoItemView extends SuperItemView<Integer> {
    private TextView tv_txt;

    public DemoItemView(Integer s) {
        super(s);
    }

    @Override
    public View newView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.item_view, parent, false);
    }

    @Override
    public void bindView(View view, Integer s) {
        tv_txt = (TextView) view.findViewById(R.id.tv_txt);
        tv_txt.setText(s + "-----------");
    }
}

