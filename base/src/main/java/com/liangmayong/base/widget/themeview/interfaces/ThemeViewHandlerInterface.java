package com.liangmayong.base.widget.themeview.interfaces;

import com.liangmayong.base.support.theme.ThemeType;
import com.liangmayong.base.support.theme.listener.OnThemeListener;

/**
 * Created by LiangMaYong on 2017/2/14.
 */
public interface ThemeViewHandlerInterface {

    void setThemeListener(OnThemeListener themeListener);

    void setThemeType(ThemeType themeType);

    void setThemeColor(int skinColor, int skinTextColor);

    int getThemeColor();

    int getThemeTextColor();

    ThemeType getThemeType();
}
