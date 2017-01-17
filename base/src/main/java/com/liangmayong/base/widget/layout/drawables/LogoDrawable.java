package com.liangmayong.base.widget.layout.drawables;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;

import com.liangmayong.base.R;
import com.liangmayong.base.widget.layout.PullRefreshLayout;

import java.security.InvalidParameterException;

/**
 * Created by LiangMaYong on 2016/8/24.
 */
public class LogoDrawable extends RefreshDrawable {

    public static LogoDrawable newDrawable(PullRefreshLayout refreshLayout) {
        return new LogoDrawable(refreshLayout, R.mipmap.base_default_pull_loading_1, R.mipmap.base_default_pull_loading_2, R.mipmap.base_default_pull_loading_3, R.mipmap.base_default_pull_loading_4, R.mipmap.base_default_pull_loading_5, R.mipmap.base_default_pull_loading_6);
    }

    private Paint mPaint;

    private boolean isRunning;

    private boolean moveRefresh = true;

    private boolean elastic = false;

    private int refreshDelay = 150;

    private int picW = 0;

    private int picH = 0;

    private int currentPercent = 0;

    private int currentMoveY = 0;

    private int[] drawBitmap;

    public LogoDrawable(PullRefreshLayout layout, int... resIds) {
        super(layout);
        if (resIds == null || resIds.length < 2)
            throw new InvalidParameterException("The resIds length must be >= 2");
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dp2px(3));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        drawBitmap = resIds;
    }


    /**
     * setRefreshDelay
     *
     * @param refreshDelay refreshDelay
     */
    public void setRefreshDelay(int refreshDelay) {
        this.refreshDelay = refreshDelay;
    }

    /**
     * setElastic
     *
     * @param elastic elastic
     */
    public void setElastic(boolean elastic) {
        this.elastic = elastic;
    }

    /**
     * setMoveRefresh
     *
     * @param moveRefresh moveRefresh
     */
    public void setMoveRefresh(boolean moveRefresh) {
        this.moveRefresh = moveRefresh;
    }

    @Override
    public void setPercent(float percent) {
        currentPercent = (int) (percent * 10) - 3;
    }


    @Override
    public void offsetTopAndBottom(int offset) {
    }

    @Override
    public void setMoveY(int moveY) {
        if (moveY < 0) {
            currentMoveY = 0;
        } else {
            currentMoveY = moveY;
        }
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        if (!refreshing) {
            setMoveY(0);
        }
    }

    @Override
    public void start() {
        isRunning = true;
        invalidateSelf();
    }

    @Override
    public void stop() {
        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    private int i = 0;

    @Override
    public void draw(Canvas canvas) {
        if (currentMoveY > 0) {
            Bitmap bitmap = getCurrentBitmap();
            picH = bitmap.getHeight();
            picW = bitmap.getWidth();
            int x = getRefreshLayout().getWidth() / 2 - (picW / 2);
            int y = 0;
            int height = picH;
            int width = picW;
            if (currentMoveY <= picH) {
                if (moveRefresh) {
                    y = currentMoveY - picH;
                } else {
                    y = picH - currentMoveY;
                }
            }
            if (elastic) {
                y = 0;
                height = currentMoveY;
                if (currentMoveY > picH) {
                    height = picH;
                }
            }
            drawImage(canvas, bitmap, x, y, width, height);
            if (i == drawBitmap.length) {
                i = 0;
            }
        }
        canvas.save();
        canvas.restore();
        if (currentMoveY > 0) {
            if (isRunning) {
                getRefreshLayout().postDelayed(invadateRun, refreshDelay);
            } else {
                invalidateSelf();
            }
        }
    }

    private Runnable invadateRun = new Runnable() {
        @Override
        public void run() {
            invalidateSelf();
        }
    };

    /**
     * getCurrentBitmap
     *
     * @return bitmap
     */
    private Bitmap getCurrentBitmap() {
        if (isRunning) {
            return getFrameBitmap(i++);
        } else {
            int index = 0;
            if (moveRefresh) {
                if (currentPercent <= 0) {
                    index = 0;
                } else if (currentPercent >= drawBitmap.length) {
                    index = drawBitmap.length - 1;
                } else {
                    index = currentPercent;
                }
                if (currentMoveY > picH) {
                    index = drawBitmap.length - 1;
                }
            }
            return getFrameBitmap(index);
        }
    }

    protected Bitmap getFrameBitmap(int index) {
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),
                drawBitmap[index]);
        return bitmap;
    }

    /**
     * drawImage
     *
     * @param canvas canvas
     * @param bitmap bitmap
     * @param x      x
     * @param y      y
     * @param w      w
     * @param h      h
     */
    private void drawImage(Canvas canvas, Bitmap bitmap, int x, int y, int w, int h) {
        Rect dst = new Rect();
        dst.left = x;
        dst.top = y;
        dst.right = x + w;
        dst.bottom = y + h;

        canvas.drawBitmap(bitmap, null, dst, null);
        dst = null;
    }

    /**
     * dp2px
     *
     * @param dp dp
     * @return px
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

}