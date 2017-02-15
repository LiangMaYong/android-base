package com.liangmayong.base.widget.skinview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.support.skin.handlers.SkinType;
import com.liangmayong.base.support.skin.listeners.OnSkinRefreshListener;
import com.liangmayong.base.widget.skinview.interfaces.SkinViewHandler;
import com.liangmayong.base.widget.skinview.interfaces.SkinViewHandlerInterface;

/**
 * Created by LiangMaYong on 2017/2/14.
 */
public class SkinView extends View implements SkinViewHandlerInterface {

    private SkinViewHandler handler = null;

    public SkinView(Context context) {
        super(context);
        init(null);
    }

    public SkinView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SkinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SkinView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        handler = new SkinViewHandler(this, new SkinViewHandler.OnSkinColorListener() {
            @Override
            public void onColor(int color, int textColor) {
                setBackgroundColor(color);
            }
        });
        handler.initAttributeSet(attrs);
        if (isInEditMode()) {
            setBackgroundColor(handler.getSkinColor());
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) return;
        SkinManager.registerSkinRefresh(handler);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isInEditMode()) return;
        SkinManager.unregisterSkinRefresh(handler);
    }

    /**
     * setSkinRefreshListener
     *
     * @param skinRefreshListener skinRefreshListener
     */
    public void setSkinRefreshListener(OnSkinRefreshListener skinRefreshListener) {
        handler.setSkinRefreshListener(skinRefreshListener);
    }

    /**
     * setSkinType
     *
     * @param skinType skinType
     */
    public void setSkinType(SkinType skinType) {
        handler.setSkinType(skinType);
    }

    @Override
    public SkinType getSkinType() {
        return handler.getSkinType();
    }

    @Override
    public void setSkinColor(int skinColor, int skinTextColor) {
        handler.setSkinColor(skinColor, skinTextColor);
    }

    @Override
    public int getSkinColor() {
        return handler.getSkinColor();
    }

    @Override
    public int getSkinTextColor() {
        return handler.getSkinTextColor();
    }
}
