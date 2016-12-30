package com.liangmayong.base.widget.skinview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.support.skin.listeners.OnSkinRefreshListener;
import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.widget.interfaces.IRefresh;

/**
 * Created by LiangMaYong on 2016/12/7.
 */
public class SkinSwipeLayout extends SwipeRefreshLayout implements OnSkinRefreshListener, IRefresh {

    // childView
    private ViewGroup childView;

    public SkinSwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SkinSwipeLayout(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (childView != null) {
            if (childView.getScrollY() > 1) {
                return false;
            }
        }
        return super.onTouchEvent(arg0);
    }

    public ViewGroup getChildView() {
        return childView;
    }

    public void setChildView(ViewGroup childView) {
        this.childView = childView;
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    @Override
    public void setOnRefreshListener(final IRefresh.OnRefreshListener listener) {
        super.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (listener != null) {
                    listener.onRefresh();
                } else {
                    setRefreshing(false);
                }
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) return;
        SkinManager.registerSkinRefresh(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isInEditMode()) return;
        SkinManager.unregisterSkinRefresh(this);
    }

    @Override
    public void onSkinRefresh(ISkin skin) {
        setColorSchemeColors(skin.getThemeColor());
    }
}
