package com.liangmayong.base.utils.toast;

import android.view.View;

/**
 * Created by ttt on 2016/7/5.
 */
public interface IToast {

    IToast setGravity(int gravity, int xOffset, int yOffset);

    IToast setDuration(long durationMillis);

    IToast setView(View view);

    IToast setMargin(float horizontalMargin, float verticalMargin);

    IToast setText(String text);

    void show();

    void cancel();
}
