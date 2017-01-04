package com.liangmayong.android_base.demo;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangmayong.android_base.ImageViewActivity;
import com.liangmayong.android_base.R;
import com.liangmayong.base.basic.expands.logcat.FlowLogcatFragment;
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.binding.view.annotations.BindLayout;
import com.liangmayong.base.binding.view.annotations.BindTitle;
import com.liangmayong.base.binding.view.annotations.BindView;
import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.support.transitions.ActivityTransitionLauncher;
import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.shapeview.ShapeImageView;
import com.liangmayong.base.widget.skinview.SkinButton;

/**
 * Created by LiangMaYong on 2016/12/29.
 */
@BindLayout(R.layout.activity_item)
@BindTitle("Android-base")
public class StackF extends FlowBaseFragment {

    @BindView(R.id.tv_title)
    private TextView tv_title;
    @BindView(R.id.et_content)
    private TextView et_content;
    @BindView(R.id.btn_test)
    private SkinButton btn_test;
    @BindView(R.id.imgview)
    private ImageView imgview;

    @Override
    protected void initViews(View containerView) {
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getContext(), ImageViewActivity.class);
                ActivityTransitionLauncher.with(getActivity()).from(v).launch(intent);
            }
        });
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo("", "file:///android_asset/demo.html");
            }
        });
    }

    @Override
    public void initDefaultToolbar(DefaultToolbar defaultToolbar) {
        super.initDefaultToolbar(defaultToolbar);
        defaultToolbar.rightTwo().icon(Icon.icon_circle_yes).click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDefaultToolbar().message().show(Icon.icon_circle_yes, " 登录成功", SkinManager.get().getSuccessTextColor(), SkinManager.get().getSuccessColor(), 1500);
            }
        });
        defaultToolbar.rightOne().icon(Icon.icon_message).click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(FlowLogcatFragment.newInstance());
            }
        });
    }
}
