package com.liangmayong.android_base.appbox;

import android.view.View;
import android.widget.TextView;

import com.liangmayong.android_base.R;
import com.liangmayong.base.compat.appbox.AppboxFragment;
import com.liangmayong.base.compat.binding.annotations.BindLayout;
import com.liangmayong.base.compat.binding.annotations.BindOnClick;
import com.liangmayong.base.compat.binding.annotations.BindTitle;
import com.liangmayong.base.compat.binding.annotations.BindView;
import com.liangmayong.base.widget.skin.SkinButton;

/**
 * Created by LiangMaYong on 2016/11/1.
 */
@BindLayout(R.layout.activity_item)
@BindTitle("AppFragment")
public class AppFragment extends AppboxFragment {

    @BindView(R.id.tv_title)
    private TextView tv_title;
    @BindView(R.id.et_content)
    private TextView et_content;
    @BindView(R.id.btn_test)
    private SkinButton btn_test;

    @Override
    protected void initView(View rootView) {
        tv_title.setText("AppFragment");
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
