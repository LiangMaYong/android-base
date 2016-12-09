package com.liangmayong.base.support.skin;

import com.liangmayong.base.support.skin.handlers.SkinEditor;
import com.liangmayong.base.support.skin.handlers.SkinEvent;
import com.liangmayong.base.support.skin.handlers.SkinHandler;
import com.liangmayong.base.support.skin.handlers.SkinTheme;
import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.support.skin.interfaces.ISkinHandler;
import com.liangmayong.base.support.skin.listeners.OnSkinRefreshListener;

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

    /**
     * setSkinHandler
     *
     * @param skinHandler skinHandler
     */
    public static void setSkinHandler(ISkinHandler skinHandler) {
        SkinManager.skinHandler = skinHandler;
        if (skinHandler != null) {
            SkinEvent.refreshSkin();
        }
    }

    /**
     * editor
     *
     * @return editor
     */
    public static SkinEditor editor() {
        if (skinHandler == null) {
            return new SkinEditor(SkinHandler.getDefault());
        }
        return new SkinEditor(skinHandler);
    }

    /**
     * get
     *
     * @return skin
     */
    public static ISkin get() {
        if (skinHandler == null) {
            return new SkinTheme(SkinHandler.getDefault());
        }
        return new SkinTheme(skinHandler);
    }


    private SkinManager() {
    }
}
