package com.liangmayong.base.widget.basic.interfaces;

import android.graphics.Paint;

/**
 * Created by LiangMaYong on 2016/9/28.
 */
public interface BasicViewHandlerInterface {

    Paint getPressedPaint();

    Paint getUnpressedPaint();

    Paint getUnpressedCoverPaint();

    void setUnpressedTransparent(boolean transparent);

    boolean isUnpressedTransparent();

    void setClickable(boolean clickable);

    boolean isClickable();

    int getPressedColor();

    void setPressedColor(int pressedColor);

    int getUnpressedColor();

    void setUnpressedColor(int color);

    int getUnpressedCoverColor();

    void setUnpressedCoverColor(int color);
}
