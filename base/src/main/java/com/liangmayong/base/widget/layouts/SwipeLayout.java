package com.liangmayong.base.widget.layouts;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by LiangMaYong on 2016/10/18.
 */
public class SwipeLayout extends SwipeRefreshLayout {

    // viewGroup
    private ViewGroup viewGroup;

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeLayout(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (viewGroup != null) {
            if (viewGroup.getScrollY() > 1) {
                return false;
            }
        }
        return super.onTouchEvent(arg0);
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }
}
