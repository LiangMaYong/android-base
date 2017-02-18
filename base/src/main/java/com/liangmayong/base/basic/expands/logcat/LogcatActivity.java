package com.liangmayong.base.basic.expands.logcat;

import com.liangmayong.base.basic.flow.FlowBaseActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;

/**
 * Created by LiangMaYong on 2016/10/17.
 */

public class LogcatActivity extends FlowBaseActivity {
    @Override
    protected FlowBaseFragment getFristFragment() {
        return new LogcatFragment().initArguments(getIntent().getExtras());
    }
}
