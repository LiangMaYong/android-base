package com.liangmayong.base.widget.skin;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

import com.liangmayong.base.R;

/**
 * Created by LiangMaYong on 2016/9/27.
 */
public class SkinEditText extends EditText implements OnSkinRefreshListener {


    private Skin.SkinType skinType = Skin.SkinType.defualt;

    public SkinEditText(Context context) {
        this(context, null);
    }


    public SkinEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBG(context, attrs);
    }


    public SkinEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBG(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SkinEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initBG(context, attrs);
    }


    protected void initBG(final Context context, final AttributeSet attrs) {
        int preview_color = -1;
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinStyleable);
            preview_color = typedArray.getColor(R.styleable.SkinStyleable_preview_color, preview_color);
            int skin = typedArray.getInt(R.styleable.SkinStyleable_skin_type, skinType.value());
            skinType = Skin.SkinType.valueOf(skin);
            typedArray.recycle();
        }
        if (isInEditMode()) {
            super.setHighlightColor(preview_color);
            if (preview_color == -1) {
                switch (skinType) {
                    case gray:
                        preview_color = 0xffb1b1b1;
                        break;
                    case primary:
                        preview_color = 0xff428bca;
                        break;
                    case success:
                        preview_color = 0xff5cb85c;
                        break;
                    case warning:
                        preview_color = 0xfff0ad4e;
                        break;
                    case info:
                        preview_color = 0xff5bc0de;
                        break;
                    case danger:
                        preview_color = 0xffd9534f;
                        break;
                    case white:
                        preview_color = 0xffffffff;
                        break;
                    case defualt:
                        preview_color = getContext().getResources().getColor(R.color.colorPrimary);
                        break;
                }
                super.setHighlightColor(preview_color);
            }
        }
    }

    /**
     * dip2px
     *
     * @param context context
     * @param dpValue dpValue
     * @return pxValue
     */
    protected int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) return;
        Skin.registerSkinRefresh(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isInEditMode()) return;
        Skin.unregisterSkinRefresh(this);
    }

    @Override
    public void onSkinRefresh(Skin skin) {
        super.setHighlightColor(skin.getColor(skinType));
        if (skinRefreshListener != null) {
            skinRefreshListener.onSkinRefresh(skin);
        }
    }

    // skinRefreshListener
    private OnSkinRefreshListener skinRefreshListener = null;

    public void setSkinRefreshListener(OnSkinRefreshListener skinRefreshListener) {
        this.skinRefreshListener = skinRefreshListener;
    }


    public void setSkinType(Skin.SkinType skinType) {
        this.skinType = skinType;
        invalidate();
    }

    public Skin.SkinType getSkinType() {
        return skinType;
    }
}
