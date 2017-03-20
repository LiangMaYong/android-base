package com.liangmayong.base.widget.themeview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.liangmayong.base.support.theme.ThemeManager;
import com.liangmayong.base.support.theme.ThemeType;
import com.liangmayong.base.support.theme.listener.OnThemeListener;
import com.liangmayong.base.widget.themeview.interfaces.ThemeViewHandler;
import com.liangmayong.base.widget.themeview.interfaces.ThemeViewHandlerInterface;

/**
 * Created by LiangMaYong on 2016/12/7.
 */
public class ThemeSwipeLayout extends SwipeRefreshLayout implements ThemeViewHandlerInterface {

    private ThemeViewHandler handler = null;
    // childView
    private ViewGroup childView;

    public ThemeSwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ThemeSwipeLayout(Context context) {
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

    /**
     * init
     *
     * @param attrs attrs
     */
    private void init(AttributeSet attrs) {
        handler = new ThemeViewHandler(this, new ThemeViewHandler.OnThemeColorListener() {
            @Override
            public void onColor(int color, int textColor) {
                setColorSchemeColors(color);
            }
        });
        handler.initAttributeSet(attrs);
        if (isInEditMode()) {
            setColorSchemeColors(handler.getThemeColor());
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) return;
        ThemeManager.registerThemeListener(handler);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isInEditMode()) return;
        ThemeManager.unregisterThemeListener(handler);
    }

    /**
     * setThemeListener
     *
     * @param themeListener themeListener
     */
    public void setThemeListener(OnThemeListener themeListener) {
        handler.setThemeListener(themeListener);
    }

    public void setThemeType(ThemeType themeType) {
        handler.setThemeType(themeType);
    }

    public ThemeType getThemeType() {
        return handler.getThemeType();
    }

    @Override
    public void setThemeColor(int themeColor, int themeTextColor) {
        handler.setThemeColor(themeColor, themeTextColor);
    }

    @Override
    public int getThemeColor() {
        return handler.getThemeColor();
    }

    @Override
    public int getThemeTextColor() {
        return handler.getThemeTextColor();
    }
}
