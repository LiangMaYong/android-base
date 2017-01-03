package com.liangmayong.android_base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.liangmayong.base.basic.BaseActivity;
import com.liangmayong.base.binding.view.annotations.BindLayout;
import com.liangmayong.base.binding.view.annotations.BindTitle;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.support.transitions.ActivityTransition;
import com.liangmayong.base.support.transitions.ExitActivityTransition;
import com.liangmayong.base.widget.iconfont.Icon;

/**
 * Created by LiangMaYong on 2017/1/3.
 */
@BindLayout(R.layout.activity_image)
@BindTitle("ImageViewActivity")
public class ImageViewActivity extends BaseActivity {
    ExitActivityTransition activityTransition = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.imgview)).bg(getWindow().getDecorView()).start(savedInstanceState);
    }


    @Override
    public void onBackPressed() {
        activityTransition.exit(this);
    }

    @Override
    public void initDefaultToolbar(DefaultToolbar defaultToolbar) {
        super.initDefaultToolbar(defaultToolbar);
        defaultToolbar.leftOne().icon(Icon.icon_back).click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
