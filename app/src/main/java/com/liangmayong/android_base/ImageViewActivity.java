package com.liangmayong.android_base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.liangmayong.base.basic.BaseActivity;
import com.liangmayong.base.binding.view.annotations.BindLayout;
import com.liangmayong.base.binding.view.annotations.BindTitle;
import com.liangmayong.base.binding.view.annotations.BindView;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.widget.iconfont.IconFont;
import com.liangmayong.base.widget.view.RectangleLayout;

/**
 * Created by LiangMaYong on 2017/1/3.
 */
@BindLayout(R.layout.activity_image)
@BindTitle("ImageViewActivity")
public class ImageViewActivity extends BaseActivity {
    @BindView(R.id.imgview)
    private RectangleLayout imgview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        transitionStart(findViewById(R.id.imgview), null);
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onInitDefaultToolbar(DefaultToolbar defaultToolbar) {
        super.onInitDefaultToolbar(defaultToolbar);
        defaultToolbar.leftOne().icon(IconFont.base_icon_back).click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
