package com.liangmayong.base.basic.expands.crash;

import com.liangmayong.base.basic.expands.swipeback.FlowSwipeBackActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;

/**
 * Created by LiangMaYong on 2017/3/28.
 */
public class CrashActivity extends FlowSwipeBackActivity {

    @Override
    protected FlowBaseFragment getFirstFragment() {
        return new CrashFragment();
    }

}
