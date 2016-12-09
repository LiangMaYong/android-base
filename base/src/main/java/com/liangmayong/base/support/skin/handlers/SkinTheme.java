package com.liangmayong.base.support.skin.handlers;

import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.support.skin.interfaces.ISkinHandler;

/**
 * Created by LiangMaYong on 2016/12/9.
 */

public class SkinTheme implements ISkin {

    private final ISkinHandler handler;

    public SkinTheme(ISkinHandler handler) {
        this.handler = handler;
    }

    @Override
    public int getThemeColor() {
        return handler.getThemeColor();
    }

    @Override
    public int getThemeTextColor() {
        return handler.getThemeTextColor();
    }

    @Override
    public int getPrimaryColor() {
        return handler.getPrimaryColor();
    }

    @Override
    public int getPrimaryTextColor() {
        return handler.getPrimaryTextColor();
    }

    @Override
    public int getSuccessColor() {
        return handler.getSuccessColor();
    }

    @Override
    public int getSuccessTextColor() {
        return handler.getSuccessTextColor();
    }

    @Override
    public int getInfoColor() {
        return handler.getInfoColor();
    }

    @Override
    public int getInfoTextColor() {
        return handler.getInfoTextColor();
    }

    @Override
    public int getWarningColor() {
        return handler.getWarningColor();
    }

    @Override
    public int getWarningTextColor() {
        return handler.getWarningTextColor();
    }

    @Override
    public int getDangerColor() {
        return handler.getDangerColor();
    }

    @Override
    public int getDangerTextColor() {
        return handler.getDangerTextColor();
    }

    @Override
    public int getGrayColor() {
        return handler.getGrayColor();
    }

    @Override
    public int getGrayTextColor() {
        return handler.getGrayTextColor();
    }

    @Override
    public int getWhiteColor() {
        return handler.getWhiteColor();
    }

    @Override
    public int getWhiteTextColor() {
        return handler.getWhiteTextColor();
    }

    @Override
    public int getBlackColor() {
        return handler.getBlackColor();
    }

    @Override
    public int getBlackTextColor() {
        return handler.getBlackTextColor();
    }

    @Override
    public int getColor(SkinType skinType) {
        return handler.getColor(skinType);
    }

    @Override
    public int getTextColor(SkinType skinType) {
        return handler.getTextColor(skinType);
    }

    @Override
    public String getThemeName() {
        return handler.getThemeName();
    }
}
