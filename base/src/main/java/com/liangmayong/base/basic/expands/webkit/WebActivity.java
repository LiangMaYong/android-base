package com.liangmayong.base.basic.expands.webkit;

import com.liangmayong.base.basic.expands.webkit.fragment.WebFragment;
import com.liangmayong.base.basic.flow.FlowBaseActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;

/**
 * Created by LiangMaYong on 2017/2/18.
 */

public class WebActivity extends FlowBaseActivity {
    @Override
    protected FlowBaseFragment getFirstFragment() {
        return new WebFragment();
    }
}
