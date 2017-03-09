package com.liangmayong.base.basic.flow.interfaces;

import android.view.KeyEvent;

/**
 * IFrag
 *
 * @author LiangMaYong
 * @version 1.0
 */
public interface IFrag {

    /**
     * onKeyDown
     *
     * @param keyCode keyCode
     * @param event   event
     * @return flag
     */
    boolean onKeyDown(int keyCode, KeyEvent event);

    /**
     * onKeyUp
     *
     * @param keyCode keyCode
     * @param event   event
     * @return flag
     */
    boolean onKeyUp(int keyCode, KeyEvent event);

    /**
     * onBackPressed
     *
     * @return flag
     */
    boolean onBackPressed();

    /**
     * onNewIntent
     */
    void onNewIntent();

}
