package com.liangmayong.android_base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.liangmayong.base.BaseActivity;
import com.liangmayong.base.viewbinding.annotations.BindLayout;
import com.liangmayong.base.viewbinding.annotations.BindTitle;
import com.liangmayong.base.widget.iconfont.Icon;

/**
 * Created by LiangMaYong on 2016/10/28.
 */
@BindLayout(R.layout.activity_item)
@BindTitle("Item Page")
public class ItemActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDefualtToolbar().leftOne().iconToLeft(Icon.icon_back).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
