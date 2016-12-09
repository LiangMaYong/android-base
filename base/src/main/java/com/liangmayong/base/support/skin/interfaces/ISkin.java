package com.liangmayong.base.support.skin.interfaces;

import com.liangmayong.base.support.skin.handlers.SkinType;

/**
 * Created by LiangMaYong on 2016/12/9.
 */

public interface ISkin {

    int getThemeColor();

    int getThemeTextColor();

    int getPrimaryColor();

    int getPrimaryTextColor();

    int getSuccessColor();

    int getSuccessTextColor();

    int getInfoColor();

    int getInfoTextColor();

    int getWarningColor();

    int getWarningTextColor();

    int getDangerColor();

    int getDangerTextColor();

    int getGrayColor();

    int getGrayTextColor();

    int getWhiteColor();

    int getWhiteTextColor();

    int getBlackColor();

    int getBlackTextColor();

    int getColor(SkinType skinType);

    int getTextColor(SkinType skinType);

    String getThemeName();
}
