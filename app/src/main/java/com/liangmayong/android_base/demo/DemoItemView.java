package com.liangmayong.android_base.demo;

import android.view.View;
import android.widget.TextView;

import com.liangmayong.android_base.R;
import com.liangmayong.base.widget.binding.annotations.BindLayout;
import com.liangmayong.base.widget.binding.annotations.BindView;
import com.liangmayong.base.widget.superlistview.item.SuperItem;

/**
 * Created by LiangMaYong on 2016/9/25.
 */
@BindLayout(R.layout.item_view)
public class DemoItemView extends SuperItem<String> {

    @BindView(R.id.tv_txt)
    private TextView tv_txt;
    @BindView(R.id.tv_sub_txt)
    private TextView tv_sub_txt;

    public DemoItemView(String s) {
        super(s);
    }

    @Override
    protected void onBindView(View itemView, String s) {
        tv_txt.setText(s);
    }
}
