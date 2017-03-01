package com.liangmayong.base.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.liangmayong.base.R;

/**
 * Created by LiangMaYong on 2017/3/1.
 */
public class RectangleLayout extends LinearLayout {

    // ratio
    private float ratio = 1;

    public RectangleLayout(Context context) {
        super(context);
        init(null);
    }

    public RectangleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public RectangleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RectangleLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().getTheme()
                    .obtainStyledAttributes(attrs, R.styleable.RectangleLayout, 0, 0);
            try {
                ratio = a.getFloat(R.styleable.RectangleLayout_ratio, ratio);
            } finally {
                a.recycle();
            }
        }
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = Math.round(width * ratio);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, mode), MeasureSpec.makeMeasureSpec(height, mode));
    }

    /**
     * setRatio
     *
     * @param ratio ratio width / height
     */
    public void setRatio(float ratio) {
        this.ratio = ratio;
        invalidate();
    }

    /**
     * getRatio
     *
     * @return ratio
     */
    public float getRatio() {
        return ratio;
    }
}
