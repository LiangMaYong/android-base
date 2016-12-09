package com.liangmayong.base.support.skin.handlers;

import com.liangmayong.base.support.database.DataPreferences;
import com.liangmayong.base.support.skin.interfaces.ISkinDefault;
import com.liangmayong.base.support.skin.interfaces.ISkinHandler;

/**
 * Created by LiangMaYong on 2016/12/9.
 */

public class SkinHandler implements ISkinHandler {

    private static volatile SkinHandler ourInstance = null;

    public static SkinHandler getDefault() {
        if (ourInstance == null) {
            synchronized (SkinHandler.class) {
                ourInstance = new SkinHandler("default_handler", new SkinDefault());
            }
        }
        return ourInstance;
    }

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

    private final String themeName;
    private final ISkinDefault default_color;

    public SkinHandler(String themeName, ISkinDefault default_color) {
        this.themeName = themeName;
        this.default_color = default_color;
    }

    @Override
    public int getThemeColor() {
        return getColor(THEME_COLOR, default_color.getThemeColor());
    }

    @Override
    public int getThemeTextColor() {
        return getColor(THEME_TEXT_COLOR, default_color.getThemeTextColor());
    }

    @Override
    public int getPrimaryColor() {
        return getColor(PRIMARY_COLOR, default_color.getPrimaryColor());
    }

    @Override
    public int getPrimaryTextColor() {
        return getColor(PRIMARY_TEXT_COLOR, default_color.getPrimaryTextColor());
    }

    @Override
    public int getSuccessColor() {
        return getColor(SUCCESS_COLOR, default_color.getSuccessColor());
    }

    @Override
    public int getSuccessTextColor() {
        return getColor(SUCCESS_TEXT_COLOR, default_color.getSuccessTextColor());
    }

    @Override
    public int getInfoColor() {
        return getColor(INFO_COLOR, default_color.getInfoColor());
    }

    @Override
    public int getInfoTextColor() {
        return getColor(INFO_TEXT_COLOR, default_color.getInfoTextColor());
    }

    @Override
    public int getWarningColor() {
        return getColor(WARNING_COLOR, default_color.getWarningColor());
    }

    @Override
    public int getWarningTextColor() {
        return getColor(WARNING_TEXT_COLOR, default_color.getWarningTextColor());
    }

    @Override
    public int getDangerColor() {
        return getColor(DANGER_COLOR, default_color.getDangerColor());
    }

    @Override
    public int getDangerTextColor() {
        return getColor(DANGER_TEXT_COLOR, default_color.getDangerTextColor());
    }

    @Override
    public int getGrayColor() {
        return getColor(GRAY_COLOR, default_color.getGrayColor());
    }

    @Override
    public int getGrayTextColor() {
        return getColor(GRAY_TEXT_COLOR, default_color.getGrayTextColor());
    }

    @Override
    public int getWhiteColor() {
        return getColor(WHITE_COLOR, default_color.getWhiteColor());
    }

    @Override
    public int getWhiteTextColor() {
        return getColor(WHITE_TEXT_COLOR, default_color.getWhiteTextColor());
    }

    @Override
    public int getBlackColor() {
        return getColor(BLACK_COLOR, default_color.getBlackColor());
    }

    @Override
    public int getBlackTextColor() {
        return getColor(BLACK_TEXT_COLOR, default_color.getBlackTextColor());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void setThemeColor(int themeColor, int themeTextColor) {
        setColor(THEME_COLOR, themeColor);
        setColor(THEME_TEXT_COLOR, themeTextColor);
    }

    @Override
    public void setPrimaryColor(int primaryColor, int primaryTextColor) {
        setColor(PRIMARY_COLOR, primaryColor);
        setColor(PRIMARY_TEXT_COLOR, primaryTextColor);
    }

    @Override
    public void setGrayColor(int grayColor, int grayTextColor) {
        setColor(GRAY_COLOR, grayColor);
        setColor(GRAY_TEXT_COLOR, grayTextColor);
    }

    @Override
    public void setWhiteColor(int whiteColor, int whiteTextColor) {
        setColor(WHITE_COLOR, whiteColor);
        setColor(WHITE_TEXT_COLOR, whiteTextColor);
    }

    @Override
    public void setBlackColor(int blackColor, int blackTextColor) {
        setColor(BLACK_COLOR, blackColor);
        setColor(BLACK_TEXT_COLOR, blackTextColor);
    }

    @Override
    public void setSuccessColor(int successColor, int successTextColor) {
        setColor(SUCCESS_COLOR, successColor);
        setColor(SUCCESS_TEXT_COLOR, successTextColor);
    }

    @Override
    public void setInfoColor(int infoColor, int infoTextColor) {
        setColor(INFO_COLOR, infoColor);
        setColor(INFO_TEXT_COLOR, infoTextColor);
    }

    @Override
    public void setWarningColor(int warningColor, int warningTextColor) {
        setColor(WARNING_COLOR, warningColor);
        setColor(WARNING_TEXT_COLOR, warningTextColor);
    }

    @Override
    public void setDangerColor(int dangerColor, int dangerTextColor) {
        setColor(DANGER_COLOR, dangerColor);
        setColor(DANGER_TEXT_COLOR, dangerTextColor);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public int getColor(SkinType skinType) {
        switch (skinType) {
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

    @Override
    public int getTextColor(SkinType skinType) {
        switch (skinType) {
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

    @Override
    public String getThemeName() {
        return themeName;
    }

    @Override
    public void reset() {
        setBlackColor(default_color.getBlackColor(), default_color.getBlackTextColor());
        setThemeColor(default_color.getThemeColor(), default_color.getThemeTextColor());
        setWhiteColor(default_color.getWhiteColor(), default_color.getWhiteTextColor());
        setWarningColor(default_color.getWarningColor(), default_color.getWarningTextColor());
        setSuccessColor(default_color.getSuccessColor(), default_color.getSuccessTextColor());
        setDangerColor(default_color.getDangerColor(), default_color.getDangerTextColor());
        setInfoColor(default_color.getInfoColor(), default_color.getInfoTextColor());
        setGrayColor(default_color.getGrayColor(), default_color.getGrayTextColor());
        setPrimaryColor(default_color.getPrimaryColor(), default_color.getPrimaryTextColor());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void setColor(String key, int value) {
        DataPreferences.getPreferences("skin_" + getThemeName()).setInt(key, value);
    }

    @Override
    public void setExtra(String key, String value) {
        DataPreferences.getPreferences("skin_" + getThemeName()).setString(key, value);
    }

    @Override
    public int getColor(String key, int defaultValue) {
        return DataPreferences.getPreferences("skin_" + getThemeName()).getInt(key, defaultValue);
    }

    @Override
    public String getExtra(String key) {
        return DataPreferences.getPreferences("skin_" + getThemeName()).getString(key, "");
    }
}
