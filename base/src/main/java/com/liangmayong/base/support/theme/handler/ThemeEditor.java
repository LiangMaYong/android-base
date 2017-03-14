package com.liangmayong.base.support.theme.handler;

/**
 * Created by LiangMaYong on 2017/3/14.
 */
public class ThemeEditor {

    private static volatile ThemeEditor ourInstance = null;

    public static ThemeEditor get() {
        if (ourInstance == null) {
            synchronized (ThemeEditor.class) {
                ourInstance = new ThemeEditor();
            }
        }
        return ourInstance;
    }

    //property
    private ThemeProperty property;

    private ThemeEditor() {
        this.property = ThemeProperty.get();
    }

    /**
     * setThemeColor
     *
     * @param themeColor     themeColor
     * @param themeTextColor themeTextColor
     * @return editor
     */
    public ThemeEditor setThemeColor(int themeColor, int themeTextColor) {
        property.setThemeColor(themeColor, themeTextColor);
        return this;
    }

    /**
     * setPrimaryColor
     *
     * @param primaryColor     primaryColor
     * @param primaryTextColor primaryTextColor
     * @return editor
     */
    public ThemeEditor setPrimaryColor(int primaryColor, int primaryTextColor) {
        property.setPrimaryColor(primaryColor, primaryTextColor);
        return this;
    }

    /**
     * setSuccessColor
     *
     * @param successColor     successColor
     * @param successTextColor successTextColor
     * @return editor
     */
    public ThemeEditor setSuccessColor(int successColor, int successTextColor) {
        property.setSuccessColor(successColor, successTextColor);
        return this;
    }

    /**
     * setGrayColor
     *
     * @param grayColor     grayColor
     * @param grayTextColor grayTextColor
     * @return editor
     */
    public ThemeEditor setGrayColor(int grayColor, int grayTextColor) {
        property.setGrayColor(grayColor, grayTextColor);
        return this;
    }

    /**
     * setInfoColor
     *
     * @param infoColor     infoColor
     * @param infoTextColor infoTextColor
     * @return editor
     */
    public ThemeEditor setInfoColor(int infoColor, int infoTextColor) {
        property.setInfoColor(infoColor, infoTextColor);
        return this;
    }

    /**
     * setWarningColor
     *
     * @param warningColor     warningColor
     * @param warningTextColor warningTextColor
     * @return editor
     */
    public ThemeEditor setWarningColor(int warningColor, int warningTextColor) {
        property.setWarningColor(warningColor, warningTextColor);
        return this;
    }

    /**
     * setDangerColor
     *
     * @param dangerColor     dangerColor
     * @param dangerTextColor dangerTextColor
     * @return editor
     */
    public ThemeEditor setDangerColor(int dangerColor, int dangerTextColor) {
        property.setDangerColor(dangerColor, dangerTextColor);
        return this;
    }

    /**
     * setWhiteColor
     *
     * @param whiteColor     whiteColor
     * @param whiteTextColor whiteTextColor
     * @return editor
     */
    public ThemeEditor setWhiteColor(int whiteColor, int whiteTextColor) {
        property.setWhiteColor(whiteColor, whiteTextColor);
        return this;
    }

    /**
     * setBlackColor
     *
     * @param blackColor     blackColor
     * @param blackTextColor blackTextColor
     * @return editor
     */
    public ThemeEditor setBlackColor(int blackColor, int blackTextColor) {
        property.setBlackColor(blackColor, blackTextColor);
        return this;
    }

    /**
     * setColor
     *
     * @param key   key
     * @param value value
     */
    public ThemeEditor setExtraColor(String key, int value) {
        property.setColor(key, value);
        return this;
    }

    /**
     * setColor
     *
     * @param key   key
     * @param value value
     */
    public ThemeEditor setExtraString(String key, String value) {
        property.setExtra(key, value);
        return this;
    }

    /**
     * switchTheme
     *
     * @param themeDefaultClass themeDefaultClass
     */
    public ThemeEditor setTheme(Class<? extends ThemeDefault> themeDefaultClass) {
        property.setTheme(themeDefaultClass);
        return this;
    }

    /**
     * resetColorValue
     */
    public ThemeEditor reset() {
        property.reset();
        return this;
    }

    /**
     * commit
     */
    public void commit() {
        ThemeEvent.notifyTheme();
    }
}
