package com.liangmayong.base.widget.themeskin;

import com.liangmayong.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangMaYong on 2016/9/11.
 */
public class Skin {

    /**
     * SkinType
     */
    public static enum SkinType {
        defualt, primary, success, info, warning, danger, white;
    }

    private static final String SKIN_PREFERENCES_NAME = "android_base_skin_preferences";
    private static final String SKIN_AIRING_EVENT_NAME = "android_base_skin_airing";
    private static volatile Preferences preferences = null;
    private static volatile Skin skin = null;
    private static volatile Editor editor = null;
    private static final List<OnSkinRefreshListener> SKIN_REFRESH_LISTENERS = new ArrayList<OnSkinRefreshListener>();

    /**
     * registerSkinRefresh
     *
     * @param refreshListener refreshListener
     */
    public static void registerSkinRefresh(OnSkinRefreshListener refreshListener) {
        if (refreshListener != null && !SKIN_REFRESH_LISTENERS.contains(refreshListener)) {
            SKIN_REFRESH_LISTENERS.add(refreshListener);
            refreshListener.onRefreshSkin(get());
        }
    }

    /**
     * unregisterSkinRefresh
     *
     * @param refreshListener refreshListener
     */
    public static void unregisterSkinRefresh(OnSkinRefreshListener refreshListener) {
        if (refreshListener != null && SKIN_REFRESH_LISTENERS.contains(refreshListener)) {
            SKIN_REFRESH_LISTENERS.remove(refreshListener);
        }
    }

    /**
     * refreshSkin
     */
    public static void refreshSkin() {
        for (int i = 0; i < SKIN_REFRESH_LISTENERS.size(); i++) {
            SKIN_REFRESH_LISTENERS.get(i).onRefreshSkin(get());
        }
    }

    /**
     * isClassGeneric
     *
     * @param clazz clazz
     * @param name  name
     * @return true or false
     */
    private static boolean isClassGeneric(Class<?> clazz, String name) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            if (name.equals(clazz.getName())) {
                return true;
            }
            Class<?>[] classes = clazz.getInterfaces();
            for (int i = 0; i < classes.length; i++) {
                if (name.equals(classes[i].getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Preferences getSkinPreferences() {
        if (preferences == null) {
            synchronized (Skin.class) {
                preferences = Preferences.getPreferences(SKIN_PREFERENCES_NAME);
            }
        }
        return preferences;
    }


    /**
     * get
     *
     * @return skin
     */
    public static Skin get() {
        if (skin == null) {
            synchronized (Skin.class) {
                skin = new Skin();
            }
        }
        return skin;
    }

    /**
     * editor
     *
     * @return editor
     */
    public static Editor editor() {
        if (editor == null) {
            synchronized (Skin.class) {
                editor = new Editor();
            }
        }
        return editor;
    }


    private Skin() {
        reset();
        //defualt
        themeColor = getSkinPreferences().getInt("themeColor", themeColor);
        themeTextColor = getSkinPreferences().getInt("themeTextColor", themeTextColor);

        //colors
        primaryColor = getSkinPreferences().getInt("primaryColor", primaryColor);
        successColor = getSkinPreferences().getInt("successColor", successColor);
        infoColor = getSkinPreferences().getInt("infoColor", infoColor);
        warningColor = getSkinPreferences().getInt("warningColor", warningColor);
        dangerColor = getSkinPreferences().getInt("dangerColor", dangerColor);

        //text colors
        primaryTextColor = getSkinPreferences().getInt("primaryTextColor", primaryTextColor);
        successTextColor = getSkinPreferences().getInt("successTextColor", successTextColor);
        infoTextColor = getSkinPreferences().getInt("infoTextColor", infoTextColor);
        warningTextColor = getSkinPreferences().getInt("warningTextColor", warningTextColor);
        dangerTextColor = getSkinPreferences().getInt("dangerTextColor", dangerTextColor);
    }

    private int themeColor = 0;

    private int themeTextColor = 0;
    //primaryColor
    private int primaryColor = 0;
    //successColor
    private int successColor = 0;
    //infoColor
    private int infoColor = 0;
    //warningColor
    private int warningColor = 0;
    //dangerColor
    private int dangerColor = 0;

    //primaryColor
    private int primaryTextColor = 0;
    //successColor
    private int successTextColor = 0;
    //infoColor
    private int infoTextColor = 0;
    //warningColor
    private int warningTextColor = 0;
    //dangerColor
    private int dangerTextColor = 0;

    /**
     * getThemeColor
     *
     * @return themeColor
     */
    public int getThemeColor() {
        return themeColor;
    }

    /**
     * getTextColor
     *
     * @return textColor
     */
    public int getThemeTextColor() {
        return themeTextColor;
    }

    /**
     * reset
     */
    private void reset() {
        //themeColor
        themeColor = 0xff333333;
        //themeTextColor
        themeTextColor = 0xffffffff;
        //primaryColor
        primaryColor = 0xff428bca;
        //successColor
        successColor = 0xff5cb85c;
        //infoColor
        infoColor = 0xff5bc0de;
        //warningColor
        warningColor = 0xfff0ad4e;
        //dangerColor
        dangerColor = 0xffd9534f;

        //primaryColor
        primaryTextColor = 0xffffffff;
        //successColor
        successTextColor = 0xffffffff;
        //infoColor
        infoTextColor = 0xffffffff;
        //warningColor
        warningTextColor = 0xffffffff;
        //dangerColor
        dangerTextColor = 0xffffffff;
    }


    public int getPrimaryColor() {
        return primaryColor;
    }

    public int getPrimaryTextColor() {
        return primaryTextColor;
    }

    public int getSuccessColor() {
        return successColor;
    }

    public int getSuccessTextColor() {
        return successTextColor;
    }

    public int getInfoColor() {
        return infoColor;
    }

    public int getInfoTextColor() {
        return infoTextColor;
    }

    public int getWarningColor() {
        return warningColor;
    }

    public int getWarningTextColor() {
        return warningTextColor;
    }

    public int getDangerColor() {
        return dangerColor;
    }

    public int getDangerTextColor() {
        return dangerTextColor;
    }

    public int getColor(SkinType skinType) {
        if (hasColor(skinType)) {
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
                    return 0xffffffff;
                default:
                    return getThemeColor();
            }
        }
        return getThemeColor();
    }


    public int getTextColor(SkinType skinType) {
        if (hasColor(skinType)) {
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
                    return 0xff333333;
                default:
                    return getThemeTextColor();
            }
        }
        return getThemeTextColor();
    }

    /**
     * getExtraColor
     *
     * @param key key
     * @return value
     */
    public int getExtraColor(String key) {
        return getSkinPreferences().contains("color_" + key) ? getSkinPreferences().getInt("color_" + key, 0) : 0;
    }

    /**
     * getExtraString
     *
     * @param key key
     * @return value
     */
    public String getExtraString(String key) {
        return getSkinPreferences().contains("string_" + key) ? getSkinPreferences().getString("string_" + key) : "";
    }

    /**
     * setThemeColor
     *
     * @param themeColor     themeColor
     * @param themeTextColor themeTextColor
     */
    private void setThemeColor(int themeColor, int themeTextColor) {
        this.themeColor = themeColor;
        this.themeTextColor = themeTextColor;
    }

    /**
     * setPrimaryColor
     *
     * @param primaryColor     primaryColor
     * @param primaryTextColor primaryTextColor
     */
    private void setPrimaryColor(int primaryColor, int primaryTextColor) {
        this.primaryColor = primaryColor;
        this.primaryTextColor = primaryTextColor;
    }

    /**
     * setSuccessColor
     *
     * @param successColor     successColor
     * @param successTextColor successTextColor
     */
    private void setSuccessColor(int successColor, int successTextColor) {
        this.successColor = successColor;
        this.successTextColor = successTextColor;
    }

    /**
     * setInfoColor
     *
     * @param infoColor     infoColor
     * @param infoTextColor infoTextColor
     */
    private void setInfoColor(int infoColor, int infoTextColor) {
        this.infoColor = infoColor;
        this.infoTextColor = infoTextColor;
    }

    /**
     * setWarningColor
     *
     * @param warningColor     warningColor
     * @param warningTextColor warningTextColor
     */
    private void setWarningColor(int warningColor, int warningTextColor) {
        this.warningColor = warningColor;
        this.warningTextColor = warningTextColor;
    }

    /**
     * setDangerColor
     *
     * @param dangerColor     dangerColor
     * @param dangerTextColor dangerTextColor
     */
    private void setDangerColor(int dangerColor, int dangerTextColor) {
        this.dangerColor = dangerColor;
        this.dangerTextColor = dangerTextColor;
    }

    /**
     * hasThemeColor
     *
     * @return hasThemeColor
     */
    public boolean hasThemeColor() {
        if (getThemeColor() != 0) {
            return true;
        }
        return false;
    }

    /**
     * hasThemeColor
     *
     * @return hasThemeColor
     */
    public boolean hasColor(SkinType skinType) {
        switch (skinType) {
            case primary:
                return hasPrimaryColor();
            case success:
                return hasSuccessColor();
            case info:
                return hasInfoColor();
            case warning:
                return hasWarningColor();
            case danger:
                return hasDangerColor();
            case defualt:
                return hasThemeColor();
            case white:
                return true;
            default:
                return false;
        }
    }

    /**
     * hasThemeColor
     *
     * @return hasThemeColor
     */
    public boolean hasPrimaryColor() {
        if (getPrimaryColor() != 0) {
            return true;
        }
        return false;
    }

    /**
     * hasThemeColor
     *
     * @return hasThemeColor
     */
    public boolean hasWarningColor() {
        if (getWarningColor() != 0) {
            return true;
        }
        return false;
    }


    /**
     * hasInfoColor
     *
     * @return hasInfoColor
     */
    public boolean hasInfoColor() {
        if (getInfoColor() != 0) {
            return true;
        }
        return false;
    }

    /**
     * hasSuccessColor
     *
     * @return hasSuccessColor
     */
    public boolean hasSuccessColor() {
        if (getSuccessColor() != 0) {
            return true;
        }
        return false;
    }

    /**
     * hasDangerColor
     *
     * @return hasDangerColor
     */
    public boolean hasDangerColor() {
        if (getDangerColor() != 0) {
            return true;
        }
        return false;
    }

    /**
     * hasExtraColor
     *
     * @return hasExtraColor
     */
    public boolean hasExtraColor(String key) {
        if (getExtraColor(key) != 0) {
            return true;
        }
        return false;
    }

    /**
     * hasExtraString
     *
     * @return hasExtraString
     */
    public boolean hasExtraString(String key) {
        if (getExtraString(key) != null && !"".equals(getExtraString(key))) {
            return true;
        }
        return false;
    }

    /**
     * Editor
     */
    public static class Editor {

        private Editor() {
        }


        /**
         * setThemeColor
         *
         * @param themeColor     themeColor
         * @param themeTextColor themeTextColor
         * @return editor
         */
        public Editor setThemeColor(int themeColor, int themeTextColor) {
            Skin.get().setThemeColor(themeColor, themeTextColor);
            getSkinPreferences().setInt("themeColor", themeColor);
            getSkinPreferences().setInt("themeTextColor", themeTextColor);
            return this;
        }

        /**
         * setPrimaryColor
         *
         * @param primaryColor     primaryColor
         * @param primaryTextColor primaryTextColor
         * @return editor
         */
        public Editor setPrimaryColor(int primaryColor, int primaryTextColor) {
            Skin.get().setPrimaryColor(primaryColor, primaryTextColor);
            getSkinPreferences().setInt("primaryColor", primaryColor);
            getSkinPreferences().setInt("primaryTextColor", primaryTextColor);
            return this;
        }

        /**
         * setSuccessColor
         *
         * @param successColor     successColor
         * @param successTextColor successTextColor
         * @return editor
         */
        public Editor setSuccessColor(int successColor, int successTextColor) {
            Skin.get().setSuccessColor(successColor, successTextColor);
            getSkinPreferences().setInt("successColor", successColor);
            getSkinPreferences().setInt("successTextColor", successTextColor);
            return this;
        }


        /**
         * setInfoColor
         *
         * @param infoColor     infoColor
         * @param infoTextColor infoTextColor
         * @return editor
         */
        public Editor setInfoColor(int infoColor, int infoTextColor) {
            Skin.get().setInfoColor(infoColor, infoTextColor);
            getSkinPreferences().setInt("infoColor", infoColor);
            getSkinPreferences().setInt("infoTextColor", infoTextColor);
            return this;
        }

        /**
         * setWarningColor
         *
         * @param warningColor     warningColor
         * @param warningTextColor warningTextColor
         * @return editor
         */
        public Editor setWarningColor(int warningColor, int warningTextColor) {
            Skin.get().setWarningColor(warningColor, warningTextColor);
            getSkinPreferences().setInt("warningColor", warningColor);
            getSkinPreferences().setInt("warningTextColor", warningTextColor);
            return this;
        }

        /**
         * setDangerColor
         *
         * @param dangerColor     dangerColor
         * @param dangerTextColor dangerTextColor
         * @return editor
         */
        public Editor setDangerColor(int dangerColor, int dangerTextColor) {
            Skin.get().setDangerColor(dangerColor, dangerTextColor);
            getSkinPreferences().setInt("dangerColor", dangerColor);
            getSkinPreferences().setInt("dangerTextColor", dangerTextColor);
            return this;
        }

        /**
         * setExtraColor
         *
         * @param key   key
         * @param value value
         */
        public Editor setExtraColor(String key, int value) {
            getSkinPreferences().setInt("color_" + key, value);
            return this;
        }

        /**
         * setExtraColor
         *
         * @param key   key
         * @param value value
         */
        public Editor setExtraString(String key, String value) {
            getSkinPreferences().setString("string_" + key, value);
            return this;
        }

        /**
         * reset
         */
        public Editor reset() {
            Skin.get().reset();
            return this;
        }

        /**
         * commit
         */
        public void commit() {
            refreshSkin();
        }

    }
}
