package com.liangmayong.base.widget.iconfont;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

import com.liangmayong.base.R;

/**
 * Created by LiangMaYong on 2017/1/18.
 */
public class FontView extends TextView {

    private String fontPath = "";

    public FontView(Context context) {
        super(context);
        init(null);
    }

    public FontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FontView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FontView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
                    .obtainStyledAttributes(attrs, R.styleable.BasicFontView, 0, 0);
            try {
                if (a.hasValue(R.styleable.BasicFontView_fontPath)) {
                    iconfontPath = a.getString(R.styleable.BasicFontView_fontPath);
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
            if (FontTypeface.getFont(getContext(), fontPath) != null) {
                this.setTypeface(FontTypeface.getFont(getContext(), fontPath));
            }
        }
    }
}
