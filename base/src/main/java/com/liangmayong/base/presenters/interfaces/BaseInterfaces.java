package com.liangmayong.base.presenters.interfaces;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.liangmayong.base.bind.Presenter;
import com.liangmayong.base.widget.themeskin.OnSkinRefreshListener;
import com.liangmayong.base.widget.themeskin.Skin;
import com.liangmayong.base.widget.toolbar.DefualtToolbar;

import java.util.HashMap;

/**
 * Created by LiangMaYong on 2016/8/22.
 */
public class BaseInterfaces {

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

        /**
         * hasSetThemeColor
         *
         * @return has
         */
        boolean hasSetThemeColor();
    }

    public static interface IView extends OnSkinRefreshListener {

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
