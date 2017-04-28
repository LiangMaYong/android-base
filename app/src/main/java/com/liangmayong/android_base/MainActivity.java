package com.liangmayong.android_base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liangmayong.android_base.air.Demo;
import com.liangmayong.android_base.fragment.ListFragment;
import com.liangmayong.base.airbus.AirBus;
import com.liangmayong.base.airbus.annotations.OnAir;
import com.liangmayong.base.airbus.annotations.UnAir;
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

    private void onAirMain(Demo demo) {
        showToast(demo + "");
    }

}
