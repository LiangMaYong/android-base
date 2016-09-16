package com.liangmayong.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.liangmayong.base.activitys.WebActivity;
import com.liangmayong.base.bind.mvp.Presenter;
import com.liangmayong.base.utils.ToastUtils;
import com.liangmayong.base.widget.themeskin.OnSkinRefreshListener;
import com.liangmayong.base.widget.themeskin.Skin;

import java.util.HashMap;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
public final class BasePresenter extends Presenter<BaseView> implements BaseInterfaces, OnSkinRefreshListener {

    //inputManager
    private InputMethodManager inputManager = null;

    @Override
    protected void onAttach(BaseView view) {
        super.onAttach(view);
        inputManager = (InputMethodManager) view.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Skin.registerSkinRefresh(this);
    }

    @Override
    protected void onDettach() {
        Skin.unregisterSkinRefresh(this);
        super.onDettach();
    }

    @Override
    public void showToast(CharSequence text) {
        ToastUtils.showToast(text);
    }

    @Override
    public void showToast(int stringId) {
        ToastUtils.showToast(getViewInstance().getActivity().getString(stringId));
    }

    @Override
    public void showToast(CharSequence text, int duration) {
        ToastUtils.showToast(text, duration);
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
        extras.putString(WEB_EXTRA_TITLE, title);
        extras.putString(WEB_EXTRA_URL, url);
        if (headers != null) {
            extras.putSerializable(WEB_EXTRA_HEADERS, headers);
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
    public void onRefreshSkin(Skin skin) {
    }
}
