package com.liangmayong.base.widget.basic.interfaces;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.liangmayong.base.R;

/**
 * Created by LiangMaYong on 2017/2/15.
 */

public class BasicViewHandler implements BasicViewHandlerInterface {

    // paint
    private Paint mPressedPaint = null;
    private Paint mUnpressedPaint = null;
    private Paint mUnpressedCoverPaint = null;

    // color
    private int mPressedColor = 0x55333333;
    private int mUnpressedColor = 0xff333333;
    private int mUnpressedCoverColor = Color.TRANSPARENT;

    // bool
    private boolean mClickable = false;
    private boolean mUnpressedTransparent = false;

    public BasicViewHandler(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BasicViewHandler);
            mPressedColor = typedArray.getColor(R.styleable.BasicViewHandler_pressed_color, mPressedColor);
            mUnpressedColor = typedArray.getColor(R.styleable.BasicViewHandler_unpressed_color, mUnpressedColor);
            mUnpressedCoverColor = typedArray.getColor(R.styleable.BasicViewHandler_unpressed_cover_color, mUnpressedCoverColor);
            mUnpressedTransparent = typedArray.getBoolean(R.styleable.BasicViewHandler_unpressed_transparent, mUnpressedTransparent);
            typedArray.recycle();
        }
        invalidate();
    }

    public void invalidate() {
        // mPressedPaint
        if (mPressedPaint == null) {
            mPressedPaint = new Paint();
        }
        mPressedPaint.setAntiAlias(true);
        mPressedPaint.setStyle(Paint.Style.FILL);
        mPressedPaint.setColor(mPressedColor);
        if (!isClickable()) {
            mPressedPaint.setAlpha(0);
        }

        // mUnpressedPaint
        if (mUnpressedPaint == null) {
            mUnpressedPaint = new Paint();
        }
        mUnpressedPaint.setAntiAlias(true);
        mUnpressedPaint.setStyle(Paint.Style.FILL);
        mUnpressedPaint.setColor(mUnpressedColor);

        // mUnpressedCoverPaint
        if (mUnpressedCoverPaint == null) {
            mUnpressedCoverPaint = new Paint();
        }
        mUnpressedCoverPaint.setAntiAlias(true);
        mUnpressedCoverPaint.setStyle(Paint.Style.FILL);
        mUnpressedCoverPaint.setColor(mUnpressedCoverColor);
    }

    public Paint getPressedPaint() {
        return mPressedPaint;
    }

    public Paint getUnpressedPaint() {
        return mUnpressedPaint;
    }

    public Paint getUnpressedCoverPaint() {
        return mUnpressedCoverPaint;
    }

    public boolean isUnpressedTransparent() {
        return mUnpressedTransparent;
    }

    @Override
    public void setUnpressedTransparent(boolean transparent) {
        this.mUnpressedTransparent = transparent;
        invalidate();
    }

    public void setClickable(boolean clickable) {
        this.mClickable = clickable;
        invalidate();
    }

    @Override
    public boolean isClickable() {
        return mClickable;
    }

    @Override
    public int getPressedColor() {
        return mPressedColor;
    }

    @Override
    public void setPressedColor(int pressedColor) {
        mPressedPaint.setColor(mPressedColor);
        invalidate();
    }

    @Override
    public int getUnpressedColor() {
        return mUnpressedColor;
    }

    @Override
    public void setUnpressedColor(int color) {
        this.mUnpressedColor = color;
        invalidate();
    }

    @Override
    public int getUnpressedCoverColor() {
        return mUnpressedCoverColor;
    }

    @Override
    public void setUnpressedCoverColor(int color) {
        this.mUnpressedCoverColor = color;
        invalidate();
    }

}
