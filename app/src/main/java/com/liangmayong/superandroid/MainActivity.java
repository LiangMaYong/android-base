package com.liangmayong.superandroid;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.liangmayong.base.BaseActivity;
import com.liangmayong.base.bind.annotations.ColorId;
import com.liangmayong.base.bind.annotations.Layout;
import com.liangmayong.base.bind.annotations.Title;
import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.pullrefresh.PullRefreshLayout;
import com.liangmayong.base.widget.pullrefresh.drawables.PictureDrawable;
import com.liangmayong.preferences.annotations.PreferenceValue;

@Layout(R.layout.activity_main)
@Title("关于我们")
public class MainActivity extends BaseActivity {

    @PreferenceValue("key")
    String app_name;

    @ColorId
    int colorPrimary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeColor(colorPrimary);
        // getDefualtToolbar().setTitle(app_name);
        getDefualtToolbar().setSubTitle("expo.com");


        getDefualtToolbar().leftOne().iconToLeft(Icon.icon_back).text("关于我们").nullBackground().clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        goTo("百度一下", "http://www.baidu.com");

        final PullRefreshLayout pullRefreshLayout = (PullRefreshLayout) findViewById(R.id.pull);
        PictureDrawable pictureDrawable = new PictureDrawable(pullRefreshLayout, R.mipmap.loading_bee, R.mipmap.loading_bee1, R.mipmap.loading_bee2, R.mipmap.loading_bee3, R.mipmap.loading_bee4, R.mipmap.loading_bee5, R.mipmap.loading_bee6);
        pullRefreshLayout.setRefreshDrawable(pictureDrawable);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullRefreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });

    }
}
