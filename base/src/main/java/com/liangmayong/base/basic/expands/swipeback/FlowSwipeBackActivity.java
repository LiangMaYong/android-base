
package com.liangmayong.base.basic.expands.swipeback;

import android.os.Bundle;
import android.view.View;

import com.liangmayong.base.basic.expands.swipeback.core.SwipeBackActivityBase;
import com.liangmayong.base.basic.expands.swipeback.core.SwipeBackActivityHelper;
import com.liangmayong.base.basic.expands.swipeback.core.SwipeBackLayout;
import com.liangmayong.base.basic.flow.FlowBaseActivity;

public abstract class FlowSwipeBackActivity extends FlowBaseActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivtyCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
