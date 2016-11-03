package com.liangmayong.android_base;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;

import com.liangmayong.android_base.demo.DemoListFrag;
import com.liangmayong.android_base.mvp.ListPresenter;
import com.liangmayong.base.sub.BaseSubActivity;
import com.liangmayong.base.sub.BaseSubFragment;
import com.liangmayong.base.sub.webkit.BaseWebWidget;
import com.liangmayong.base.ui.fragments.DefualtWebFragment;
import com.liangmayong.base.widget.binding.annotations.BindP;

@BindP(ListPresenter.class)
public class MainActivity extends BaseSubActivity implements ListPresenter.IView {

    @Override
    public BaseSubFragment generateSubFragment() {
        return new DemoListFrag();
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        super.onCreateActivity(savedInstanceState);
        //URL拦截
        DefualtWebFragment.addWebWidget("appbox://", new BaseWebWidget() {
            @Override
            public boolean overrideUrlLoading(WebView web, String url) {
                showToast(url);
                return true;
            }
        });
        getPresenter(ListPresenter.class).load();
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void loadDataSuccess(String data) {
         showToast(data);
    }
}
