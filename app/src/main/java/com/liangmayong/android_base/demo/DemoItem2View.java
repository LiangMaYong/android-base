package com.liangmayong.android_base.demo;

import android.view.View;
import android.widget.TextView;

import com.liangmayong.android_base.R;
import com.liangmayong.base.support.binding.annotations.BindLayout;
import com.liangmayong.base.support.binding.annotations.BindView;
import com.liangmayong.base.widget.skinview.SkinRippleButton;
import com.liangmayong.base.widget.superlistview.item.SuperItem;

/**
 * Created by LiangMaYong on 2016/9/25.
 */
@BindLayout(R.layout.item2_view)
public class DemoItem2View extends SuperItem<String> {

    @BindView(R.id.tv_txt)
    private TextView tv_txt;
    @BindView(R.id.btn_show)
    private SkinRippleButton btn_show;

    public DemoItem2View(String s) {
        super(s);
    }

    @Override
    protected void onBindView(View itemView, String s) {
        tv_txt.setText(s);
        setClickable(false);
        if (getOnItemClickListener() != null) {
            btn_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnItemClickListener().onClick(DemoItem2View.this, getPosition(), v);
                }
            });
        }
    }
}
