package com.liangmayong.android_base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.liangmayong.base.BaseActivity;
import com.liangmayong.base.support.binding.annotations.BindLayout;
import com.liangmayong.base.support.binding.annotations.BindOnClick;
import com.liangmayong.base.support.binding.annotations.BindTitle;
import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.widget.iconfont.Icon;

/**
 * Created by LiangMaYong on 2016/12/26.
 */
@BindLayout(R.layout.activity_item)
@BindTitle("Test")
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initDefaultToolbar() {
        super.initDefaultToolbar();
        getDefaultToolbar().leftOne().iconToLeft(Icon.icon_photo).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("action");

            }
        });
    }

    @BindOnClick({R.id.btn_test})
    private void bindOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                SkinManager.editor().setThemeColor(0xfffcb315, 0xffffffff).commit();
                break;
        }
    }
}
