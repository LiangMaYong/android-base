package com.liangmayong.base.widget.iconfont;

import android.content.Context;
import android.util.AttributeSet;


/**
 * IconView
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class IconView extends FontTextView {

    public IconView(Context context) {
        super(context);
    }

    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public IconView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * set icon
     *
     * @param icon icon
     */
    public void setIcon(FontValue icon) {
        super.setText(icon);
    }
}
