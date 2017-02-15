package com.liangmayong.base.widget.skinview.interfaces;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.liangmayong.base.R;
import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.support.skin.handlers.SkinType;
import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.support.skin.listeners.OnSkinRefreshListener;

/**
 * Created by LiangMaYong on 2017/2/14.
 */

public class SkinViewHandler implements SkinViewHandlerInterface, OnSkinRefreshListener {

    public interface OnSkinColorListener {
        void onColor(int color, int textColor);
    }

    public SkinViewHandler(View view, OnSkinColorListener skinColorListener) {
        this.view = view;
        this.skinColorListener = skinColorListener;
    }

    private OnSkinColorListener skinColorListener = null;
    private ISkin skin = null;
    private int mSkinColor;
    private int mSkinTextColor;
    private boolean mSetSkinColor = false;
    private boolean mSetSkinTextColor = false;
    private SkinType skinType = SkinType.default_type;
    private View view;

    // skinRefreshListener
    private OnSkinRefreshListener skinRefreshListener = null;

    @Override
    public void setSkinRefreshListener(OnSkinRefreshListener skinRefreshListener) {
        this.skinRefreshListener = skinRefreshListener;
    }

    @Override
    public void setSkinType(SkinType skinType) {
        this.skinType = skinType;
        this.mSetSkinColor = false;
        this.mSetSkinTextColor = false;
        if (skin != null) {
            onSkinRefresh(skin);
        }
    }

    @Override
    public SkinType getSkinType() {
        return skinType;
    }

    @Override
    public void setSkinColor(int skinColor, int skinTextColor) {
        this.mSkinColor = skinColor;
        this.mSkinTextColor = skinTextColor;
        this.mSetSkinColor = true;
        this.mSetSkinTextColor = true;
        if (skin != null) {
            onSkinRefresh(skin);
        }
    }

    @Override
    public int getSkinColor() {
        if (!mSetSkinColor) {
            if (view.isInEditMode()) {
                int mShowColor = 0xff333333;
                switch (skinType) {
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
                return SkinManager.get().getColor(skinType);
            }
        }
        return mSkinColor;
    }

    @Override
    public int getSkinTextColor() {
        if (!mSetSkinTextColor) {
            if (view.isInEditMode()) {
                return 0xffffffff;
            } else {
                return SkinManager.get().getTextColor(skinType);
            }
        }
        return mSkinTextColor;
    }

    @Override
    public void onSkinRefresh(ISkin skin) {
        this.skin = skin;
        if (skinRefreshListener != null) {
            skinRefreshListener.onSkinRefresh(skin);
        }
        if (skinColorListener != null) {
            skinColorListener.onColor(getSkinColor(), getSkinTextColor());
        }
    }

    public void initAttributeSet(final AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.SkinViewStyleable);
            int skin = typedArray.getInt(R.styleable.SkinViewStyleable_skin_type, skinType.value());
            skinType = SkinType.valueOf(skin);
            if (typedArray.hasValue(R.styleable.SkinViewStyleable_skin_color)) {
                mSkinColor = typedArray.getColor(R.styleable.SkinViewStyleable_skin_color, SkinManager.get().getColor(skinType));
                mSetSkinColor = true;
            }
            if (typedArray.hasValue(R.styleable.SkinViewStyleable_skin_text_color)) {
                mSkinTextColor = typedArray.getColor(R.styleable.SkinViewStyleable_skin_text_color, SkinManager.get().getTextColor(skinType));
                mSetSkinTextColor = true;
            }
            typedArray.recycle();
        }
    }
}
