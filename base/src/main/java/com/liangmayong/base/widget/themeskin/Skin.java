package com.liangmayong.base.widget.themeskin;

import com.liangmayong.airing.Airing;
import com.liangmayong.airing.AiringContent;
import com.liangmayong.airing.OnAiringListener;
import com.liangmayong.preferences.Preferences;

/**
 * Created by LiangMaYong on 2016/9/11.
 */
public class Skin {

    private static final String SKIN_PREFERENCES_NAME = "android_base_skin";
    private static final String SKIN_AIRING_EVENT_NAME = "android_base_skin";
    private static volatile Preferences preferences = null;
    private static volatile Skin skin = null;

    /**
     * registerSkinRefresh
     *
     * @param object object
     */
    public static void registerSkinRefresh(Object object) {
        if (isClassGeneric(object.getClass(), OnSkinRefreshListener.class.getName())) {
            Airing.getDefault().observer(object).register(SKIN_AIRING_EVENT_NAME, new OnAiringListener() {
                @Override
                public void onAiring(AiringContent airingContent) {
                    ((OnSkinRefreshListener) airingContent.getTarget()).onRefreshSkin(Skin.get());
                }
            });
        }
    }

    /**
     * unregisterSkinRefresh
     *
     * @param object object
     */
    public static void unregisterSkinRefresh(Object object) {
        Airing.getDefault().observer(object).unregister(SKIN_AIRING_EVENT_NAME);
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
     * setSkinColor
     *
     * @param themeColor themeColor
     */
    public static void setSkinColor(int themeColor) {
        getSkinPreferences().setInt("themeColor", themeColor);
        get().setThemeColor(themeColor);
        Airing.getDefault().sender(SKIN_PREFERENCES_NAME).sendEmpty();
    }

    /**
     * hasThemeColor
     *
     * @return hasThemeColor
     */
    public static boolean hasThemeColor() {
        if (get().getThemeColor() != 0) {
            return true;
        }
        return false;
    }

    /**
     * setSkinExtra
     *
     * @param key   key
     * @param value value
     */
    public static void setSkinExtra(String key, String value) {
        getSkinPreferences().setString(key, value);
        Airing.getDefault().sender(SKIN_PREFERENCES_NAME).sendEmpty();
    }

    /**
     * getSkin
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

    private Skin() {
        themeColor = getSkinPreferences().getInt("themeColor", 0);
    }

    //themeColor
    private int themeColor = 0;

    /**
     * getThemeColor
     *
     * @return themeColor
     */
    public int getThemeColor() {
        return themeColor;
    }

    /**
     * setThemeColor
     *
     * @param themeColor themeColor
     */
    private void setThemeColor(int themeColor) {
        this.themeColor = themeColor;
    }

    /**
     * getExtra
     *
     * @param key key
     * @return value
     */
    public String getExtra(String key) {
        return getSkinPreferences().contains(key) ? getSkinPreferences().getString(key) : "";
    }

}
