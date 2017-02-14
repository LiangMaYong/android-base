package com.liangmayong.base.widget.skinview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.liangmayong.base.support.skin.handlers.SkinType;
import com.liangmayong.base.support.skin.listeners.OnSkinRefreshListener;
import com.liangmayong.base.widget.refresh.RefreshView;
import com.liangmayong.base.widget.skinview.handler.SkinViewHandler;
import com.liangmayong.base.widget.skinview.interfaces.ISkinViewHandler;

/**
 * Created by LiangMaYong on 2016/12/7.
 */
public class SkinSwipeLayout extends SwipeRefreshLayout implements ISkinViewHandler, RefreshView {

    private SkinViewHandler handler = null;
    // childView
    private ViewGroup childView;

    public SkinSwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SkinSwipeLayout(Context context) {
        super(context);
        init(null);
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
    public void setOnRefreshListener(final RefreshView.OnRefreshListener listener) {
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

    private void init(AttributeSet attrs) {
        handler = new SkinViewHandler(this, new SkinViewHandler.OnSkinColorListener() {
            @Override
            public void onColor(int color, int textColor) {
                setColorSchemeColors(color);
            }
        });
        handler.initAttributeSet(attrs);
        if (isInEditMode()) {
            setColorSchemeColors(handler.getSkinColor());
        }
    }

    @Override
    public void setSkinRefreshListener(OnSkinRefreshListener skinRefreshListener) {
        handler.setSkinRefreshListener(skinRefreshListener);
    }

    @Override
    public void setSkinType(SkinType skinType) {
        handler.setSkinType(skinType);
    }

    @Override
    public SkinType getSkinType() {
        return handler.getSkinType();
    }

    @Override
    public void setSkinColor(int skinColor, int skinTextColor) {
        handler.setSkinColor(skinColor, skinTextColor);
    }

    @Override
    public int getSkinColor() {
        return handler.getSkinColor();
    }

    @Override
    public int getSkinTextColor() {
        return handler.getSkinTextColor();
    }
}
