package com.liangmayong.base.widget.skinview.interfaces;

import com.liangmayong.base.support.skin.handlers.SkinType;
import com.liangmayong.base.support.skin.listeners.OnSkinRefreshListener;

/**
 * Created by LiangMaYong on 2017/2/14.
 */
public interface ISkinViewHandler {

    void setSkinRefreshListener(OnSkinRefreshListener skinRefreshListener);

    void setSkinType(SkinType skinType);

    SkinType getSkinType();

    void setSkinColor(int skinColor, int skinTextColor);

    int getSkinColor();

    int getSkinTextColor();
}
