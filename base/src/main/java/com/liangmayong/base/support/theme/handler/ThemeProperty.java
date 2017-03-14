package com.liangmayong.base.support.theme.handler;

import android.content.Context;
import android.util.Log;

import com.liangmayong.base.support.config.ConfigProperty;
import com.liangmayong.base.support.theme.ThemeType;
import com.liangmayong.base.support.theme.defaults.ThemeDay;
import com.liangmayong.base.support.utils.ContextUtils;

/**
 * Created by LiangMaYong on 2017/3/14.
 */
public class ThemeProperty {

    private static final String THEME_COLOR = "THEME_COLOR";
    private static final String THEME_TEXT_COLOR = "THEME_TEXT_COLOR";
    private static final String PRIMARY_COLOR = "PRIMARY_COLOR";
    private static final String PRIMARY_TEXT_COLOR = "PRIMARY_TEXT_COLOR";
    private static final String SUCCESS_COLOR = "SUCCESS_COLOR";
    private static final String SUCCESS_TEXT_COLOR = "SUCCESS_TEXT_COLOR";
    private static final String INFO_COLOR = "INFO_COLOR";
    private static final String INFO_TEXT_COLOR = "INFO_TEXT_COLOR";
    private static final String WARNING_COLOR = "WARNING_COLOR";
    private static final String WARNING_TEXT_COLOR = "WARNING_TEXT_COLOR";
    private static final String DANGER_COLOR = "DANGER_COLOR";
    private static final String DANGER_TEXT_COLOR = "DANGER_TEXT_COLOR";
    private static final String GRAY_COLOR = "GRAY_COLOR";
    private static final String GRAY_TEXT_COLOR = "GRAY_TEXT_COLOR";
    private static final String WHITE_COLOR = "WHITE_COLOR";
    private static final String WHITE_TEXT_COLOR = "WHITE_TEXT_COLOR";
    private static final String BLACK_COLOR = "BLACK_COLOR";
    private static final String BLACK_TEXT_COLOR = "BLACK_TEXT_COLOR";


    private static volatile ThemeProperty ourInstance = null;

    public static ThemeProperty get() {
        if (ourInstance == null) {
            synchronized (ThemeProperty.class) {
                ourInstance = new ThemeProperty();
            }
        }
        return ourInstance;
    }

    private ThemeProperty() {
    }

    public int getThemeColor() {
        return getColor(THEME_COLOR, getThemeDefault().getThemeColor());
    }

    public int getThemeTextColor() {
        return getColor(THEME_TEXT_COLOR, getThemeDefault().getThemeTextColor());
    }

    public int getPrimaryColor() {
        return getColor(PRIMARY_COLOR, getThemeDefault().getPrimaryColor());
    }

    public int getPrimaryTextColor() {
        return getColor(PRIMARY_TEXT_COLOR, getThemeDefault().getPrimaryTextColor());
    }

    public int getSuccessColor() {
        return getColor(SUCCESS_COLOR, getThemeDefault().getSuccessColor());
    }

    public int getSuccessTextColor() {
        return getColor(SUCCESS_TEXT_COLOR, getThemeDefault().getSuccessTextColor());
    }

    public int getInfoColor() {
        return getColor(INFO_COLOR, getThemeDefault().getInfoColor());
    }

    public int getInfoTextColor() {
        return getColor(INFO_TEXT_COLOR, getThemeDefault().getInfoTextColor());
    }

    public int getWarningColor() {
        return getColor(WARNING_COLOR, getThemeDefault().getWarningColor());
    }

    public int getWarningTextColor() {
        return getColor(WARNING_TEXT_COLOR, getThemeDefault().getWarningTextColor());
    }

    public int getDangerColor() {
        return getColor(DANGER_COLOR, getThemeDefault().getDangerColor());
    }

    public int getDangerTextColor() {
        return getColor(DANGER_TEXT_COLOR, getThemeDefault().getDangerTextColor());
    }

    public int getGrayColor() {
        return getColor(GRAY_COLOR, getThemeDefault().getGrayColor());
    }

    public int getGrayTextColor() {
        return getColor(GRAY_TEXT_COLOR, getThemeDefault().getGrayTextColor());
    }

    public int getWhiteColor() {
        return getColor(WHITE_COLOR, getThemeDefault().getWhiteColor());
    }

    public int getWhiteTextColor() {
        return getColor(WHITE_TEXT_COLOR, getThemeDefault().getWhiteTextColor());
    }

    public int getBlackColor() {
        return getColor(BLACK_COLOR, getThemeDefault().getBlackColor());
    }

    public int getBlackTextColor() {
        return getColor(BLACK_TEXT_COLOR, getThemeDefault().getBlackTextColor());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setThemeColor(int themeColor, int themeTextColor) {
        setColor(THEME_COLOR, themeColor);
        setColor(THEME_TEXT_COLOR, themeTextColor);
    }

    public void setPrimaryColor(int primaryColor, int primaryTextColor) {
        setColor(PRIMARY_COLOR, primaryColor);
        setColor(PRIMARY_TEXT_COLOR, primaryTextColor);
    }

    public void setGrayColor(int grayColor, int grayTextColor) {
        setColor(GRAY_COLOR, grayColor);
        setColor(GRAY_TEXT_COLOR, grayTextColor);
    }

    public void setWhiteColor(int whiteColor, int whiteTextColor) {
        setColor(WHITE_COLOR, whiteColor);
        setColor(WHITE_TEXT_COLOR, whiteTextColor);
    }

    public void setBlackColor(int blackColor, int blackTextColor) {
        setColor(BLACK_COLOR, blackColor);
        setColor(BLACK_TEXT_COLOR, blackTextColor);
    }

    public void setSuccessColor(int successColor, int successTextColor) {
        setColor(SUCCESS_COLOR, successColor);
        setColor(SUCCESS_TEXT_COLOR, successTextColor);
    }

    public void setInfoColor(int infoColor, int infoTextColor) {
        setColor(INFO_COLOR, infoColor);
        setColor(INFO_TEXT_COLOR, infoTextColor);
    }

    public void setWarningColor(int warningColor, int warningTextColor) {
        setColor(WARNING_COLOR, warningColor);
        setColor(WARNING_TEXT_COLOR, warningTextColor);
    }

    public void setDangerColor(int dangerColor, int dangerTextColor) {
        setColor(DANGER_COLOR, dangerColor);
        setColor(DANGER_TEXT_COLOR, dangerTextColor);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getColor(ThemeType themeType) {
        switch (themeType) {
            case primary:
                return getPrimaryColor();
            case success:
                return getSuccessColor();
            case info:
                return getInfoColor();
            case warning:
                return getWarningColor();
            case danger:
                return getDangerColor();
            case white:
                return getWhiteColor();
            case black:
                return getBlackColor();
            case gray:
                return getGrayColor();
            default:
                return getThemeColor();
        }
    }

    public int getTextColor(ThemeType themeType) {
        switch (themeType) {
            case primary:
                return getPrimaryTextColor();
            case success:
                return getSuccessTextColor();
            case info:
                return getInfoTextColor();
            case warning:
                return getWarningTextColor();
            case danger:
                return getDangerTextColor();
            case white:
                return getWhiteTextColor();
            case black:
                return getBlackTextColor();
            case gray:
                return getGrayTextColor();
            default:
                return getThemeTextColor();
        }
    }

    private String themeDefaultProperty = ThemeDay.class.getName();
    private ThemeDefault themeDefault;

    public String getThemeName() {
        return getThemeDefault().getThemeName();
    }

    public ThemeDefault getThemeDefault() {
        String themeDefaultClass = ConfigProperty.getInstance("theme_property").getProperty(getContext(), "theme_default", ThemeDefault.class.getName());
        if (!themeDefaultProperty.equals(themeDefaultClass) || themeDefault == null) {
            themeDefaultProperty = themeDefaultClass;
            try {
                themeDefault = (ThemeDefault) Class.forName(themeDefaultClass).newInstance();
            } catch (Exception e) {
                themeDefault = new ThemeDay();
            }
        }
        return themeDefault;
    }

    public void setTheme(Class<? extends ThemeDefault> themeDefaultClass) {
        ConfigProperty.getInstance("theme_property").setProperty(getContext(), "theme_default", themeDefaultClass.getName());
    }

    public void reset() {
        setThemeColor(getThemeDefault().getThemeColor(), getThemeDefault().getThemeTextColor());
        setWhiteColor(getThemeDefault().getWhiteColor(), getThemeDefault().getWhiteTextColor());
        setWarningColor(getThemeDefault().getWarningColor(), getThemeDefault().getWarningTextColor());
        setSuccessColor(getThemeDefault().getSuccessColor(), getThemeDefault().getSuccessTextColor());
        setDangerColor(getThemeDefault().getDangerColor(), getThemeDefault().getDangerTextColor());
        setInfoColor(getThemeDefault().getInfoColor(), getThemeDefault().getInfoTextColor());
        setGrayColor(getThemeDefault().getGrayColor(), getThemeDefault().getGrayTextColor());
        setPrimaryColor(getThemeDefault().getPrimaryColor(), getThemeDefault().getPrimaryTextColor());
        setBlackColor(getThemeDefault().getBlackColor(), getThemeDefault().getBlackTextColor());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setColor(String key, int value) {
        ConfigProperty.getInstance("theme_property_" + getThemeName()).setProperty(getContext(), key, value + "");
    }

    public void setExtra(String key, String value) {
        ConfigProperty.getInstance("theme_property_" + getThemeName()).setProperty(getContext(), key, value);
    }

    public int getColor(String key, int defaultValue) {
        String value = ConfigProperty.getInstance("theme_property_" + getThemeName()).getProperty(getContext(), key, defaultValue + "");
        int color = defaultValue;
        try {
            color = Integer.parseInt(value);
        } catch (Exception e) {
        }
        return color;
    }

    public String getExtra(String key) {
        return ConfigProperty.getInstance("theme_property_" + getThemeName()).getProperty(getContext(), key);
    }

    private Context getContext() {
        return ContextUtils.getApplication();
    }
}
