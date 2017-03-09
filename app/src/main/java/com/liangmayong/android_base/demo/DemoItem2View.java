package com.liangmayong.android_base.demo;

import android.view.View;
import android.widget.TextView;

import com.liangmayong.android_base.R;
import com.liangmayong.base.binding.view.annotations.BindLayout;
import com.liangmayong.base.binding.view.annotations.BindView;
import com.liangmayong.base.support.adapter.view.BindingSuperItemView;

/**
 * Created by LiangMaYong on 2016/9/25.
 */
@BindLayout(R.layout.item2_view)
public class DemoItem2View extends BindingSuperItemView<String> {

    @BindView(R.id.tv_txt)
    private TextView tv_txt;

    public DemoItem2View(String s) {
        super(s);
    }

    @Override
    protected void onBindView(View itemView, String s) {
        tv_txt.setText(s);
    }
}
