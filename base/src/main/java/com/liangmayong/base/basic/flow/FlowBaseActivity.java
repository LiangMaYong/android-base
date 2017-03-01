package com.liangmayong.base.basic.flow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.liangmayong.base.R;
import com.liangmayong.base.basic.BaseActivity;
import com.liangmayong.base.basic.flow.fragments.ErrorFragment;
import com.liangmayong.base.basic.flow.interfaces.IFlow;
import com.liangmayong.base.basic.flow.stack.StackManager;

/**
 * FlowBaseActivity
 *
 * @author LiangMaYong
 * @version 1.0
 */
public abstract class FlowBaseActivity extends BaseActivity implements IFlow {

    // ERROR_MSG_FIRST_STACK_NULL
    private static final String ERROR_MSG_FIRST_STACK_NULL = "First fragment can't is null\nby Android-Base";

    //manager
    private StackManager manager;

    @Override
    public StackManager getStackManager() {
        return manager;
    }

    @Override
    public void onFlowFragmentViewCreated(FlowBaseFragment fragment, View view) {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generateContainerView();
    }

    @Override
    protected void onCreateAbstract(@Nullable Bundle savedInstanceState) {
        FlowBaseFragment fragment = getFirstFragment();
        if (fragment == null) {
            fragment = ErrorFragment.newInstance(ERROR_MSG_FIRST_STACK_NULL);
        }
        fragment.setFrist(true);
        fragment.initArguments(getIntent().getExtras());
        manager = new StackManager(this, generateContainerFragmentId());
        manager.setFragment(fragment);
        onConfigFlowFragmentAnims();
    }

    /**
     * generateContainerFragmentId
     *
     * @return fragment id
     */
    protected int generateContainerFragmentId() {
        return R.id.base_default_flow_frame;
    }

    /**
     * generateContainerView
     */
    protected void generateContainerView() {
        setContentView(R.layout.base_default_flow_activity);
    }

    /**
     * onConfigFlowFragmentAnims
     */
    protected void onConfigFlowFragmentAnims() {
        int anim_next_in = R.anim.base_anim_in_enter;
        int anim_next_out = R.anim.base_anim_out_enter;
        int anim_quit_in = R.anim.base_anim_in_exit;
        int anim_quit_out = R.anim.base_anim_out_exit;
        setStackFragmentAnims(anim_next_in, anim_next_out, anim_quit_in, anim_quit_out);
    }

    /**
     * Set page switch animation
     *
     * @param nextIn  The next page to enter the animation
     * @param nextOut The next page out of the animation
     * @param quitIn  The current page into the animation
     * @param quitOut Exit animation for the current page
     */
    @Override
    public void setStackFragmentAnims(int nextIn, int nextOut, int quitIn, int quitOut) {
        if (manager != null) {
            manager.setAnim(nextIn, nextOut, quitIn, quitOut);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getVisibleFragment() != null) {
            boolean flag = getVisibleFragment().onTouchEvent(event);
            if (flag) {
                return true;
            } else {
                return super.onTouchEvent(event);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (getVisibleFragment() != null) {
            return getVisibleFragment().onKeyUp(keyCode, event);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public final boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (getVisibleFragment() != null) {
                    boolean flag = getVisibleFragment().onKeyDown(keyCode, event);
                    if (!flag) {
                        if (manager != null) {
                            manager.onBackPressed();
                        } else {
                            return super.onKeyDown(keyCode, event);
                        }
                    }
                } else {
                    if (manager != null) {
                        manager.onBackPressed();
                    } else {
                        return super.onKeyDown(keyCode, event);
                    }
                }
                return true;
            default:
                if (getVisibleFragment() != null) {
                    return getVisibleFragment().onKeyDown(keyCode, event);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * getFirstFragment
     *
     * @return ftist fragment
     */
    protected abstract FlowBaseFragment getFirstFragment();

    /**
     * get visible fragment
     *
     * @return visible fragment
     */
    @Override
    public final FlowBaseFragment getVisibleFragment() {
        if (manager != null) {
            return manager.getVisibleFragment();
        }
        return null;
    }

}
