package com.liangmayong.base.basic.expands.logcat;

import com.liangmayong.base.basic.expands.swipeback.BaseSwipeBackActivity;
import com.liangmayong.base.basic.expands.swipeback.FlowSwipeBackActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
public class LogcatActivity extends FlowSwipeBackActivity {
    @Override
    protected FlowBaseFragment getFirstFragment() {
        return new LogcatFragment().initArguments(getIntent().getExtras());
    }
}
