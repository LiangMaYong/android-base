package com.liangmayong.base.basic.flow.stack;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.liangmayong.base.basic.flow.FlowBaseFragment;

/**
 * StackManager
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class StackManager {

    public static final int STACK_STANDARD = 0x11;
    public static final int STACK_SINGLE_INSTANCE = 0x12;
    private final Activity activity;
    private final FragmentManager fragmentManager;
    private final FragmentStack stack;
    private final int fragment_id;
    // anim
    private int nextIn;
    private int nextOut;
    private Animation quitInAnimation, quitOutAnimation;

    public StackManager(Activity activity, FragmentManager manager, int fragment_id) {
        this.stack = new FragmentStack();
        this.activity = activity;
        this.fragmentManager = manager;
        this.fragment_id = fragment_id;
    }
    /////////////////////////////////////////////////////////////////////////////
    ///////// public
    /////////////////////////////////////////////////////////////////////////////

    /**
     * Set the bottom of the fragment
     *
     * @param mTargetFragment bottom of the fragment
     */
    public void setFirstFragment(FlowBaseFragment mTargetFragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(fragment_id, mTargetFragment, mTargetFragment.getClass().getName()).commit();
        stack.putStandard(mTargetFragment);
    }

    /**
     * Set page switch animation
     *
     * @param nextIn  The next page to enter the animation
     * @param nextOut The next page out of the animation
     * @param quitIn  The current page into the animation
     * @param quitOut Exit animation for the current page
     */
    public void setFragmentAnim(int nextIn, int nextOut, int quitIn, int quitOut) {
        this.nextIn = nextIn;
        this.nextOut = nextOut;
        quitInAnimation = AnimationUtils.loadAnimation(activity, quitIn);
        quitOutAnimation = AnimationUtils.loadAnimation(activity, quitOut);
    }

    /**
     * closeFragment
     *
     * @param fragment fragment
     */
    public void closeFragment(final FlowBaseFragment fragment) {
        if (getVisibleFragment().equals(fragment)) {
            onBackPressed();
            return;
        }
        stack.closeFragment(fragment);
        realCloseFragment(fragment);
    }

    /**
     * Jump to the specified fragment
     *
     * @param from      current fragment
     * @param to        next fragment
     * @param bundle    Parameter carrier
     * @param stackMode fragment stack Mode
     */
    public void openFragment(FlowBaseFragment from, FlowBaseFragment to, Bundle bundle, @StackMode int stackMode) {
        if (bundle != null) {
            to.setArguments(bundle);
        }
        switch (stackMode) {
            case STACK_SINGLE_INSTANCE:
                stack.putSingleInstance(to);
                realOpenFragment(from, to);
                break;
            default:
                stack.putStandard(to);
                realOpenFragment(from, to);
                break;
        }
    }

    /**
     * onBackPressed
     */
    public void onBackPressed() {
        Fragment[] last = stack.getLast();
        final FlowBaseFragment from = (FlowBaseFragment) last[0];
        FlowBaseFragment to = (FlowBaseFragment) last[1];

        if (to == null || !to.isAdded()) {
            realCloseAllFragment();
            activity.finish();
            return;
        }
        if (from != null) {
            if (to != null) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.show(to).commit();
            }
            View fromVie = from.getView();
            if (fromVie != null && quitOutAnimation != null) {
                fromVie.startAnimation(quitOutAnimation);
                quitOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        stack.onBackPressed();
                        realCloseFragment(from);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                stack.onBackPressed();
                realCloseFragment(from);
            }
        }
        if (to != null) {
            View toView = to.getView();
            if (toView != null && quitInAnimation != null) {
                toView.startAnimation(quitInAnimation);
            }
        }
    }

    /**
     * getTheme visible fragment
     *
     * @return visible fragment
     */
    public FlowBaseFragment getVisibleFragment() {
        Fragment[] last = stack.getLast();
        final FlowBaseFragment from = (FlowBaseFragment) last[0];
        return from;
    }

    @IntDef({STACK_STANDARD, STACK_SINGLE_INSTANCE})
    public @interface StackMode {
    }

    /////////////////////////////////////////////////////////////////////////////
    ///////// private
    /////////////////////////////////////////////////////////////////////////////

    /**
     * Jump to the specified fragment
     *
     * @param from current fragment
     * @param to   next fragment
     */
    private void realOpenFragment(final Fragment from, final Fragment to) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (nextIn != 0 && nextOut != 0) {
            transaction.setCustomAnimations(nextIn, nextOut);
            transaction.add(fragment_id, to, to.getClass().getName()).hide(from).commit();
        } else {
            transaction.add(fragment_id, to, to.getClass().getName()).hide(from).commit();
        }
    }

    /**
     * Closes the specified fragment
     *
     * @param mTargetFragment fragment
     */
    private void realCloseFragment(Fragment mTargetFragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(mTargetFragment).commit();
    }

    /**
     * Close all fragment
     */
    private void realCloseAllFragment() {
        int backStackCount = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            int backStackId = fragmentManager.getBackStackEntryAt(i).getId();
            fragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
