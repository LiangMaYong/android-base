package com.liangmayong.android_base.mvp;

import android.content.Context;
import android.os.Handler;

import com.liangmayong.base.widget.binding.Presenter;

/**
 * Created by LiangMaYong on 2016/11/3.
 */

public class ListPresenter extends Presenter<ListPresenter.IView> {

    public interface IView {
        Context getContext();

        void loadDataSuccess(String data);
    }

    public void load() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getViewInstance().loadDataSuccess("String");
            }
        }, 5000);
    }

}
