package com.liangmayong.base.basic.interfaces;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.liangmayong.base.binding.mvp.Presenter;
import com.liangmayong.base.support.fixbug.AndroidBug5497Workaround;
import com.liangmayong.base.support.theme.Theme;
import com.liangmayong.base.support.theme.listener.OnThemeListener;
import com.liangmayong.base.support.toolbar.DefaultToolbar;

/**
 * Created by LiangMaYong on 2016/9/14.
 */
public interface IBase extends OnThemeListener, AndroidBug5497Workaround.OnSoftKeyboardListener {

    String WEB_EXTRA_URL = "web_extra_url";
    String WEB_EXTRA_TITLE = "web_extra_title";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////  Temp   ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * getTemp
     *
     * @param key key
     * @param defTemp defTemp
     * @return temp
     */
    <T> T getTemp(String key, T defTemp);

    /**
     * setTemp
     *
     * @param key       key
     * @param temporary temporary
     */
    void setTemp(String key, Object temporary);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////  Toolbar   ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * getDefaultToolbar
     *
     * @return default_type toolbar
     */
    DefaultToolbar getDefaultToolbar();

    /**
     * defaultToolbar
     *
     * @param defaultToolbar defaultToolbar
     */
    void onInitDefaultToolbar(DefaultToolbar defaultToolbar);


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////  Theme   ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    void onThemeEdited(Theme theme);


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////  Toast   ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * showToast
     *
     * @param text text
     */
    void showToast(CharSequence text);

    /**
     * showToast
     *
     * @param stringId stringId
     */
    void showToast(int stringId);

    /**
     * showToast
     *
     * @param text     text
     * @param duration duration
     */
    void showToast(CharSequence text, int duration);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////   Go to   ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * goTo
     *
     * @param cls cls
     */
    void goTo(Class<? extends Activity> cls);

    /**
     * goTo
     *
     * @param cls    cls
     * @param extras extras
     */
    void goTo(Class<? extends Activity> cls, Bundle extras);

    /**
     * goToForResult
     *
     * @param cls         cls
     * @param requestCode requestCode
     */
    void goToForResult(Class<? extends Activity> cls, int requestCode);

    /**
     * goToForResult
     *
     * @param cls         cls
     * @param extras      extras
     * @param requestCode requestCode
     */
    void goToForResult(Class<? extends Activity> cls, Bundle extras, int requestCode);

    /**
     * goHome
     */
    void goHome();


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////// KeyBoard  ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * hideSoftKeyBoad
     */
    void hideSoftKeyBoard();

    /**
     * showSoftKeyBoad
     *
     * @param editText editText
     */
    void showSoftKeyBoard(EditText editText, long delay);

    /**
     * ignoreTouchHideSoftKeyboard
     *
     * @param view view
     */
    void ignoreTouchHideSoftKeyboard(View view);

    /**
     * touchHideSoftKeyboard
     *
     * @param view view
     */
    void touchHideSoftKeyboard(View view);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////// Presenter ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * getPresenter
     *
     * @param cls cls
     * @param <T> type
     * @return presenter
     */
    <T extends Presenter> T getPresenter(Class<T> cls);
}
