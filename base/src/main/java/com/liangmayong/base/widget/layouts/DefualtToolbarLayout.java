package com.liangmayong.base.widget.layouts;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.liangmayong.base.R;

/**
 * Created by LiangMaYong on 2016/9/30.
 */
public class DefualtToolbarLayout extends LinearLayout {

    public DefualtToolbarLayout(Context context) {
        super(context);
        includeDefualtToolbar();
    }

    public DefualtToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        includeDefualtToolbar();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public DefualtToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        includeDefualtToolbar();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DefualtToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        includeDefualtToolbar();
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        includeDefualtToolbar();
    }

    private void includeDefualtToolbar() {
        setVerticalGravity(VERTICAL);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.base_defualt_toolbar, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(view);
    }
}
