package com.liangmayong.base.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

import com.liangmayong.base.R;
import com.liangmayong.base.utils.DimenUtils;
import com.liangmayong.base.widget.drawables.RadiusDrawable;
import com.liangmayong.base.widget.themeskin.OnSkinRefreshListener;
import com.liangmayong.base.widget.themeskin.Skin;

/**
 * Created by liangmayong on 2016/9/11.
 */
public class SkinTextView extends TextView implements OnSkinRefreshListener {

    public SkinTextView(Context context) {
        super(context);
        initView();
    }

    public SkinTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SkinTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SkinTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        Skin.registerSkinRefresh(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        Skin.unregisterSkinRefresh(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onRefreshSkin(Skin skin) {
        setTextColor(skin.getThemeColor());
    }
}
