package com.liangmayong.base.widget.basic.interfaces;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.liangmayong.base.R;
import com.liangmayong.base.support.utils.DimenUtils;

/**
 * Created by LiangMaYong on 2017/2/15.
 */
public class BasicViewDrawer {

    static final int SHAPE_TYPE_ROUND = 0;
    static final int SHAPE_TYPE_RECTANGLE = 1;
    static final int SHAPE_TYPE_STROKE = 2;
    static final int SHAPE_TYPE_OVAL = 3;
    static final int SHAPE_TYPE_TRANSPARENT = 4;

    private int mWidth;
    private int mHeight;
    private int mShapeType = SHAPE_TYPE_RECTANGLE;
    private int mRadius;
    private int mStrokeWidth;

    public BasicViewDrawer(Context context, AttributeSet attrs, int shapeType, int radius, int strokeWidth) {
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BasicViewDrawer);
            mShapeType = typedArray.getInt(R.styleable.BasicViewDrawer_shape_type, shapeType);
            mRadius = typedArray.getDimensionPixelSize(R.styleable.BasicViewDrawer_radius, radius);
            mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.BasicViewDrawer_stroke_width, strokeWidth);
            typedArray.recycle();
        }
    }

    public BasicViewDrawer(Context context, AttributeSet attrs) {
        this(context, attrs, SHAPE_TYPE_RECTANGLE, DimenUtils.dip2px(context, 0), DimenUtils.dip2px(context, 1.4f));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public int getShapeType() {
        return mShapeType;
    }

    public void setShapeType(int mShapeType) {
        this.mShapeType = mShapeType;
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int mRadius) {
        this.mRadius = mRadius;
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }

    public void onBackgroundDraw(Canvas canvas, Paint paint, boolean isTransparent) {
        if (paint == null || isTransparent) {
            return;
        }
        if (mRadius > mHeight / 2 || mRadius > mWidth / 2) {
            mRadius = Math.min(mHeight / 2, mWidth / 2);
        }
        if (mShapeType == SHAPE_TYPE_ROUND) {
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(0);
            canvas.drawCircle(mWidth / 2, mHeight / 2, Math.min(mHeight / 2, mWidth / 2),
                    paint);
        } else if (mShapeType == SHAPE_TYPE_OVAL) {
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(0);
            RectF rectF = new RectF();
            rectF.set(0, 0, mWidth, mHeight);
            canvas.drawOval(rectF, paint);
        } else if (mShapeType == SHAPE_TYPE_TRANSPARENT) {
        } else if (mShapeType == SHAPE_TYPE_STROKE) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.MITER);
            paint.setStrokeWidth(mStrokeWidth);
            canvas.drawPath(getPath(0, 0, mWidth, mHeight, mStrokeWidth, mRadius), paint);
        } else {
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(0);
            RectF rectF = new RectF();
            rectF.set(0, 0, mWidth, mHeight);
            if (mRadius > mHeight / 2 || mRadius > mWidth / 2) {
                mRadius = Math.min(mHeight / 2, mWidth / 2);
            }
            canvas.drawRoundRect(rectF, mRadius, mRadius, paint);
        }
    }


    public void onPressedDraw(Canvas canvas, Paint paint) {
        if (mShapeType == SHAPE_TYPE_ROUND) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, Math.min(mHeight / 2.1038f, mWidth / 2.1038f),
                    paint);
        } else if (mShapeType == SHAPE_TYPE_OVAL) {
            RectF rectF = new RectF();
            rectF.set(0, 0, mWidth, mHeight);
            canvas.drawOval(rectF, paint);
        } else if (mShapeType == SHAPE_TYPE_TRANSPARENT) {
        } else {
            RectF rectF = new RectF();
            rectF.set(0, 0, mWidth, mHeight);
            canvas.drawRoundRect(rectF, mRadius, mRadius, paint);
        }
    }


    protected Path getPath(int left, int top, int right, int bottom, int mStrokeWidth, int mRadius) {
        int halfStrokeWidth = mStrokeWidth / 2;
        left += halfStrokeWidth;
        top += halfStrokeWidth;
        right -= halfStrokeWidth;
        bottom -= halfStrokeWidth;

        Path path = new Path();
        path.moveTo(left + mRadius, top);
        path.lineTo(right - mRadius, top);
        path.arcTo(new RectF(right - mRadius * 2, top, right, top + mRadius * 2),
                -90, 90);
        // path.quadTo(right, top, right, top + topRightRadius);
        path.lineTo(right, bottom - mRadius);
        path.arcTo(new RectF(right - mRadius * 2, bottom - mRadius * 2, right,
                bottom), 0, 90);
        // path.quadTo(right, bottom, right - bottomRightRadius, bottom);
        path.lineTo(left + mRadius, bottom);
        path.arcTo(new RectF(left, bottom - mRadius * 2, left + mRadius * 2,
                bottom), 90, 90);
        // path.quadTo(left, bottom, left, bottom - bottomLeftRadius);
        path.lineTo(left, top + mRadius);
        path.arcTo(new RectF(left, top, left + mRadius * 2, top + mRadius * 2), 180, 90);
        // path.quadTo(left, top, left + topLeftRadius, top);
        path.close();
        return path;
    }
}
