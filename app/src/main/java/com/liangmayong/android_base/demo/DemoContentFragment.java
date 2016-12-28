package com.liangmayong.android_base.demo;

import android.util.Log;
import android.view.View;

import com.liangmayong.android_base.R;
import com.liangmayong.base.sub.BaseSubFragment;
import com.liangmayong.base.support.binding.annotations.BindLayout;
import com.liangmayong.base.support.binding.annotations.BindOnClick;
import com.liangmayong.base.support.binding.annotations.BindTitle;
import com.liangmayong.base.support.binding.annotations.BindView;
import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.logcat.LogcatTextView;
import com.liangmayong.base.widget.skinview.SkinButton;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@BindLayout(R.layout.activity_item)
@BindTitle("Item")
public class DemoContentFragment extends BaseSubFragment {

    @BindView(R.id.btn_test)
    private SkinButton btn_test;

    private String TAG = "TAG";

    @Override
    protected void initSubView(View rootView) {
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        getDefaultToolbar().rightTwo().iconToRight(Icon.icon_circle_yes).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDefaultToolbar().message().show(Icon.icon_circle_yes, " 登录成功", getSkin().getSuccessTextColor(), getSkin().getSuccessColor(), 1500);
            }
        });
        getDefaultToolbar().rightOne().iconToLeft(Icon.icon_message).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDefaultToolbar().message().show(Icon.icon_about, " 关于我们", getSkin().getInfoTextColor(), getSkin().getInfoColor(), 1500);
            }
        });
    }

    @BindOnClick({R.id.btn_test})
    private void bindOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
//                getDefaultToolbar().switchVisibility();
                goTo("百度一下", "http://www.baidu.com");
//                openFragment(new DemoListFrag());
                break;
        }
    }
}
