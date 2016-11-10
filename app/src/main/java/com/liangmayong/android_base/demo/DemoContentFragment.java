package com.liangmayong.android_base.demo;

import android.view.View;
import android.widget.TextView;

import com.liangmayong.android_base.R;
import com.liangmayong.base.sub.BaseSubFragment;
import com.liangmayong.base.widget.binding.annotations.BindLayout;
import com.liangmayong.base.widget.binding.annotations.BindOnClick;
import com.liangmayong.base.widget.binding.annotations.BindTitle;
import com.liangmayong.base.widget.binding.annotations.BindView;
import com.liangmayong.base.widget.skin.SkinButton;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@BindLayout(R.layout.activity_item)
@BindTitle("Item")
public class DemoContentFragment extends BaseSubFragment {

    @BindView(R.id.tv_title)
    private TextView tv_title;
    @BindView(R.id.btn_test)
    private SkinButton btn_test;

    @Override
    protected void initSubView(View rootView) {
        //getDefualtToolbar().setTitle(getArguments().getString("title"));
        //tv_title.setText(getArguments().getString("title"));
    }

    @BindOnClick({R.id.btn_test})
    private void bindOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                goTo("百度一下", "http://www.baidu.com");
                break;
        }
    }
}
