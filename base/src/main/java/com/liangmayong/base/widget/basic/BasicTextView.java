package com.liangmayong.base.widget.basic;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.liangmayong.base.R;
import com.liangmayong.base.widget.iconfont.FontValue;
import com.liangmayong.base.widget.iconfont.IconFont;

/**
 * Created by LiangMaYong on 2017/1/18.
 */
public class BasicTextView extends TextView {

    private String fontPath = "";

    public BasicTextView(Context context) {
        super(context);
        init(null);
    }

    public BasicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BasicTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BasicTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    /**
     * init view
     *
     * @param attrs attrs set
     */
    private void init(AttributeSet attrs) {
        String iconfontPath = IconFont.PATH;
        if (attrs != null) {
            TypedArray a = getContext().getTheme()
                    .obtainStyledAttributes(attrs, R.styleable.BasicTextView, 0, 0);
            try {
                if (a.hasValue(R.styleable.BasicTextView_fontPath)) {
                    iconfontPath = a.getString(R.styleable.BasicTextView_fontPath);
                }
            } finally {
                a.recycle();
            }
        }
        setFontTypeface(iconfontPath);
    }

    /**
     * setStateColor
     *
     * @param color   color
     * @param pressed pressed
     */
    public void setStateColor(int color, int pressed) {
        int statePressed = android.R.attr.state_pressed;
        int stateFocesed = android.R.attr.state_focused;
        int[][] state = {{statePressed}, {-statePressed}, {stateFocesed}, {-stateFocesed}};
        ColorStateList colors = new ColorStateList(state, new int[]{pressed, color, pressed, color});
        setTextColor(colors);
    }

    /**
     * setFontText
     *
     * @param fontPath fontPath
     * @param text     text
     */
    public void setFontText(String fontPath, CharSequence text) {
        setFontTypeface(fontPath);
        setText(text);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text instanceof FontValue) {
            setFontTypeface(((FontValue) text).getFont());
        }
        super.setText(text, type);
    }

    /**
     * setFontTypeface
     *
     * @param fontPath fontPath
     */
    public void setFontTypeface(String fontPath) {
        if (!this.fontPath.equals(fontPath)) {
            this.fontPath = fontPath;
        } else {
            return;
        }
        if (!isInEditMode()) {
            if (BasicFontTypeface.getFont(getContext(), fontPath) != null) {
                this.setTypeface(BasicFontTypeface.getFont(getContext(), fontPath));
            }
        }
    }
}
