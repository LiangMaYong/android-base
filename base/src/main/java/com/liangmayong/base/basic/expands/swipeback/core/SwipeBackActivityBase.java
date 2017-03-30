package com.liangmayong.base.basic.expands.swipeback.core;

/**
 * @author Yrom
 */
public interface SwipeBackActivityBase {

    SwipeBackLayout getSwipeBackLayout();

    void setSwipeBackEnable(boolean enable);

    void scrollToFinishActivity();

}
