package com.liangmayong.base.widget.skinview.shape;

import com.liangmayong.base.support.skin.listeners.OnSkinRefreshListener;
import com.liangmayong.base.widget.skinview.interfaces.ISkinViewHandler;

/**
 * Created by LiangMaYong on 2016/9/28.
 */
public interface SkinShapeInterface extends ISkinViewHandler, OnSkinRefreshListener {

    int SHAPE_TYPE_ROUND = 0;
    int SHAPE_TYPE_RECTANGLE = 1;
    int SHAPE_TYPE_STROKE = 2;
    int SHAPE_TYPE_OVAL = 3;
    int SHAPE_TYPE_TRANSPARENT = 4;

    int getRadius();

    void setRadius(int radius);

    void setShapeType(int shapeType);

    int getShapeType();

    void setPressedColor(int pressedColor);

    int getPressedColor();

    void setStrokeWidth(int strokeWidth);

    void setBackgroundCoverColor(int color);
}
