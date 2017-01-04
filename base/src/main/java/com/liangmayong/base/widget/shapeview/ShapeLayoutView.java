package com.liangmayong.base.widget.shapeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.liangmayong.base.R;

/**
 * Created by LiangMaYong on 2016/12/23.
 */

public class ShapeLayoutView extends RelativeLayout {

    private static final PorterDuffXfermode PORTER_DUFF_DST_IN = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private static final PorterDuffXfermode PORTER_DUFF_SRC_ATOP = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);


    private Canvas maskCanvas;
    private Bitmap maskBitmap;
    private Paint maskPaint;
    private Drawable shape;
    private Drawable cover;

    private Canvas coverCanvas;
    private Bitmap coverBitmap;
    private Paint coverPaint;

    private boolean square = false;

    private int pressedColor = 0x20333333;

    private int background = 0xffffffff;

    private boolean pressed = false;

    public ShapeLayoutView(Context context) {
        super(context);
        setup(context, null, 0);
    }

    public ShapeLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs, 0);
    }

    public ShapeLayoutView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup(context, attrs, defStyle);
    }

    /**
     * setup
     *
     * @param context  context
     * @param attrs    attrs
     * @param defStyle defStyle
     */
    private void setup(Context context, AttributeSet attrs, int defStyle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeViewStyleable, defStyle, 0);
            this.square = typedArray.getBoolean(R.styleable.ShapeViewStyleable_shapeSquare, false);
            this.shape = typedArray.getDrawable(R.styleable.ShapeViewStyleable_shape);
            this.cover = typedArray.getDrawable(R.styleable.ShapeViewStyleable_shapeCover);
            this.pressedColor = typedArray.getColor(R.styleable.ShapeViewStyleable_shapePressed, pressedColor);
            this.background = typedArray.getColor(R.styleable.ShapeViewStyleable_shapeBackground, background);
            typedArray.recycle();
        }

        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskPaint.setColor(Color.BLACK);

        coverPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        coverPaint.setColor(Color.BLACK);

        if (isInEditMode()) {
            if (cover != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    setBackground(cover);
                } else {
                    setBackgroundDrawable(cover);
                }
            }
        } else {
            setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @SuppressWarnings("SameParameterValue")
    public void setSquare(boolean square) {
        this.square = square;
        invalidate();
    }

    public void setCover(Drawable cover) {
        this.cover = cover;
        invalidate();
    }

    public void setShape(Drawable shape) {
        this.shape = shape;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createShapeCanvas(w, h, oldw, oldh);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawColor(background);
        super.dispatchDraw(canvas);
        if (!isInEditMode()) {
            if (pressed) {
                canvas.drawColor(pressedColor);
            }
            if (maskBitmap != null) {
                maskPaint.setXfermode(PORTER_DUFF_DST_IN);
                canvas.drawBitmap(maskBitmap, 0.0f, 0.0f, maskPaint);
            }
            if (coverBitmap != null) {
                coverPaint.setXfermode(PORTER_DUFF_SRC_ATOP);
                canvas.drawBitmap(coverBitmap, 0.0f, 0.0f, coverPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isClickable()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressed = true;
                    invalidate();
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    pressed = false;
                    invalidate();
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (square) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            int dimen = Math.min(width, height);
            setMeasuredDimension(dimen, dimen);
        }
    }

    /**
     * createShapeCanvas
     *
     * @param width  width
     * @param height height
     * @param oldw   oldw
     * @param oldh   oldh
     */
    private void createShapeCanvas(int width, int height, int oldw, int oldh) {
        if (maskBitmap != null) {
            maskBitmap.recycle();
            maskBitmap = null;
        }
        if (coverBitmap != null) {
            coverBitmap.recycle();
            coverBitmap = null;
        }
        boolean sizeChanged = width != oldw || height != oldh;
        boolean isValid = width > 0 && height > 0;
        if (isValid && (maskCanvas == null || sizeChanged)) {
            if (shape != null) {
                maskCanvas = new Canvas();
                maskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
                maskCanvas.setBitmap(maskBitmap);

                maskPaint.reset();
                paintMaskCanvas(maskCanvas, maskPaint, width, height);
            }
            if (cover != null) {
                coverCanvas = new Canvas();
                coverBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
                coverCanvas.setBitmap(coverBitmap);

                coverPaint.reset();
                paintCoverCanvas(coverCanvas, coverPaint, width, height);
            }
        }
    }

    /**
     * paintMaskCanvas
     *
     * @param maskCanvas maskCanvas
     * @param maskPaint  maskPaint
     * @param width      width
     * @param height     height
     */
    private void paintMaskCanvas(Canvas maskCanvas, Paint maskPaint, int width, int height) {
        if (shape != null) {
            if (shape instanceof BitmapDrawable) {
                configureBitmapBounds(width, height);
            }
            shape.setBounds(0, 0, width, height);
            shape.draw(maskCanvas);
        }
    }

    /**
     * paintCoverCanvas
     *
     * @param topCanvas topCanvas
     * @param topPaint  topPaint
     * @param width     width
     * @param height    height
     */
    private void paintCoverCanvas(Canvas topCanvas, Paint topPaint, int width, int height) {
        if (cover != null) {
            if (cover instanceof BitmapDrawable) {
                configureBitmapBounds(width, height);
            }
            cover.setBounds(0, 0, width, height);
            cover.draw(topCanvas);
        }
    }

    /**
     * configureBitmapBounds
     *
     * @param viewWidth  viewWidth
     * @param viewHeight viewHeight
     */
    private void configureBitmapBounds(int viewWidth, int viewHeight) {
        int drawableWidth = shape.getIntrinsicWidth();
        int drawableHeight = shape.getIntrinsicHeight();
        boolean fits = viewWidth == drawableWidth && viewHeight == drawableHeight;

        if (drawableWidth > 0 && drawableHeight > 0 && !fits) {
            shape.setBounds(0, 0, drawableWidth, drawableHeight);
            float widthRatio = (float) viewWidth / (float) drawableWidth;
            float heightRatio = (float) viewHeight / (float) drawableHeight;
            float scale = Math.min(widthRatio, heightRatio);
            float dx = (int) ((viewWidth - drawableWidth * scale) * 0.5f + 0.5f);
            float dy = (int) ((viewHeight - drawableHeight * scale) * 0.5f + 0.5f);
        }
    }
}
