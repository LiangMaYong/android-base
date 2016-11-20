package com.liangmayong.base.utils.toast;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by ttt on 2016/7/5.
 */
public class SystemToast implements IToast {

    private Toast mToast;

    private Context mContext;

    public static IToast makeText(Context context, CharSequence text, long duration) {
        return new SystemToast(context)
                .setText(text)
                .setDuration(duration);
    }

    public SystemToast(Context context) {
        mContext = context;
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    @Override
    public IToast setGravity(int gravity, int xOffset, int yOffset) {
        mToast.setGravity(gravity, xOffset, yOffset);
        return this;
    }

    @Override
    public IToast setDuration(long durationMillis) {
        mToast.setDuration((int) durationMillis);
        return this;
    }

    @Override
    public IToast setView(View view) {
        mToast.setView(view);
        return this;
    }

    @Override
    public IToast setMargin(float horizontalMargin, float verticalMargin) {
        mToast.setMargin(horizontalMargin, verticalMargin);
        return this;
    }

    @Override
    public IToast setText(CharSequence text) {
        mToast.setText(text);
        return this;
    }

    @Override
    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }

    @Override
    public void cancel() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
