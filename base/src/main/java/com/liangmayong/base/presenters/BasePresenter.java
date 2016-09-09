package com.liangmayong.base.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.liangmayong.airing.Airing;
import com.liangmayong.airing.AiringContent;
import com.liangmayong.airing.OnAiringListener;
import com.liangmayong.base.activitys.WebActivity;
import com.liangmayong.base.bind.Presenter;
import com.liangmayong.base.presenters.interfaces.BaseInterfaces;
import com.liangmayong.base.utils.ToastUtils;
import com.liangmayong.preferences.Preferences;

import java.util.HashMap;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
public class BasePresenter extends Presenter<BaseInterfaces.IView> implements BaseInterfaces.IPresenter {

    //inputManager
    private InputMethodManager inputManager = null;

    @Override
    protected void onAttach(BaseInterfaces.IView view) {
        super.onAttach(view);
        inputManager = (InputMethodManager) view.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        handleRefreshThemeColor();
        Airing.getDefault().observer(this).register(BaseInterfaces.REFRESH_THEME_COLOR_EVENT_NAME, new OnAiringListener() {
            @Override
            public void onAiring(AiringContent airingContent) {
                handleRefreshThemeColor();
            }
        });
    }

    private void handleRefreshThemeColor() {
        if (Preferences.getDefaultPreferences().contains(BaseInterfaces.PREFERENCES_THEME_COLOR)) {
            int color = Preferences.getDefaultPreferences().getInt(BaseInterfaces.PREFERENCES_THEME_COLOR, 0);
            if (color != 0) {
                getViewInstance().refreshThemeColor(color);
            }
        }
    }

    @Override
    protected void onDettach() {
        super.onDettach();
        Airing.unregister(this);
    }

    @Override
    public void setThemeColor(int color) {
        Preferences.getDefaultPreferences().setInt(BaseInterfaces.PREFERENCES_THEME_COLOR, color);
        Airing.getDefault().sender(BaseInterfaces.REFRESH_THEME_COLOR_EVENT_NAME).sendEmpty();
    }

    @Override
    public void showToast(CharSequence text) {
        ToastUtils.showToast(getViewInstance().getActivity(), text);
    }

    @Override
    public void showToast(int stringId) {
        ToastUtils.showToast(getViewInstance().getActivity(), getViewInstance().getActivity().getString(stringId));
    }

    @Override
    public void showToast(CharSequence text, int duration) {
        ToastUtils.showToast(getViewInstance().getActivity(), text, duration);
    }

    @Override
    public void goTo(Class<? extends Activity> cls) {
        goToForResult(cls, null, -1);
    }

    @Override
    public void goTo(Class<? extends Activity> cls, Bundle extras) {
        goToForResult(cls, extras, -1);
    }

    @Override
    public void goTo(String title, String url) {
        goTo(title, url, null);
    }

    @Override
    public void goTo(String title, String url, HashMap<String, String> headers) {
        Bundle extras = new Bundle();
        extras.putString(BaseInterfaces.WEB_EXTRA_TITLE, title);
        extras.putString(BaseInterfaces.WEB_EXTRA_URL, url);
        if (headers != null) {
            extras.putSerializable(BaseInterfaces.WEB_EXTRA_HEADERS, headers);
        }
        goTo(WebActivity.class, extras);
    }

    @Override
    public void goToForResult(Class<? extends Activity> cls, int requestCode) {
        goToForResult(cls, null, requestCode);
    }

    @Override
    public void goToForResult(Class<? extends Activity> cls, Bundle extras, int requestCode) {
        Intent intent = new Intent(getViewInstance().getActivity(), cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        getViewInstance().getActivity().startActivityForResult(intent, requestCode);
    }

    @Override
    public void hideSoftKeyBoard() {
        if (inputManager.isActive() && getViewInstance().getActivity().getCurrentFocus() != null) {
            if (getViewInstance().getActivity().getCurrentFocus().getWindowToken() != null) {
                inputManager.hideSoftInputFromWindow(getViewInstance().getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void showSoftKeyBoard(final EditText editText) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                inputManager.showSoftInput(editText, 0);
            }
        }, 500);
    }

    @Override
    public boolean hasSetThemeColor() {
        return Preferences.getDefaultPreferences().contains(BaseInterfaces.PREFERENCES_THEME_COLOR);
    }
}
