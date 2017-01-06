package com.liangmayong.base.basic.expands.logcat;

import android.os.Bundle;
import android.view.View;

import com.liangmayong.base.R;
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.basic.interfaces.IBasic;
import com.liangmayong.base.binding.view.annotations.BindTitle;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.logcat.LogcatTextView;

/**
 * Created by LiangMaYong on 2017/1/4.
 */
@BindTitle("Logcat")
public class FlowLogcatFragment extends FlowBaseFragment {

    /**
     * newInstance
     *
     * @param tag title
     * @return FlowLogcatFragment
     */
    public static FlowLogcatFragment newInstance(String tag) {
        Bundle extras = new Bundle();
        extras.putString(IBasic.LOGCAT_EXTRA_TAG, tag);
        return (FlowLogcatFragment) new FlowLogcatFragment().initArguments(extras);
    }

    /**
     * newInstance
     *
     * @return FlowLogcatFragment
     */
    public static FlowLogcatFragment newInstance() {
        return new FlowLogcatFragment();
    }

    private LogcatTextView default_logcat;

    @Override
    protected void initViews(View containerView) {
        _initView(containerView);
        if (getArguments() != null) {
            String tag = getArguments().getString(IBasic.LOGCAT_EXTRA_TAG);
            default_logcat.setLogcatTag(tag);
        }
        default_logcat.refreshLogcat();
    }

    @Override
    public void initDefaultToolbar(DefaultToolbar defaultToolbar) {
        super.initDefaultToolbar(defaultToolbar);
        defaultToolbar.rightOne().icon(Icon.icon_delete).click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                default_logcat.clearLogcat();
            }
        });
        defaultToolbar.rightTwo().icon(Icon.icon_refresh).click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                default_logcat.refreshLogcat();
            }
        });
    }

    @Override
    protected int getContaierLayoutId() {
        return R.layout.base_default_fragment_logcat;
    }

    private void _initView(View rootView) {
        default_logcat = (LogcatTextView) rootView.findViewById(R.id.default_logcat);
    }
}
