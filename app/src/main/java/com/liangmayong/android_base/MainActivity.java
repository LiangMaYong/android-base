package com.liangmayong.android_base;

import android.os.Bundle;

import com.liangmayong.android_base.demo.DemoContentFragment;
import com.liangmayong.android_base.demo.DemoListFrag;
import com.liangmayong.base.sub.BaseSubActivity;
import com.liangmayong.base.sub.BaseSubFragment;

public class MainActivity extends BaseSubActivity {

    @Override
    public BaseSubFragment generateSubFragment() {
        return new DemoContentFragment();
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        super.onCreateActivity(savedInstanceState);
    }
}
