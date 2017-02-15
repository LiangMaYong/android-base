package com.liangmayong.base.support.skin.themes;

import android.os.Build;

import com.liangmayong.base.R;
import com.liangmayong.base.support.skin.interfaces.ISkinDefault;
import com.liangmayong.base.support.utils.ContextUtils;

/**
 * Created by LiangMaYong on 2016/12/9.
 */

public class DefaultTheme implements ISkinDefault {

    @Override
    public int getThemeColor() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return ContextUtils.getApplication().getColor(R.color.colorPrimary);
            } else {
                return ContextUtils.getApplication().getResources().getColor(R.color.colorPrimary);
            }
        } catch (Exception e) {
        }
        return getPrimaryColor();
    }

    @Override
    public int getThemeTextColor() {
        return 0xffffffff;
    }

    @Override
    public int getPrimaryColor() {
        return 0xff428bca;
    }

    @Override
    public int getPrimaryTextColor() {
        return 0xffffffff;
    }

    @Override
    public int getSuccessColor() {
        return 0xff5cb85c;
    }

    @Override
    public int getSuccessTextColor() {
        return 0xffffffff;
    }

    @Override
    public int getInfoColor() {
        return 0xff5bc0de;
    }

    @Override
    public int getInfoTextColor() {
        return 0xffffffff;
    }

    @Override
    public int getWarningColor() {
        return 0xfff0ad4e;
    }

    @Override
    public int getWarningTextColor() {
        return 0xffffffff;
    }

    @Override
    public int getDangerColor() {
        return 0xffd9534f;
    }

    @Override
    public int getDangerTextColor() {
        return 0xffffffff;
    }

    @Override
    public int getGrayColor() {
        return 0xffb1b1b1;
    }

    @Override
    public int getGrayTextColor() {
        return 0xffffffff;
    }

    @Override
    public int getWhiteColor() {
        return 0xffffffff;
    }

    @Override
    public int getWhiteTextColor() {
        return 0xff333333;
    }

    @Override
    public int getBlackColor() {
        return 0xff333333;
    }

    @Override
    public int getBlackTextColor() {
        return 0xffffffff;
    }
}
