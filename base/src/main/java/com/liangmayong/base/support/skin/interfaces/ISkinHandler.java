package com.liangmayong.base.support.skin.interfaces;

import com.liangmayong.base.support.skin.handlers.SkinType;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public interface ISkinHandler {

    String THEME_COLOR = "THEME_COLOR";
    String THEME_TEXT_COLOR = "THEME_TEXT_COLOR";
    String PRIMARY_COLOR = "PRIMARY_COLOR";
    String PRIMARY_TEXT_COLOR = "PRIMARY_TEXT_COLOR";
    String SUCCESS_COLOR = "SUCCESS_COLOR";
    String SUCCESS_TEXT_COLOR = "SUCCESS_TEXT_COLOR";
    String INFO_COLOR = "INFO_COLOR";
    String INFO_TEXT_COLOR = "INFO_TEXT_COLOR";
    String WARNING_COLOR = "WARNING_COLOR";
    String WARNING_TEXT_COLOR = "WARNING_TEXT_COLOR";
    String DANGER_COLOR = "DANGER_COLOR";
    String DANGER_TEXT_COLOR = "DANGER_TEXT_COLOR";
    String GRAY_COLOR = "GRAY_COLOR";
    String GRAY_TEXT_COLOR = "GRAY_TEXT_COLOR";
    String WHITE_COLOR = "WHITE_COLOR";
    String WHITE_TEXT_COLOR = "WHITE_TEXT_COLOR";
    String BLACK_COLOR = "BLACK_COLOR";
    String BLACK_TEXT_COLOR = "BLACK_TEXT_COLOR";

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
