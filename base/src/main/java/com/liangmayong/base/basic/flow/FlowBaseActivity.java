package com.liangmayong.base.basic.flow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;

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

    //stackManager
    private StackManager stackManager;

    @Override
    public StackManager getStackManager() {
        return stackManager;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generateContainerView();
        FlowBaseFragment fragment = getFirstFragment();
        if (fragment == null) {
            fragment = ErrorFragment.newInstance(ERROR_MSG_FIRST_STACK_NULL);
        } else {
            fragment.initArguments(getIntent().getExtras());
        }
        stackManager = new StackManager(this, getSupportFragmentManager(), generateContainerFragmentId());
        stackManager.setFirstFragment(fragment);
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
        setContentView(R.layout.base_default_activity_flow);
    }

    /**
     * onConfigFlowFragmentAnims
     */
    protected void onConfigFlowFragmentAnims() {
        int anim_next_in = R.anim.base_anim_in_enter;
        int anim_next_out = R.anim.base_anim_out_enter;
        int anim_quit_in = R.anim.base_anim_in_exit;
        int anim_quit_out = R.anim.base_anim_out_exit;
        setFlowFragmentAnims(anim_next_in, anim_next_out, anim_quit_in, anim_quit_out);
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
    public void setFlowFragmentAnims(int nextIn, int nextOut, int quitIn, int quitOut) {
        if (stackManager != null) {
            stackManager.setFragmentAnim(nextIn, nextOut, quitIn, quitOut);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getLastFragment() != null) {
            boolean flag = getLastFragment().onTouchEvent(event);
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
        if (getLastFragment() != null) {
            return getLastFragment().onKeyUp(keyCode, event);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public final boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (getLastFragment() != null) {
                    boolean flag = getLastFragment().onKeyDown(keyCode, event);
                    if (!flag) {
                        if (stackManager != null) {
                            stackManager.onBackPressed();
                        } else {
                            return super.onKeyDown(keyCode, event);
                        }
                    }
                } else {
                    if (stackManager != null) {
                        stackManager.onBackPressed();
                    } else {
                        return super.onKeyDown(keyCode, event);
                    }
                }
                return true;
            default:
                if (getLastFragment() != null) {
                    return getLastFragment().onKeyDown(keyCode, event);
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
     * getTheme visible fragment
     *
     * @return visible fragment
     */
    @Override
    public final FlowBaseFragment getLastFragment() {
        if (stackManager != null) {
            return stackManager.getVisibleFragment();
        }
        return null;
    }


}
