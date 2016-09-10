package com.liangmayong.base.widget;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * PagerView
 */
public class PagerView extends ViewPager {

    public PagerView(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= 9) {
            setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    public PagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= 9) {
            setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
