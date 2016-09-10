package com.liangmayong.base.widget.pullrefresh.drawables;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.liangmayong.base.widget.pullrefresh.PullRefreshLayout;

/**
 * Created by LiangMaYong on 2016/8/24.
 */
public abstract class RefreshDrawable extends Drawable implements Drawable.Callback, Animatable {

    private PullRefreshLayout mRefreshLayout;

    public RefreshDrawable(PullRefreshLayout layout) {
        mRefreshLayout = layout;
    }

    public Context getContext() {
        return mRefreshLayout != null ? mRefreshLayout.getContext() : null;
    }

    public PullRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    public abstract void setPercent(float percent);

    public abstract void offsetTopAndBottom(int offset);

    public abstract void setMoveY(int moveY);

    @Override
    public void invalidateDrawable(Drawable who) {
        if (Build.VERSION.SDK_INT >= 11) {
            final Callback callback = getCallback();
            if (callback != null) {
                callback.invalidateDrawable(this);
            }
        }
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (Build.VERSION.SDK_INT >= 11) {
            final Callback callback = getCallback();
            if (callback != null) {
                callback.scheduleDrawable(this, what, when);
            }
        }
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        if (Build.VERSION.SDK_INT >= 11) {
            final Callback callback = getCallback();
            if (callback != null) {
                callback.unscheduleDrawable(this, what);
            }
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }
}
