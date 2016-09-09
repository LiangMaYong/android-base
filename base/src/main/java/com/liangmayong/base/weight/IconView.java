package com.liangmayong.base.weight;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.liangmayong.base.weight.iconfont.IconFont;
import com.liangmayong.base.weight.iconfont.IconValue;


/**
 * IconView
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class IconView extends TextView {

    public static int ICON_LEFT = 0;
    public static int ICON_RIGHT = 1;
    private IconValue icon;
    private String text;
    private boolean isSet = false;
    private boolean isAnim = false;
    private String fontPath = "";
    private int align = 0;
    private float icon_size = 0;
    private boolean is_set_icon_size = false;

    public IconView(Context context) {
        super(context);
        init(null);
    }

    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public IconView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    /**
     * init view
     *
     * @param attrs attrs set
     */
    private void init(AttributeSet attrs) {
        text = super.getText().toString();
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);
    }

    public void setIconSize(float size) {
        is_set_icon_size = true;
        icon_size = size;
        setText(text);
    }

    public float getIconSize() {
        return icon_size;
    }

    private void setIconTypeface(String fontPath) {
        if (!this.fontPath.equals(fontPath)) {
            this.fontPath = fontPath;
        } else {
            return;
        }
        if (!isInEditMode()) {
            if (IconFont.getFont(getContext(), fontPath) != null) {
                this.setTypeface(IconFont.getFont(getContext(), fontPath));
            }
        }
    }

    @Override
    public CharSequence getText() {
        return this.text;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!isSet) {
            this.text = text + "";
        }

        String string = "";
        String iconValue = "";
        if (icon != null) {
            iconValue = Character.toString((char) icon.getUnicodeValue());
        }
        if (align == ICON_LEFT) {
            string += iconValue;
        }
        if (!"".equals(this.text) && this.text != null) {
            string += this.text;
        }
        if (align == ICON_RIGHT) {
            string += iconValue;
        }
        if (is_set_icon_size) {
            Spannable WordtoSpan = new SpannableString(string);
            if (align == ICON_LEFT) {
                WordtoSpan.setSpan(new AbsoluteSizeSpan(dip2px(getContext(), getIconSize())), 0, iconValue.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (align == ICON_RIGHT) {
                WordtoSpan.setSpan(new AbsoluteSizeSpan(dip2px(getContext(), getIconSize())), this.text.length(), string.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            super.setText(WordtoSpan, type);
        } else {
            super.setText(string, type);
        }
    }

    /**
     * dip2px
     *
     * @param context context
     * @param dpValue dpValue
     * @return pxValue
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * set icon
     *
     * @param text  button text
     * @param icon  icon
     * @param align align
     */
    public void setIcon(CharSequence text, IconValue icon, int align) {
        this.align = align;
        this.icon = icon;
        if (icon != null) {
            setIconTypeface(icon.getFontPath());
        }
        this.text = text + "";
        isSet = true;
        this.setText(text);
        isSet = false;
    }

    /**
     * start animation
     *
     * @param animRes
     */
    public void startAnimation(int animRes) {
        if (!isAnim) {
            isAnim = true;
            this.startAnimation(AnimationUtils.loadAnimation(getContext(), animRes));
        }
    }

    /**
     * stop animation
     */
    public void stopAnimation() {
        this.clearAnimation();
        isAnim = false;
    }

    /**
     * set textColor
     *
     * @param color   color
     * @param pressed pressed
     */
    public void setTextColor(int color, int pressed) {
        int statePressed = android.R.attr.state_pressed;
        int stateFocesed = android.R.attr.state_focused;
        int[][] state = {{statePressed}, {-statePressed}, {stateFocesed}, {-stateFocesed}};
        ColorStateList colors = new ColorStateList(state, new int[]{pressed, color, pressed, color});
        setTextColor(colors);
    }
}
