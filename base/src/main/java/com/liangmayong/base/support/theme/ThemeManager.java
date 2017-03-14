package com.liangmayong.base.support.theme;

import com.liangmayong.base.support.theme.handler.ThemeEditor;
import com.liangmayong.base.support.theme.handler.ThemeEvent;
import com.liangmayong.base.support.theme.listener.OnThemeListener;

/**
 * Created by LiangMaYong on 2017/3/14.
 */

public class ThemeManager {

    /**
     * registerThemeListener
     *
     * @param themeListener themeListener
     */
    public static void registerThemeListener(OnThemeListener themeListener) {
        ThemeEvent.registerThemeListener(themeListener);
        ThemeEvent.notifyTheme(themeListener);
    }

    /**
     * unregisterThemeListener
     *
     * @param themeListener refreshListener
     */
    public static void unregisterThemeListener(OnThemeListener themeListener) {
        ThemeEvent.unregisterThemeListener(themeListener);
    }

    /**
     * notifyTheme
     *
     * @param themeListener themeListener
     */
    public static void notifyTheme(OnThemeListener themeListener) {
        ThemeEvent.notifyTheme(themeListener);
    }

    /**
     * getEditor
     *
     * @return theme editor
     */
    public static ThemeEditor getEditor() {
        return ThemeEditor.get();
    }

    /**
     * getTheme
     *
     * @return theme
     */
    public static Theme getTheme() {
        return Theme.get();
    }
}
