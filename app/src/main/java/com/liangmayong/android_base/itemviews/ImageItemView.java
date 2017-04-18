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
@BindLayout(R.layout.item_image_view)
public class ImageItemView extends BindingSuperItemView<Integer> {

    @BindView(R.id.tv_txt)
    private TextView tv_txt;

    public ImageItemView(Integer s) {
        super(s);
    }

    @Override
    protected void onBindView(View itemView, Integer s) {
        tv_txt.setText(s + "");
    }
}
