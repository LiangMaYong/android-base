package com.liangmayong.base.interfaces;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.liangmayong.base.presenter.Presenter;
import com.liangmayong.base.widget.toolbar.DefualtToolbar;
import com.liangmayong.skin.OnSkinRefreshListener;

/**
 * Created by LiangMaYong on 2016/9/14.
 */
public interface BaseInterface extends OnSkinRefreshListener {

    String WEB_EXTRA_URL = "web_extra_url";
    String WEB_EXTRA_TITLE = "web_extra_title";

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
     * goTo
     *
     * @param title title
     * @param url   url
     */
    void goTo(String title, String url);

    /**
     * goHome
     */
    void goHome();

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
     * getDefualtToolbar
     *
     * @return defualt toolbar
     */
    DefualtToolbar getDefualtToolbar();


    /**
     * hideSoftKeyBoad
     */
    void hideSoftKeyBoard();

    /**
     * showSoftKeyBoad
     *
     * @param editText editText
     */
    void showSoftKeyBoard(EditText editText);

    /**
     * presenterType
     *
     * @param presenterType presenterType
     */
    void addPresenter(Class<? extends Presenter>... presenterType);
}
