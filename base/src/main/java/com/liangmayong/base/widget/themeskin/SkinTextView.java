package com.liangmayong.base.widget.themeskin;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

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


    private Skin.SkinType skinType = Skin.SkinType.defualt;

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

    @Override
    protected void onDetachedFromWindow() {
        Skin.unregisterSkinRefresh(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onRefreshSkin(Skin skin) {
        setTextColor(skin.getColor(skinType));
    }
}
