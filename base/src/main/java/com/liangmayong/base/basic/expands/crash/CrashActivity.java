package com.liangmayong.base.basic.expands.crash;

import com.liangmayong.base.basic.flow.FlowBaseActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;

/**
 * Created by LiangMaYong on 2017/3/28.
 */
public class CrashActivity extends FlowBaseActivity {

    @Override
    protected FlowBaseFragment getFirstFragment() {
        return new CrashFragment();
    }

}
