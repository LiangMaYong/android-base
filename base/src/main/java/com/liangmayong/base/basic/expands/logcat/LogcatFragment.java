package com.liangmayong.base.basic.expands.logcat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.liangmayong.base.R;
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.binding.view.annotations.BindTitle;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.widget.iconfont.IconFont;
import com.liangmayong.base.widget.view.LogcatTextView;
import com.liangmayong.base.widget.skinview.SkinButton;

/**
 * Created by LiangMaYong on 2017/1/4.
 */
@BindTitle("Logcat")
public class LogcatFragment extends FlowBaseFragment {

    // extra_logcat_tag
    public static final String EXTRA_LOGCAT_TAG = "extra_logcat_tag";
    private LogcatTextView base_default_logcat;
    private EditText base_edit_logcat_tag;
    private SkinButton base_btn_logcat_set;

    /**
     * newInstance
     *
     * @param tag title
     * @return FlowLogcatFragment
     */
    public static LogcatFragment newInstance(String tag) {
        Bundle extras = new Bundle();
        extras.putString(LogcatFragment.EXTRA_LOGCAT_TAG, tag);
        return (LogcatFragment) new LogcatFragment().initArguments(extras);
    }

    /**
     * newInstance
     *
     * @return FlowLogcatFragment
     */
    public static LogcatFragment newInstance() {
        return new LogcatFragment();
    }

    @Override
    protected void initViews(View containerView) {
        _initView(containerView);
        if (getArguments() != null) {
            String tag = getArguments().getString(EXTRA_LOGCAT_TAG);
            base_default_logcat.setLogcatTag(tag);
            base_edit_logcat_tag.setText(tag);
        }
        base_default_logcat.refreshLogcat();
    }

    @Override
    public void onInitDefaultToolbar(DefaultToolbar defaultToolbar) {
        super.onInitDefaultToolbar(defaultToolbar);
        defaultToolbar.rightOne().icon(IconFont.base_icon_delete).click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base_default_logcat.clearLogcat();
            }
        });
        defaultToolbar.rightTwo().icon(IconFont.base_icon_refresh).click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base_default_logcat.refreshLogcat();
            }
        });
    }

    @Override
    protected int getContaierLayoutId() {
        return R.layout.base_default_fragment_logcat;
    }

    private void _initView(View rootView) {
        base_default_logcat = (LogcatTextView) rootView.findViewById(R.id.base_default_logcat);
        base_edit_logcat_tag = (EditText) rootView.findViewById(R.id.base_default_edit_logcat_tag);
        base_btn_logcat_set = (SkinButton) rootView.findViewById(R.id.base_default_btn_logcat_set);
        base_btn_logcat_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {
        // validate
        String tag = base_edit_logcat_tag.getText().toString().trim();
        base_default_logcat.setLogcatTag(tag);
    }
}
