package com.liangmayong.base.widget.basic;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.liangmayong.base.support.utils.DimenUtils;
import com.liangmayong.base.widget.basic.interfaces.BasicViewDrawer;
import com.liangmayong.base.widget.basic.interfaces.BasicViewHandler;
import com.liangmayong.base.widget.basic.interfaces.BasicViewInterface;

/**
 * Created by LiangMaYong on 2016/9/27.
 */
public class BasicButton extends Button implements BasicViewInterface {

    private BasicViewDrawer basicViewDrawer = null;
    private BasicViewHandler basicViewHandler = null;


    public BasicButton(Context context) {
        this(context, null);
    }


    public BasicButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs);
    }


    public BasicButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BasicButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttributeSet(context, attrs);
    }

    protected void initAttributeSet(final Context context, final AttributeSet attrs) {
        basicViewDrawer = new BasicViewDrawer(context, attrs, SHAPE_TYPE_RECTANGLE, DimenUtils.dip2px(context, 2), DimenUtils.dip2px(context, 1.4f));
        basicViewHandler = new BasicViewHandler(context, attrs);
        this.setWillNotDraw(false);
        this.setDrawingCacheEnabled(true);
        this.eraseOriginalBackgroundColor();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        basicViewDrawer.setWidth(w);
        basicViewDrawer.setHeight(h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        basicViewDrawer.onBackgroundDraw(canvas, basicViewHandler.getUnpressedPaint(), basicViewHandler.isUnpressedTransparent());
        basicViewDrawer.onBackgroundDraw(canvas, basicViewHandler.getUnpressedCoverPaint(), false);
        basicViewDrawer.onPressedDraw(canvas, basicViewHandler.getPressedPaint());
        super.onDraw(canvas);
    }


    @Override
    public void setBackgroundColor(int color) {
        eraseOriginalBackgroundColor();
    }

    protected void eraseOriginalBackgroundColor() {
        super.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isClickable()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    basicViewHandler.setClickable(true);
                    invalidate();
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    basicViewHandler.setClickable(false);
                    invalidate();
                    break;
            }
        }
        return super.onTouchEvent(event);
    }


    @Override
    public int getPressedColor() {
        return basicViewHandler.getPressedColor();
    }


    @Override
    public void setPressedColor(int pressedColor) {
        basicViewHandler.setPressedColor(pressedColor);
        invalidate();
    }

    @Override
    public int getUnpressedColor() {
        return basicViewHandler.getUnpressedColor();
    }


    @Override
    public void setUnpressedColor(int color) {
        basicViewHandler.setUnpressedColor(color);
        invalidate();
    }

    @Override
    public int getUnpressedCoverColor() {
        return basicViewHandler.getUnpressedCoverColor();
    }

    @Override
    public void setUnpressedCoverColor(int color) {
        basicViewHandler.setUnpressedCoverColor(color);
        invalidate();
    }

    @Override
    public int getShapeType() {
        return basicViewDrawer.getShapeType();
    }


    @Override
    public void setShapeType(int shapeType) {
        basicViewDrawer.setShapeType(shapeType);
        invalidate();
    }


    @Override
    public int getRadius() {
        return basicViewDrawer.getRadius();
    }

    @Override
    public int getStrokeWidth() {
        return basicViewDrawer.getStrokeWidth();
    }

    @Override
    public void setRadius(int radius) {
        basicViewDrawer.setRadius(radius);
        invalidate();
    }

    @Override
    public void setStrokeWidth(int mStrokeWidth) {
        basicViewDrawer.setStrokeWidth(mStrokeWidth);
        invalidate();
    }
}
