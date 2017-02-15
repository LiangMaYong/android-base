package com.liangmayong.android_base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.liangmayong.base.basic.BaseActivity;
import com.liangmayong.base.binding.view.annotations.BindLayout;
import com.liangmayong.base.binding.view.annotations.BindTitle;
import com.liangmayong.base.binding.view.annotations.BindView;
import com.liangmayong.base.support.shake.Shake;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.support.transitions.ActivityTransition;
import com.liangmayong.base.support.transitions.ExitActivityTransition;
import com.liangmayong.base.widget.iconfont.IconFont;
import com.liangmayong.base.widget.layout.SquareLinearLayout;

/**
 * Created by LiangMaYong on 2017/1/3.
 */
@BindLayout(R.layout.activity_image)
@BindTitle("ImageViewActivity")
public class ImageViewActivity extends BaseActivity {
    private ExitActivityTransition activityTransition = null;
    @BindView(R.id.imgview)
    private SquareLinearLayout imgview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        activityTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.imgview)).bg(getWindow().getDecorView()).start(savedInstanceState);
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityTransition.exit(ImageViewActivity.this);
            }
        });
        Shake.shakeActivity(this, 500, true);
    }


    @Override
    public void onBackPressed() {
        activityTransition.exit(this);
    }

    @Override
    public void initDefaultToolbar(DefaultToolbar defaultToolbar) {
        super.initDefaultToolbar(defaultToolbar);
        defaultToolbar.leftOne().icon(IconFont.base_icon_back).click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
