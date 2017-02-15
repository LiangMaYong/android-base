package com.liangmayong.base.widget.skinview;

import android.content.Context;
import android.util.AttributeSet;

import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.support.skin.handlers.SkinType;
import com.liangmayong.base.support.skin.listeners.OnSkinRefreshListener;
import com.liangmayong.base.widget.skinview.interfaces.SkinViewHandler;
import com.liangmayong.base.widget.skinview.interfaces.SkinViewHandlerInterface;
import com.liangmayong.base.widget.view.SegmentView;

/**
 * Created by LiangMaYong on 2016/9/27.
 */
public class SkinSegmentView extends SegmentView implements SkinViewHandlerInterface {

    private SkinViewHandler handler = null;

    public SkinSegmentView(Context context) {
        super(context);
        init(null);
    }

    public SkinSegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SkinSegmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        handler = new SkinViewHandler(this, new SkinViewHandler.OnSkinColorListener() {
            @Override
            public void onColor(int color, int textColor) {
                setSegmentColors(textColor, color);
            }
        });
        handler.initAttributeSet(attrs);
        if (isInEditMode()) {
            setSegmentColors(handler.getSkinTextColor(), handler.getSkinColor());
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
