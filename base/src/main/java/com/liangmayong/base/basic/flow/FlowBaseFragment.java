package com.liangmayong.base.basic.flow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    public static final int STANDARD = StackManager.STANDARD;
    public static final int SINGLE_TOP = StackManager.SINGLE_TOP;
    public static final int SINGLE_TASK = StackManager.SINGLE_TASK;
    public static final int SINGLE_INSTANCE = StackManager.SINGLE_INSTANCE;
    public static final int KEEP_CURRENT = StackManager.KEEP_CURRENT;
    private FlowBaseActivity activity;
    private boolean isClose = false;
    private boolean isFrist = false;
    private boolean isShow = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getFlowActivity().onFlowFragmentViewCreated(this, view);
        isClose = false;
        isShow = true;
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public FlowBaseFragment initArguments(Bundle extras) {
        return (FlowBaseFragment) super.initArguments(extras);
    }

    @Override
    public void initDefaultToolbar(DefaultToolbar defaultToolbar) {
        super.initDefaultToolbar(defaultToolbar);
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
        open(fragment, null, KEEP_CURRENT);
    }

    /**
     * open fragment
     *
     * @param fragment fragment
     * @param bundle   bundle
     */
    protected void open(FlowBaseFragment fragment, Bundle bundle) {
        open(fragment, null, KEEP_CURRENT);
    }

    /**
     * open fragment
     *
     * @param fragment  fragment
     * @param bundle    bundle
     * @param stackMode stackMode
     */
    protected void open(FlowBaseFragment fragment, Bundle bundle, int stackMode) {
        if (getFlowActivity() == null) {
            return;
        }
        getFlowActivity().getStackManager().addFragment(this, fragment, bundle, stackMode);
    }

    /**
     * closeSelfAndOpen
     *
     * @param fragment fragment
     */
    protected void closeSelfAndOpen(FlowBaseFragment fragment) {
        closeSelfAndOpen(fragment, null);
    }

    /**
     * closeSelfAndOpen
     *
     * @param fragment fragment
     * @param bundle   bundle
     */
    protected void closeSelfAndOpen(FlowBaseFragment fragment, Bundle bundle) {
        closeSelfAndOpen(fragment, bundle, KEEP_CURRENT);
    }

    /**
     * closeAndOpen fragment
     *
     * @param fragment  fragment
     * @param bundle    bundle
     * @param stackMode stackMode
     */
    protected void closeSelfAndOpen(FlowBaseFragment fragment, Bundle bundle, int stackMode) {
        open(fragment, bundle, stackMode);
        if (!isLast()) {
            close(this);
        }
    }

    /**
     * Jump to the specified fragment and do not hide the current page.
     *
     * @param to To jump to the page
     */
    public void dialogFragment(Fragment to, int dialog_in, int dialog_out) {
        if (getFlowActivity() == null) {
            return;
        }
        getFlowActivity().getStackManager().dialogFragment(to, dialog_in, dialog_out);
    }

    /**
     * close this current Fragment
     */
    protected void closeSelf() {
        getFlowActivity().getStackManager().close(this, true);
    }

    /**
     * Closes the specified fragment
     *
     * @param fragment the specified fragment
     */
    protected void close(FlowBaseFragment fragment) {
        if (fragment.equals(this)) {
            closeSelf();
            return;
        }
        getFlowActivity().getStackManager().close(fragment, false);
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
     * Override this method in order to facilitate the singleTop mode to be
     * called in
     */
    @Override
    public void onNewIntent() {
    }

    @Override
    public void onClosed() {
        isClose = true;
    }

    /**
     * isClosed
     *
     * @return isClose
     */
    public boolean isClosed() {
        return isClose;
    }


    /**
     * setFrist
     *
     * @param frist frist
     */
    public void setFrist(boolean frist) {
        isFrist = frist;
    }

    /**
     * isFrist
     *
     * @return isFrist
     */
    public boolean isFrist() {
        return isFrist;
    }

    /**
     * isShow
     *
     * @return isShow
     */
    public boolean isShow() {
        return isShow;
    }

    /**
     * isLast
     *
     * @return isLast
     */
    public boolean isLast() {
        if (getFlowActivity() != null && getFlowActivity().getVisibleFragment().equals(this)) {
            return true;
        }
        return false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            this.isShow = true;
        } else {
            this.isShow = false;
        }
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
