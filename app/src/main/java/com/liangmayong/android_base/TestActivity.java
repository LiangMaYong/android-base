package com.liangmayong.android_base;

import com.liangmayong.android_base.demo.DemoListFrag;
import com.liangmayong.base.basic.flow.FlowBaseActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.binding.view.annotations.BindLayout;
import com.liangmayong.base.binding.view.annotations.BindTitle;
import com.liangmayong.base.support.skin.SkinManager;

/**
 * Created by LiangMaYong on 2016/12/26.
 */
@BindLayout(R.layout.activity_item)
@BindTitle("Test")
public class TestActivity extends FlowBaseActivity {

    @Override
    protected FlowBaseFragment getFirstFragment() {
        SkinManager.editor().setThemeColor(0xFF3399FF, 0xFFFFFFFF).commit();
        return new DemoListFrag();
    }

}
