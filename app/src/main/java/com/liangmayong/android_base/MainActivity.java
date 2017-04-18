package com.liangmayong.android_base;

import com.liangmayong.android_base.fragment.ListFragment;
import com.liangmayong.base.basic.flow.FlowBaseActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;

/**
 * Created by LiangMaYong on 2016/12/26.
 */
public class MainActivity extends FlowBaseActivity {

    @Override
    protected FlowBaseFragment getFirstFragment() {
        return new ListFragment();
    }

}
