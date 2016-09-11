package com.liangmayong.base.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.liangmayong.base.R;
import com.liangmayong.base.bind.annotations.OnClick;
import com.liangmayong.base.utils.DimenUtils;
import com.liangmayong.base.widget.drawables.RadiusDrawable;
import com.liangmayong.base.widget.themeskin.OnSkinRefreshListener;
import com.liangmayong.base.widget.themeskin.Skin;

/**
 * Created by liangmayong on 2016/9/11.
 */
public class SkinButton extends Button implements OnSkinRefreshListener {

    private Drawable[] drawables = new Drawable[2];
    private int defualt_radius = 0;
    private int defualt_stroke_color = 0x05eeeeee;
    private int defualt_text_color = 0xffffffff;
    private int defualt_bg_nor_deta = 0x00111111;
    private int defualt_bg_pre_deta = 0x55111111;

    public SkinButton(Context context) {
        super(context);
        initView();
    }

    public SkinButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SkinButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SkinButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        defualt_radius = DimenUtils.dip2px(getContext(), 5);
        drawables[0] = new RadiusDrawable(defualt_radius, defualt_radius, defualt_radius, defualt_radius, false, getContext().getResources().getColor(R.color.colorPrimary) - defualt_bg_nor_deta);
        drawables[1] = new RadiusDrawable(defualt_radius, defualt_radius, defualt_radius, defualt_radius, false, getContext().getResources().getColor(R.color.colorPrimary) - defualt_bg_pre_deta);
        ((RadiusDrawable) drawables[0]).setStrokeColor(defualt_stroke_color);
        ((RadiusDrawable) drawables[0]).setStrokeWidth(DimenUtils.dip2px(getContext(), 2));
        ((RadiusDrawable) drawables[1]).setStrokeColor(defualt_stroke_color);
        ((RadiusDrawable) drawables[1]).setStrokeWidth(DimenUtils.dip2px(getContext(), 2));
        if (drawables[0] != null) {
            setBackgroundDrawable(drawables[0]);
        }
        super.setTextColor(defualt_text_color);
        Skin.registerSkinRefresh(this);
    }

    /**
     * setTextColor
     *
     * @param textColor
     */
    @Override
    public void setTextColor(int textColor) {
        this.defualt_text_color = textColor;
        super.setTextColor(textColor);
    }

    @Override
    protected void onDetachedFromWindow() {
        Skin.unregisterSkinRefresh(this);
        super.onDetachedFromWindow();
    }

    public void setRadius(int radius) {
        this.defualt_radius = radius;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (drawables[1] != null) {
                    setBackgroundDrawable(drawables[1]);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (drawables[0] != null) {
                    setBackgroundDrawable(drawables[0]);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onRefreshSkin(Skin skin) {
        ((RadiusDrawable) drawables[0]).setColor(skin.getThemeColor() - defualt_bg_nor_deta);
        ((RadiusDrawable) drawables[1]).setColor(skin.getThemeColor() - defualt_bg_pre_deta);
        invalidate();
    }
}
