package com.liangmayong.android_base;

import android.view.View;

import com.liangmayong.base.sub.BaseSubFragment;
import com.liangmayong.viewbinding.annotations.BindLayout;
import com.liangmayong.viewbinding.annotations.BindTitle;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@BindLayout(R.layout.activity_main)
@BindTitle("TestSub2")
public class SubFrag2 extends BaseSubFragment {

    @Override
    protected void initSubView(View rootView) {

    }

    @Override
    protected boolean onBackPressed() {
        showToast("onBackPressed");
        return false;
    }
}
