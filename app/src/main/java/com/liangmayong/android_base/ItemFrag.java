package com.liangmayong.android_base;

import android.view.View;
import android.widget.TextView;

import com.liangmayong.base.sub.BaseSubFragment;
import com.liangmayong.base.viewbinding.annotations.BindLayout;
import com.liangmayong.base.viewbinding.annotations.BindTitle;
import com.liangmayong.base.viewbinding.annotations.BindView;

/**
 * Created by LiangMaYong on 2016/10/17.
 */
@BindLayout(R.layout.activity_item)
@BindTitle("Item")
public class ItemFrag extends BaseSubFragment {


    @BindView(R.id.ll_name)
    private TextView ll_name;

    @Override
    protected void initSubView(View rootView) {
        ll_name.setText(getArguments().getString("title"));
    }

}
