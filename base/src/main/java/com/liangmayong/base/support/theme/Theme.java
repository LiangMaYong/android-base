package com.liangmayong.base.support.theme;

import com.liangmayong.base.support.theme.handler.ThemeProperty;

/**
 * Created by LiangMaYong on 2017/3/14.
 */
public class Theme {


    private static volatile Theme ourInstance = null;

    public static Theme get() {
        if (ourInstance == null) {
            synchronized (Theme.class) {
                ourInstance = new Theme();
            }
        }
        return ourInstance;
    }

    private ThemeProperty property;

    private Theme() {
        this.property = ThemeProperty.get();
    }

    public int getThemeColor() {
        return property.getThemeColor();
    }

    public int getThemeTextColor() {
        return property.getThemeTextColor();
    }

    public int getPrimaryColor() {
        return property.getPrimaryColor();
    }

    public int getPrimaryTextColor() {
        return property.getPrimaryTextColor();
    }

    public int getSuccessColor() {
        return property.getSuccessColor();
    }

    public int getSuccessTextColor() {
        return property.getSuccessTextColor();
    }

    public int getInfoColor() {
        return property.getInfoColor();
    }

    public int getInfoTextColor() {
        return property.getInfoTextColor();
    }

    public int getWarningColor() {
        return property.getWarningColor();
    }

    public int getWarningTextColor() {
        return property.getWarningTextColor();
    }

    public int getDangerColor() {
        return property.getDangerColor();
    }

    public int getDangerTextColor() {
        return property.getDangerTextColor();
    }

    public int getGrayColor() {
        return property.getGrayColor();
    }

    public int getGrayTextColor() {
        return property.getGrayTextColor();
    }

    public int getWhiteColor() {
        return property.getWhiteColor();
    }

    public int getWhiteTextColor() {
        return property.getWhiteTextColor();
    }

    public int getBlackColor() {
        return property.getBlackColor();
    }

    public int getBlackTextColor() {
        return property.getBlackTextColor();
    }

    public int getColor(ThemeType themeType) {
        return property.getColor(themeType);
    }

    public int getTextColor(ThemeType themeType) {
        return property.getTextColor(themeType);
    }

    public String getThemeName() {
        return property.getThemeName();
    }
}
