package com.liangmayong.android_base;

import com.liangmayong.base.sub.BaseSubActivity;
import com.liangmayong.base.sub.BaseSubFragment;
import com.liangmayong.viewbinding.annotations.BindTitle;

@BindTitle("AndroidBase")
public class MainActivity extends BaseSubActivity {

    @Override
    public BaseSubFragment generateSubFragment() {
        return new SubFrag();
    }

    @Override
    protected String generateWatermarkText() {
        return "AndroidBase";
    }
}
