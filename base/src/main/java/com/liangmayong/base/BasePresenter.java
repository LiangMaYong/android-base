package com.liangmayong.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.liangmayong.base.activitys.WebActivity;
import com.liangmayong.base.interfaces.BaseInterface;
import com.liangmayong.base.utils.ToastUtils;
import com.liangmayong.presenter.Presenter;

import java.util.HashMap;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
public final class BasePresenter extends Presenter<BaseInterface> {

    //inputManager
    private InputMethodManager inputManager = null;

    @Override
    public void onAttach(BaseInterface view) {
        super.onAttach(view);
        inputManager = (InputMethodManager) view.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onDettach() {
        super.onDettach();
    }

    public void showToast(CharSequence text) {
        ToastUtils.showToast(text);
    }

    public void showToast(int stringId) {
        ToastUtils.showToast(getViewInstance().getActivity().getString(stringId));
    }

    public void showToast(CharSequence text, int duration) {
        ToastUtils.showToast(text, duration);
    }

    public void goTo(Class<? extends Activity> cls) {
        goToForResult(cls, null, -1);
    }

    public void goTo(Class<? extends Activity> cls, Bundle extras) {
        goToForResult(cls, extras, -1);
    }

    public void goTo(String title, String url) {
        goTo(title, url, null);
    }

    public void goTo(String title, String url, HashMap<String, String> headers) {
        Bundle extras = new Bundle();
        extras.putString(BaseInterface.WEB_EXTRA_TITLE, title);
        extras.putString(BaseInterface.WEB_EXTRA_URL, url);
        if (headers != null) {
            extras.putSerializable(BaseInterface.WEB_EXTRA_HEADERS, headers);
        }
        goTo(WebActivity.class, extras);
    }

    public void goToForResult(Class<? extends Activity> cls, int requestCode) {
        goToForResult(cls, null, requestCode);
    }

    public void goToForResult(Class<? extends Activity> cls, Bundle extras, int requestCode) {
        Intent intent = new Intent(getViewInstance().getActivity(), cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        getViewInstance().getActivity().startActivityForResult(intent, requestCode);
    }

    public void hideSoftKeyBoard() {
        if (inputManager.isActive() && getViewInstance().getActivity().getCurrentFocus() != null) {
            if (getViewInstance().getActivity().getCurrentFocus().getWindowToken() != null) {
                inputManager.hideSoftInputFromWindow(getViewInstance().getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void showSoftKeyBoard(final EditText editText) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                inputManager.showSoftInput(editText, 0);
            }
        }, 500);
    }

}
