package com.liangmayong.base.activitys;

import com.liangmayong.base.fragments.DefualtWebFragment;
import com.liangmayong.base.interfaces.BaseInterface;
import com.liangmayong.base.sub.BaseSubActivity;
import com.liangmayong.base.sub.BaseSubFragment;

/**
 * Created by LiangMaYong on 2016/10/17.
 */

public class WebActivity extends BaseSubActivity {

    @Override
    public BaseSubFragment generateSubFragment() {
        String title = getIntent().getStringExtra(BaseInterface.WEB_EXTRA_TITLE);
        String url = getIntent().getStringExtra(BaseInterface.WEB_EXTRA_URL);
        return new DefualtWebFragment(title, url);
    }
}
