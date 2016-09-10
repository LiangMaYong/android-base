package com.liangmayong.superandroid;

import android.os.Bundle;
import android.view.View;

import com.liangmayong.base.BaseActivity;
import com.liangmayong.base.bind.annotations.ColorId;
import com.liangmayong.base.bind.annotations.Layout;
import com.liangmayong.base.bind.annotations.StringId;
import com.liangmayong.base.weight.iconfont.Icon;

@Layout(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @StringId
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


    }
}
