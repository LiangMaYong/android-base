package com.liangmayong.base.widget.basic.interfaces;

/**
 * Created by LiangMaYong on 2016/9/28.
 */
public interface BasicViewInterface {

    int SHAPE_TYPE_ROUND = BasicViewDrawer.SHAPE_TYPE_ROUND;
    int SHAPE_TYPE_RECTANGLE = BasicViewDrawer.SHAPE_TYPE_RECTANGLE;
    int SHAPE_TYPE_STROKE = BasicViewDrawer.SHAPE_TYPE_STROKE;
    int SHAPE_TYPE_OVAL = BasicViewDrawer.SHAPE_TYPE_OVAL;
    int SHAPE_TYPE_TRANSPARENT = BasicViewDrawer.SHAPE_TYPE_TRANSPARENT;

    int getPressedColor();

    void setPressedColor(int pressedColor);

    int getUnpressedColor();

    void setUnpressedColor(int color);

    int getUnpressedCoverColor();

    void setUnpressedCoverColor(int color);

    int getShapeType();

    void setShapeType(int shapeType);

    int getRadius();

    int getStrokeWidth();

    void setRadius(int radius);

    void setStrokeWidth(int mStrokeWidth);
}
