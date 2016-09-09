package com.liangmayong.base.presenters.interfaces;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import com.liangmayong.base.bind.Presenter;
import com.liangmayong.base.weight.toolbar.DefualtToolbar;

import java.util.HashMap;

/**
 * Created by LiangMaYong on 2016/8/22.
 */
public class BaseInterfaces {

    public static final String PREFERENCES_THEME_COLOR = "base_thmem_color";
    public static final String REFRESH_THEME_COLOR_EVENT_NAME = "event_refresh_theme_color";

    public static final String WEB_EXTRA_URL = "url";
    public static final String WEB_EXTRA_TITLE = "title";
    public static final String WEB_EXTRA_HEADERS = "headers";
    public static final String WEB_JAVASCRIPT_INTERFACE_NAME = "AndroidJs";

    public static interface IPresenter {

        /**
         * setThemeColor
         *
         * @param color color
         */
        void setThemeColor(int color);

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
         * 跳转到制定页面
         *
         * @param cls cls
         */
        void goTo(Class<? extends Activity> cls);

        /**
         * 跳转到制定页面
         *
         * @param cls    cls
         * @param extras extras
         */
        void goTo(Class<? extends Activity> cls, Bundle extras);

        /**
         * 跳转到制定WEB页面
         *
         * @param title title
         * @param url   url
         */
        void goTo(String title, String url);

        /**
         * 跳转到制定WEB页面
         *
         * @param title   title
         * @param url     url
         * @param headers headers
         */
        void goTo(String title, String url, HashMap<String, String> headers);

        /**
         * 跳转到制定页面
         *
         * @param cls         cls
         * @param requestCode requestCode
         */
        void goToForResult(Class<? extends Activity> cls, int requestCode);

        /**
         * 跳转到制定页面
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

        /**
         * hasSetThemeColor
         *
         * @return has
         */
        boolean hasSetThemeColor();
    }

    public static interface IView {

        /**
         * getContext
         *
         * @return context
         */
        Activity getActivity();

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
         * 跳转到制定页面
         *
         * @param cls cls
         */
        void goTo(Class<? extends Activity> cls);

        /**
         * 跳转到制定页面
         *
         * @param cls    cls
         * @param extras extras
         */
        void goTo(Class<? extends Activity> cls, Bundle extras);

        /**
         * 跳转到制定WEB页面
         *
         * @param title title
         * @param url   url
         */
        void goTo(String title, String url);

        /**
         * 跳转到制定WEB页面
         *
         * @param title   title
         * @param url     url
         * @param headers headers
         */
        void goTo(String title, String url, HashMap<String, String> headers);

        /**
         * 跳转到制定页面
         *
         * @param cls         cls
         * @param requestCode requestCode
         */
        void goToForResult(Class<? extends Activity> cls, int requestCode);

        /**
         * 跳转到制定页面
         *
         * @param cls         cls
         * @param extras      extras
         * @param requestCode requestCode
         */
        void goToForResult(Class<? extends Activity> cls, Bundle extras, int requestCode);

        /**
         * refreshThemeColor
         *
         * @param color color
         */
        void refreshThemeColor(int color);

        /**
         * setThemeColor
         *
         * @param color color
         */
        void setThemeColor(int color);

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

}
