package com.liangmayong.base.basic.expands.logcat;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.liangmayong.base.basic.flow.FlowBaseActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.support.utils.PermissionUtils;

/**
 * Created by LiangMaYong on 2016/10/17.
 */

public class LogcatActivity extends FlowBaseActivity {
    @Override
    protected FlowBaseFragment getFristFragment() {
        return new FlowLogcatFragment().initArguments(getIntent().getExtras());
    }
}
