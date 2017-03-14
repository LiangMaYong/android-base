package com.liangmayong.base.support.theme.defaults;

import android.os.Build;

import com.liangmayong.base.R;
import com.liangmayong.base.support.theme.handler.ThemeDefault;
import com.liangmayong.base.support.utils.ContextUtils;

/**
 * Created by LiangMaYong on 2017/3/14.
 */

public class ThemeDay implements ThemeDefault {

    public int getThemeColor() {
        return getResColor(R.color.colorPrimary);
    }

    public int getThemeTextColor() {
        return 0xffffffff;
    }

    public int getPrimaryColor() {
        return 0xff428bca;
    }

    public int getPrimaryTextColor() {
        return 0xffffffff;
    }

    public int getSuccessColor() {
        return 0xff5cb85c;
    }

    public int getSuccessTextColor() {
        return 0xffffffff;
    }

    public int getInfoColor() {
        return 0xff5bc0de;
    }

    public int getInfoTextColor() {
        return 0xffffffff;
    }

    public int getWarningColor() {
        return 0xfff0ad4e;
    }

    public int getWarningTextColor() {
        return 0xffffffff;
    }

    public int getDangerColor() {
        return 0xffd9534f;
    }

    public int getDangerTextColor() {
        return 0xffffffff;
    }

    public int getGrayColor() {
        return 0xffb1b1b1;
    }

    public int getGrayTextColor() {
        return 0xffffffff;
    }

    public int getWhiteColor() {
        return 0xffffffff;
    }

    public int getWhiteTextColor() {
        return 0xff333333;
    }

    public int getBlackColor() {
        return 0xff333333;
    }

    public int getBlackTextColor() {
        return 0xffffffff;
    }

    public String getThemeName() {
        return "Day";
    }

    private int getResColor(int resId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return ContextUtils.getApplication().getColor(resId);
            } else {
                return ContextUtils.getApplication().getResources().getColor(resId);
            }
        } catch (Exception e) {
        }
        return 0xff428bca;
    }
}
