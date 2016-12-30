package com.liangmayong.base.basic.flow.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.liangmayong.base.basic.flow.FlowBaseFragment;

/**
 * Created by LiangMaYong on 2016/12/29.
 */
@SuppressLint("ValidFragment")
public class StackErrorFragment extends FlowBaseFragment {

    private final String error;

    public StackErrorFragment(String error) {
        this.error = error;
    }

    @Override
    protected void initViews(View containerView) {

    }

    @Override
    protected View getContaierView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout frameLayout = new FrameLayout(inflater.getContext());
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        TextView textView = new TextView(inflater.getContext());
        frameLayout.addView(textView);
        textView.setTextSize(14);
        textView.setGravity(Gravity.CENTER);
        textView.setText(error + "");
        textView.setTextColor(0xff18a28b);
        return frameLayout;
    }

    @Override
    protected int getContaierLayoutId() {
        return 0;
    }
}
