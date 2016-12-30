package com.liangmayong.android_base;

import com.liangmayong.android_base.demo.StackF;
import com.liangmayong.base.basic.flow.FlowBaseActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;

public class MainActivity extends FlowBaseActivity {

    @Override
    protected FlowBaseFragment getFristFragment() {
        return new StackF();
    }
}
