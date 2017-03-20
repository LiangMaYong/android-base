package com.liangmayong.base.widget.themeview.interfaces;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.liangmayong.base.R;
import com.liangmayong.base.support.theme.Theme;
import com.liangmayong.base.support.theme.ThemeManager;
import com.liangmayong.base.support.theme.ThemeType;
import com.liangmayong.base.support.theme.listener.OnThemeListener;

/**
 * Created by LiangMaYong on 2017/2/14.
 */

public class ThemeViewHandler implements ThemeViewHandlerInterface, OnThemeListener {

    public interface OnThemeColorListener {
        void onColor(int color, int textColor);
    }

    public ThemeViewHandler(View view, OnThemeColorListener themeColorListener) {
        this.view = view;
        this.themeColorListener = themeColorListener;
    }

    private OnThemeColorListener themeColorListener = null;
    private Theme theme = null;
    private int mThemeColor;
    private int mThemeTextColor;
    private boolean mSetThemeColor = false;
    private boolean mSetThemeTextColor = false;
    private ThemeType themeType = ThemeType.default_type;
    private View view;

    // themeRefreshListener
    private OnThemeListener themeRefreshListener = null;

    @Override
    public void setThemeListener(OnThemeListener themeListener) {
        this.themeRefreshListener = themeListener;
    }

    public void setThemeType(ThemeType themeType) {
        this.themeType = themeType;
        this.mSetThemeColor = false;
        this.mSetThemeTextColor = false;
        if (theme != null) {
            onThemeEdited(theme);
        }
    }

    public ThemeType getThemeType() {
        return themeType;
    }

    @Override
    public void setThemeColor(int themeColor, int themeTextColor) {
        this.mThemeColor = themeColor;
        this.mThemeTextColor = themeTextColor;
        this.mSetThemeColor = true;
        this.mSetThemeTextColor = true;
        if (theme != null) {
            onThemeEdited(theme);
        }
    }

    @Override
    public int getThemeColor() {
        if (!mSetThemeColor) {
            if (view.isInEditMode()) {
                int mShowColor = 0xff333333;
                switch (themeType) {
                    case gray:
                        mShowColor = 0xffb1b1b1;
                        break;
                    case primary:
                        mShowColor = 0xff428bca;
                        break;
                    case success:
                        mShowColor = 0xff5cb85c;
                        break;
                    case warning:
                        mShowColor = 0xfff0ad4e;
                        break;
                    case info:
                        mShowColor = 0xff5bc0de;
                        break;
                    case danger:
                        mShowColor = 0xffd9534f;
                        break;
                    case white:
                        mShowColor = 0xffffffff;
                        break;
                    case default_type:
                        mShowColor = view.getContext().getResources().getColor(R.color.colorPrimary);
                        break;
                }
                return mShowColor;
            } else {
                return ThemeManager.getTheme().getColor(themeType);
            }
        }
        return mThemeColor;
    }

    @Override
    public int getThemeTextColor() {
        if (!mSetThemeTextColor) {
            if (view.isInEditMode()) {
                return 0xffffffff;
            } else {
                return ThemeManager.getTheme().getTextColor(themeType);
            }
        }
        return mThemeTextColor;
    }

    @Override
    public void onThemeEdited(Theme theme) {
        this.theme = theme;
        if (themeRefreshListener != null) {
            themeRefreshListener.onThemeEdited(theme);
        }
        if (themeColorListener != null) {
            themeColorListener.onColor(getThemeColor(), getThemeTextColor());
        }
    }

    public void initAttributeSet(final AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.ThemeView);
            int theme = typedArray.getInt(R.styleable.ThemeView_theme_type, themeType.value());
            themeType = ThemeType.valueOf(theme);
            if (typedArray.hasValue(R.styleable.ThemeView_theme_color)) {
                mThemeColor = typedArray.getColor(R.styleable.ThemeView_theme_color, ThemeManager.getTheme().getColor(themeType));
                mSetThemeColor = true;
            }
            if (typedArray.hasValue(R.styleable.ThemeView_theme_text_color)) {
                mThemeTextColor = typedArray.getColor(R.styleable.ThemeView_theme_text_color, ThemeManager.getTheme().getTextColor(themeType));
                mSetThemeTextColor = true;
            }
            typedArray.recycle();
        }
    }
}
