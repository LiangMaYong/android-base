package com.liangmayong.base.widget.skin;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.liangmayong.base.widget.skin.OnSkinRefreshListener;
import com.liangmayong.base.widget.skin.Skin;

/**
 * Created by LiangMaYong on 2016/10/18.
 */
public class SkinSwipeLayout extends SwipeRefreshLayout implements OnSkinRefreshListener {

    // viewGroup
    private ViewGroup viewGroup;

    public SkinSwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SkinSwipeLayout(Context context) {
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

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) return;
        Skin.registerSkinRefresh(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isInEditMode()) return;
        Skin.unregisterSkinRefresh(this);
    }

    @Override
    public void onSkinRefresh(Skin skin) {
        setColorSchemeColors(skin.getThemeColor());
    }
}
