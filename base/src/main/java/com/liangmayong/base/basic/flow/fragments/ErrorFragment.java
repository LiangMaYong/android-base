package com.liangmayong.base.basic.flow.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.liangmayong.base.basic.flow.FlowBaseFragment;

/**
 * Created by LiangMaYong on 2016/12/29.
 */
public class ErrorFragment extends FlowBaseFragment {


    // extra_logcat_tag
    public static final String EXTRA_ERROR_TAG = "extra_error_tag";

    /**
     * newInstance
     *
     * @param error error
     * @return FlowLogcatFragment
     */
    public static ErrorFragment newInstance(String error) {
        Bundle extras = new Bundle();
        extras.putString(ErrorFragment.EXTRA_ERROR_TAG, error);
        return (ErrorFragment) new ErrorFragment().initArguments(extras);
    }

    private String error = "";
    private TextView textView = null;

    @Override
    protected void initViews(View containerView) {
        error = getArguments().getString(ErrorFragment.EXTRA_ERROR_TAG);
        textView.setText(error + "");
    }

    @Override
    protected View getContaierView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout frameLayout = new FrameLayout(inflater.getContext());
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView = new TextView(inflater.getContext());
        frameLayout.addView(textView);
        textView.setTextSize(14);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(0xff18a28b);
        return frameLayout;
    }

    @Override
    protected int getContaierLayoutId() {
        return 0;
    }
}
