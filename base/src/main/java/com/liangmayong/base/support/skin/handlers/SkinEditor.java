package com.liangmayong.base.support.skin.handlers;

import com.liangmayong.base.support.skin.interfaces.ISkinHandler;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public class SkinEditor {
    //handler
    private ISkinHandler handler;

    /**
     * SkinEditor
     *
     * @param handler handler
     */
    public SkinEditor(ISkinHandler handler) {
        this.handler = handler;
    }

    /**
     * setThemeColor
     *
     * @param themeColor     themeColor
     * @param themeTextColor themeTextColor
     * @return editor
     */
    public SkinEditor setThemeColor(int themeColor, int themeTextColor) {
        handler.setThemeColor(themeColor, themeTextColor);
        return this;
    }

    /**
     * setPrimaryColor
     *
     * @param primaryColor     primaryColor
     * @param primaryTextColor primaryTextColor
     * @return editor
     */
    public SkinEditor setPrimaryColor(int primaryColor, int primaryTextColor) {
        handler.setPrimaryColor(primaryColor, primaryTextColor);
        return this;
    }

    /**
     * setSuccessColor
     *
     * @param successColor     successColor
     * @param successTextColor successTextColor
     * @return editor
     */
    public SkinEditor setSuccessColor(int successColor, int successTextColor) {
        handler.setSuccessColor(successColor, successTextColor);
        return this;
    }

    /**
     * setGrayColor
     *
     * @param grayColor     grayColor
     * @param grayTextColor grayTextColor
     * @return editor
     */
    public SkinEditor setGrayColor(int grayColor, int grayTextColor) {
        handler.setGrayColor(grayColor, grayTextColor);
        return this;
    }

    /**
     * setInfoColor
     *
     * @param infoColor     infoColor
     * @param infoTextColor infoTextColor
     * @return editor
     */
    public SkinEditor setInfoColor(int infoColor, int infoTextColor) {
        handler.setInfoColor(infoColor, infoTextColor);
        return this;
    }

    /**
     * setWarningColor
     *
     * @param warningColor     warningColor
     * @param warningTextColor warningTextColor
     * @return editor
     */
    public SkinEditor setWarningColor(int warningColor, int warningTextColor) {
        handler.setWarningColor(warningColor, warningTextColor);
        return this;
    }

    /**
     * setDangerColor
     *
     * @param dangerColor     dangerColor
     * @param dangerTextColor dangerTextColor
     * @return editor
     */
    public SkinEditor setDangerColor(int dangerColor, int dangerTextColor) {
        handler.setDangerColor(dangerColor, dangerTextColor);
        return this;
    }

    /**
     * setWhiteColor
     *
     * @param whiteColor     whiteColor
     * @param whiteTextColor whiteTextColor
     * @return editor
     */
    public SkinEditor setWhiteColor(int whiteColor, int whiteTextColor) {
        handler.setWhiteColor(whiteColor, whiteTextColor);
        return this;
    }

    /**
     * setBlackColor
     *
     * @param blackColor     blackColor
     * @param blackTextColor blackTextColor
     * @return editor
     */
    public SkinEditor setBlackColor(int blackColor, int blackTextColor) {
        handler.setBlackColor(blackColor, blackTextColor);
        return this;
    }

    /**
     * setColor
     *
     * @param key   key
     * @param value value
     */
    public SkinEditor setExtraColor(String key, int value) {
        handler.setColor(key, value);
        return this;
    }

    /**
     * setColor
     *
     * @param key   key
     * @param value value
     */
    public SkinEditor setExtraString(String key, String value) {
        handler.setExtra(key, value);
        return this;
    }

    /**
     * resetColorValue
     */
    public SkinEditor reset() {
        handler.reset();
        return this;
    }

    /**
     * commit
     */
    public void commit() {
        SkinEvent.refreshSkin();
        SkinEvent.refreshReceiver(handler.getThemeName());
    }
}
