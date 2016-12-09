package com.liangmayong.base.web;

import com.liangmayong.base.sub.BaseSubActivity;
import com.liangmayong.base.sub.BaseSubFragment;
import com.liangmayong.base.web.fragments.DefaultWebFragment;
import com.liangmayong.base.support.base.IBase;

/**
 * Created by LiangMaYong on 2016/10/17.
 */

public class WebActivity extends BaseSubActivity {

    @Override
    public BaseSubFragment generateSubFragment() {
        String title = getIntent().getStringExtra(IBase.WEB_EXTRA_TITLE);
        String url = getIntent().getStringExtra(IBase.WEB_EXTRA_URL);
        boolean more = "true".equals(getIntent().getStringExtra(IBase.WEB_EXTRA_MORE));
        return new DefaultWebFragment(title, url, more);
    }

}
