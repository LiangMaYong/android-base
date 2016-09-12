package com.liangmayong.base.widget.themeskin;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.liangmayong.base.utils.DimenUtils;
import com.liangmayong.base.widget.drawables.RadiusDrawable;

/**
 * Created by liangmayong on 2016/9/11.
 */
public class SkinButton extends Button implements OnSkinRefreshListener {

    private Drawable[] drawables = new Drawable[2];
    private int radius = 0;
    private int defualt_text_color = 0xffffffff;
    private int defualt_bg_nor_deta = 0x00111111;
    private int defualt_bg_pre_deta = 0x55111111;
    private Skin.SkinType skinType = Skin.SkinType.defualt;
    private boolean strokeEnabled = false;

    public SkinButton(Context context) {
        super(context);
        initView();
    }

    /**
     * setSkinType
     *
     * @param skinType skinType
     */
    public void setSkinType(Skin.SkinType skinType) {
        if (this.skinType != skinType) {
            this.skinType = skinType;
            onRefreshSkin(Skin.get());
        }
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
        radius = dip2px(getContext(), 5);
        drawables[0] = new RadiusDrawable(radius, true, Skin.get().getColor(skinType) - defualt_bg_nor_deta);
        drawables[1] = new RadiusDrawable(radius, true, Skin.get().getColor(skinType) - defualt_bg_pre_deta);
        super.setTextColor(defualt_text_color);
        setBackgroundDrawable(drawables[0]);
        Skin.registerSkinRefresh(this);
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

    /**
     * setRadius
     *
     * @param radiusPx radiusPx
     */
    public void setRadius(int radiusPx) {
        this.radius = radiusPx;
        onRefreshSkin(Skin.get());
    }

    /**
     * setRadiusDip
     *
     * @param radiusDip radiusDip
     */
    public void setRadiusDip(int radiusDip) {
        this.radius = DimenUtils.dip2px(getContext(), radiusDip);
        onRefreshSkin(Skin.get());
    }

    /**
     * setStrokeEnabled
     *
     * @param strokeEnabled strokeEnabled
     */
    public void setStrokeEnabled(boolean strokeEnabled) {
        this.strokeEnabled = strokeEnabled;
        onRefreshSkin(Skin.get());
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
        if (!strokeEnabled) {
            ((RadiusDrawable) drawables[0]).setColor(skin.getColor(skinType) - defualt_bg_nor_deta);
            ((RadiusDrawable) drawables[1]).setColor(skin.getColor(skinType) - defualt_bg_pre_deta);
            ((RadiusDrawable) drawables[0]).setStrokeWidth(0);
            ((RadiusDrawable) drawables[1]).setStrokeWidth(0);
            ((RadiusDrawable) drawables[0]).setRadius(radius);
            ((RadiusDrawable) drawables[1]).setRadius(radius);
            setTextColor(skin.getTextColor(skinType));
        } else {
            ((RadiusDrawable) drawables[0]).setColor(0x00ffffffff);
            ((RadiusDrawable) drawables[1]).setColor(0x00ffffffff);
            ((RadiusDrawable) drawables[0]).setStrokeColor(skin.getColor(skinType) - defualt_bg_nor_deta);
            ((RadiusDrawable) drawables[1]).setStrokeColor(skin.getColor(skinType) - defualt_bg_pre_deta);
            ((RadiusDrawable) drawables[0]).setStrokeWidth(DimenUtils.dip2px(getContext(), 2));
            ((RadiusDrawable) drawables[1]).setStrokeWidth(DimenUtils.dip2px(getContext(), 2));
            ((RadiusDrawable) drawables[0]).setRadius(radius);
            ((RadiusDrawable) drawables[1]).setRadius(radius);
            setTextColor(skin.getColor(skinType));
        }
        invalidate();
    }
}
