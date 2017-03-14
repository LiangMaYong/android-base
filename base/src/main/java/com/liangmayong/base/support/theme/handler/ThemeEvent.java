package com.liangmayong.base.support.theme.handler;

import com.liangmayong.base.support.theme.ThemeManager;
import com.liangmayong.base.support.theme.listener.OnThemeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangMaYong on 2017/3/14.
 */

public class ThemeEvent {

    private static final List<OnThemeListener> THEME_LISTENERS = new ArrayList<OnThemeListener>();

    /**
     * registerThemeListener
     *
     * @param themeListener refreshListener
     */
    public static void registerThemeListener(OnThemeListener themeListener) {
        if (themeListener != null && !THEME_LISTENERS.contains(themeListener)) {
            THEME_LISTENERS.add(themeListener);
        }
    }

    /**
     * unregisterThemeListener
     *
     * @param themeListener refreshListener
     */
    public static void unregisterThemeListener(OnThemeListener themeListener) {
        if (themeListener != null && THEME_LISTENERS.contains(themeListener)) {
            THEME_LISTENERS.remove(themeListener);
        }
    }

    /**
     * notifyTheme
     *
     * @param themeListener themeListener
     */
    public static void notifyTheme(OnThemeListener themeListener) {
        if (themeListener != null) {
            themeListener.onThemeEdited(ThemeManager.getTheme());
        }
    }

    /**
     * notifyTheme
     */
    public static void notifyTheme() {
        for (int i = 0; i < THEME_LISTENERS.size(); i++) {
            THEME_LISTENERS.get(i).onThemeEdited(ThemeManager.getTheme());
        }
    }
}
