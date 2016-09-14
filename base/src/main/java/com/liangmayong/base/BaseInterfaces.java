package com.liangmayong.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.HashMap;

/**
 * Created by LiangMaYong on 2016/8/22.
 */
public interface BaseInterfaces {

    public static final String WEB_EXTRA_URL = "url";
    public static final String WEB_EXTRA_TITLE = "title";
    public static final String WEB_EXTRA_HEADERS = "headers";
    public static final String WEB_JAVASCRIPT_INTERFACE_NAME = "AndroidJs";


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
     * goTo
     *
     * @param title   title
     * @param url     url
     * @param headers headers
     */
    void goTo(String title, String url, HashMap<String, String> headers);

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
     * hideSoftKeyBoad
     */
    void hideSoftKeyBoard();

    /**
     * showSoftKeyBoad
     *
     * @param editText editText
     */
    void showSoftKeyBoard(EditText editText);
}
