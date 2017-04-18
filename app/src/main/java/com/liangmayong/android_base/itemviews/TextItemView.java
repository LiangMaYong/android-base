package com.liangmayong.android_base.itemviews;

import android.view.View;
import android.widget.TextView;

import com.liangmayong.android_base.R;
import com.liangmayong.base.binding.view.annotations.BindLayout;
import com.liangmayong.base.binding.view.annotations.BindView;
import com.liangmayong.base.support.adapter.view.BindingSuperItemView;

/**
 * Created by LiangMaYong on 2016/9/25.
 */
@BindLayout(R.layout.item_text_view)
public class TextItemView extends BindingSuperItemView<Integer> {
    @BindView(R.id.tv_txt)
    private TextView tv_txt;

    public TextItemView(Integer s) {
        super(s);
    }

    @Override
    protected void onBindView(View itemView, Integer integer) {
        tv_txt.setText(integer + "-----------");
    }
}

