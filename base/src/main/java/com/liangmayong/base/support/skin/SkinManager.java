package com.liangmayong.base.support.skin;

import com.liangmayong.base.support.database.DataPreferences;
import com.liangmayong.base.support.skin.handlers.SkinEditor;
import com.liangmayong.base.support.skin.handlers.SkinEvent;
import com.liangmayong.base.support.skin.handlers.SkinHandler;
import com.liangmayong.base.support.skin.handlers.SkinTheme;
import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.support.skin.interfaces.ISkinDefault;
import com.liangmayong.base.support.skin.interfaces.ISkinHandler;
import com.liangmayong.base.support.skin.listeners.OnSkinRefreshListener;
import com.liangmayong.base.support.skin.themes.SkinDefault;

/**
 * Created by LiangMaYong on 2016/9/11.
 */
public class SkinManager {

    /**
     * registerSkinRefresh
     *
     * @param refreshListener refreshListener
     */
    public static void registerSkinRefresh(OnSkinRefreshListener refreshListener) {
        SkinEvent.registerSkinRefresh(refreshListener);
    }

    /**
     * unregisterSkinRefresh
     *
     * @param refreshListener refreshListener
     */
    public static void unregisterSkinRefresh(OnSkinRefreshListener refreshListener) {
        SkinEvent.unregisterSkinRefresh(refreshListener);
    }

    /**
     * refreshSkin
     */
    public static void refreshSkin() {
        SkinEvent.refreshSkin();
    }

    //skinHandler
    private static ISkinHandler skinHandler = null;
    private static String theme_name = "default";
    private static String theme_default = SkinDefault.class.getName();

    /**
     * switchTheme
     *
     * @param theme        theme
     * @param defaultClazz defaultClazz
     */
    public static void switchTheme(String theme, Class<? extends ISkinDefault> defaultClazz) {
        DataPreferences.getPreferences("theme").setString("theme_name", theme);
        DataPreferences.getPreferences("theme").setString("theme_default", defaultClazz.getName());
        SkinManager.skinHandler = SkinHandler.get(theme, defaultClazz);
        if (skinHandler != null) {
            SkinEvent.refreshSkin();
            if ((theme_name.equals(theme) || theme_default.equals(defaultClazz.getName()))) {
                SkinEvent.refreshReceiver();
            }
        }
    }

    /**
     * editor
     *
     * @return editor
     */
    public static SkinEditor editor() {
        return new SkinEditor(getSkinHandler());
    }

    /**
     * get
     *
     * @return skin
     */
    public static ISkin get() {
        return new SkinTheme(getSkinHandler());
    }

    /**
     * getSkinHandler
     *
     * @return skinHandler
     */
    private static ISkinHandler getSkinHandler() {
        if (skinHandler == null) {
            theme_name = DataPreferences.getPreferences("theme").getString("theme_name", theme_name);
            theme_default = DataPreferences.getPreferences("theme").getString("theme_default", theme_default);
            try {
                return SkinHandler.get(theme_name, (Class<? extends ISkinDefault>) Class.forName(theme_default));
            } catch (Exception e) {
                return SkinHandler.get(theme_name, null);
            }
        }
        return skinHandler;
    }

    private SkinManager() {
    }
}
