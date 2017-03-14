package com.liangmayong.base.widget.themeview;

import android.content.Context;
import android.util.AttributeSet;

import com.liangmayong.base.support.theme.ThemeManager;
import com.liangmayong.base.support.theme.ThemeType;
import com.liangmayong.base.support.theme.listener.OnThemeListener;
import com.liangmayong.base.widget.themeview.interfaces.ThemeViewHandler;
import com.liangmayong.base.widget.themeview.interfaces.ThemeViewHandlerInterface;
import com.liangmayong.base.widget.view.SegmentView;

/**
 * Created by LiangMaYong on 2016/9/27.
 */
public class ThemeSegmentView extends SegmentView implements ThemeViewHandlerInterface {

    private ThemeViewHandler handler = null;

    public ThemeSegmentView(Context context) {
        super(context);
        init(null);
    }

    public ThemeSegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ThemeSegmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        handler = new ThemeViewHandler(this, new ThemeViewHandler.OnThemeColorListener() {
            @Override
            public void onColor(int color, int textColor) {
                setSegmentColors(textColor, color);
            }
        });
        handler.initAttributeSet(attrs);
        if (isInEditMode()) {
            setSegmentColors(handler.getThemeTextColor(), handler.getThemeColor());
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
     * @param themeListener skinRefreshListener
     */
    public void setThemeListener(OnThemeListener themeListener) {
        handler.setThemeListener(themeListener);
    }

    /**
     * setThemeType
     *
     * @param themeType themeType
     */
    public void setThemeType(ThemeType themeType) {
        handler.setThemeType(themeType);
    }

    public ThemeType getThemeType() {
        return handler.getThemeType();
    }

    @Override
    public void setThemeColor(int skinColor, int skinTextColor) {
        handler.setThemeColor(skinColor, skinTextColor);
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
