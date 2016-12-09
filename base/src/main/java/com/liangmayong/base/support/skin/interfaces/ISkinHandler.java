package com.liangmayong.base.support.skin.interfaces;

import com.liangmayong.base.support.skin.handlers.SkinType;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public interface ISkinHandler {

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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void reset();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void setThemeColor(int themeColor, int themeTextColor);

    void setPrimaryColor(int primaryColor, int primaryTextColor);

    void setGrayColor(int grayColor, int grayTextColor);

    void setSuccessColor(int successColor, int successTextColor);

    void setInfoColor(int infoColor, int infoTextColor);

    void setWarningColor(int warningColor, int warningTextColor);

    void setDangerColor(int dangerColor, int dangerTextColor);

    void setWhiteColor(int whiteColor, int whiteTextColor);

    void setBlackColor(int blackColor, int blackTextColor);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void setColor(String key, int value);

    void setExtra(String key, String value);

    int getColor(String key, int defaultValue);

    String getExtra(String key);
}
