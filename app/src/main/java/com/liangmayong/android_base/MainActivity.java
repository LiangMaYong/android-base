package com.liangmayong.android_base;

import android.os.Bundle;

import com.liangmayong.android_base.demo.StackF;
import com.liangmayong.base.basic.flow.FlowBaseActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.support.deamon.NativeRuntime;
import com.liangmayong.base.support.skin.SkinManager;

public class MainActivity extends FlowBaseActivity {

    @Override
    public void onFlowActivityCreate(Bundle savedInstanceState) {
        super.onFlowActivityCreate(savedInstanceState);
        SkinManager.editor().setThemeColor(0xFFDA4D3E,0xFFFFFFFF).commit();
    }

    @Override
    protected FlowBaseFragment getFristFragment() {
        return new StackF();
    }
}
