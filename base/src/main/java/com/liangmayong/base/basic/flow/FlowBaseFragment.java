package com.liangmayong.base.basic.flow;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.liangmayong.base.basic.BaseFragment;
import com.liangmayong.base.basic.flow.interfaces.IFrag;
import com.liangmayong.base.basic.flow.stack.StackManager;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.widget.iconfont.IconFont;

/**
 * FlowBaseFragment
 *
 * @author LiangMaYong
 * @version 1.0
 */
public abstract class FlowBaseFragment extends BaseFragment implements IFrag {

    public static final int STACK_STANDARD = StackManager.STACK_STANDARD;
    public static final int STACK_SINGLE_INSTANCE = StackManager.STACK_SINGLE_INSTANCE;
    private FlowBaseActivity activity;

    @Override
    public FlowBaseFragment initArguments(Bundle extras) {
        return (FlowBaseFragment) super.initArguments(extras);
    }

    @Override
    public void onInitDefaultToolbar(DefaultToolbar defaultToolbar) {
        super.onInitDefaultToolbar(defaultToolbar);
        defaultToolbar.leftOne().icon(IconFont.base_icon_back).click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSelf();
            }
        });
    }

    /**
     * open a new Fragment
     *
     * @param fragment fragment
     */
    public void open(FlowBaseFragment fragment) {
        open(fragment, null);
    }

    /**
     * open fragment
     *
     * @param fragment fragment
     * @param bundle   bundle
     */
    protected void open(FlowBaseFragment fragment, Bundle bundle) {
        if (getFlowActivity() == null) {
            return;
        }
        getFlowActivity().getStackManager().openFragment(this, fragment, bundle, STACK_STANDARD);
    }

    /**
     * openSingleInstance
     *
     * @param fragment fragment
     */
    protected void openSingleInstance(FlowBaseFragment fragment) {
        openSingleInstance(fragment, null);
    }

    /**
     * openSingleInstance
     *
     * @param fragment fragment
     * @param bundle   bundle
     */
    protected void openSingleInstance(FlowBaseFragment fragment, Bundle bundle) {
        if (getFlowActivity() == null) {
            return;
        }
        getFlowActivity().getStackManager().openFragment(this, fragment, bundle, STACK_SINGLE_INSTANCE);
    }

    /**
     * Closes the specified fragment
     *
     * @param fragment the specified fragment
     */
    protected void close(FlowBaseFragment fragment) {
        getFlowActivity().getStackManager().closeFragment(fragment);
    }

    /**
     * closeFragment this current Fragment
     */
    protected void closeSelf() {
        getFlowActivity().getStackManager().closeFragment(this);
    }

    /**
     * Get fragment dependent Activity, many times this is very useful
     *
     * @return LFragmentActivity dependent Activity
     */
    public FlowBaseActivity getFlowActivity() {
        if (activity == null) {
            if (getActivity() instanceof FlowBaseActivity) {
                activity = (FlowBaseActivity) getActivity();
            }
        }
        return activity;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean flag = onBackPressed();
            if (flag) {
                return flag;
            }
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    /**
     * onTouchEvent
     *
     * @param event event
     * @return true or false
     */
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
