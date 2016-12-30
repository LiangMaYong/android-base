package com.liangmayong.base.basic.flow.stack;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.basic.flow.interfaces.IClose;

import java.util.ArrayList;
import java.util.List;

/**
 * StackManager
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class StackManager implements IClose {

    public static final int STANDARD = 0x11;
    public static final int SINGLE_TOP = 0x12;
    public static final int SINGLE_TASK = 0x13;
    public static final int SINGLE_INSTANCE = 0x14;
    public static final int KEEP_CURRENT = 0x15;
    private Stack stack;
    private final FragmentActivity context;
    private int currentMode;
    private int nextIn;
    private int nextOut;
    private Animation quit_In, quit_Out;
    private int dialogCount = 0;
    private List<Fragment> dialogFragments = new ArrayList<Fragment>();
    private int id = 0;

    public boolean isShowDialog() {
        return dialogCount > 0;
    }

    public StackManager(FragmentActivity context, int id) {
        stack = new Stack();
        stack.setClose(this);
        this.context = context;
        this.id = id;
    }

    /**
     * Set the bottom of the fragment
     *
     * @param mTargetFragment bottom of the fragment
     */
    public void setFragment(FlowBaseFragment mTargetFragment) {
        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(id, mTargetFragment, mTargetFragment.getClass().getName()).commit();
        stack.putStandard(mTargetFragment);
    }

    /**
     * Jump to the specified fragment
     *
     * @param from current fragment
     * @param to   next fragment
     */
    public void addFragment(final Fragment from, final Fragment to) {
        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        if (nextIn != 0 && nextOut != 0) {
            transaction.setCustomAnimations(nextIn, nextOut);
            transaction.add(id, to, to.getClass().getName()).hide(from).commit();
        } else {
            transaction.add(id, to, to.getClass().getName()).hide(from).commit();
        }
    }

    /**
     * Set page switch animation
     *
     * @param nextIn  The next page to enter the animation
     * @param nextOut The next page out of the animation
     * @param quitIn  The current page into the animation
     * @param quitOut Exit animation for the current page
     */
    public void setAnim(int nextIn, int nextOut, int quitIn, int quitOut) {
        this.nextIn = nextIn;
        this.nextOut = nextOut;
        quit_In = AnimationUtils.loadAnimation(context, quitIn);
        quit_Out = AnimationUtils.loadAnimation(context, quitOut);
    }

    /**
     * Jump to the specified fragment
     *
     * @param from      current fragment
     * @param to        next fragment
     * @param bundle    Parameter carrier
     * @param stackMode fragment stack Mode
     */
    public void addFragment(FlowBaseFragment from, FlowBaseFragment to, Bundle bundle, @StackMode int stackMode) {
        if (stackMode != KEEP_CURRENT) {
            currentMode = stackMode;
        }
        if (bundle != null) {
            to.setArguments(bundle);
        }
        switch (currentMode) {
            case SINGLE_TOP:
                if (!stack.putSingleTop(to)) {
                    addFragment(from, to);
                }
                break;
            case SINGLE_TASK:
                if (!stack.putSingleTask(to)) {
                    addFragment(from, to);
                }
                break;
            case SINGLE_INSTANCE:
                stack.putSingleInstance(to);
                addFragment(from, to);
                break;
            default:
                stack.putStandard(to);
                addFragment(from, to);
                break;
        }
    }

    /**
     * open this fragment
     *
     * @param from current fragment
     * @param to   next fragment
     */
    public void openFragment(FlowBaseFragment from, FlowBaseFragment to) {
        addFragment(from, to, null, KEEP_CURRENT);
    }

    /**
     * Jump to the specified fragment with a parameter form
     *
     * @param from   current fragment
     * @param to     next fragment
     * @param bundle Parameter carrier
     */
    public void addFragment(FlowBaseFragment from, FlowBaseFragment to, Bundle bundle) {
        addFragment(from, to, bundle, KEEP_CURRENT);
    }

    /**
     * Jump to the specified fragment and do not hide the current page.
     *
     * @param to To jump to the page
     */
    public void dialogFragment(Fragment to, int dialog_in, int dialog_out) {
        dialogCount++;
        dialogFragments.add(to);
        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            if (dialog_in != 0 && dialog_out != 0) {
                transaction.setCustomAnimations(dialog_in, dialog_out)
                        .add(id, to, to.getClass().getName()).commit();
            } else {
                transaction.add(id, to, to.getClass().getName()).commit();
            }
        }
    }

    /**
     * Closes the specified fragment
     *
     * @param mTargetFragment fragment
     */
    public void closeFragment(Fragment mTargetFragment) {
        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        transaction.remove(mTargetFragment).commit();
    }

    /**
     * Close the specified fragment by tag
     *
     * @param tag fragment tag
     */
    public void closeFragment(String tag) {
        FlowBaseFragment fragmentByTag = (FlowBaseFragment) context.getSupportFragmentManager().findFragmentByTag(tag);
        if (fragmentByTag != null) {
            closeFragment(fragmentByTag);
            context.getSupportFragmentManager().popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void close() {
        context.getSupportFragmentManager().popBackStack();
    }

    /**
     * Close all fragment
     */
    public void closeAllFragment() {
        int backStackCount = context.getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            int backStackId = context.getSupportFragmentManager().getBackStackEntryAt(i).getId();
            context.getSupportFragmentManager().popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void onBackPressed() {
        if (!dialogFragments.isEmpty()) {
            dialogCount--;
            closeFragment(dialogFragments.get(dialogCount));
            dialogFragments.remove(dialogCount);
            return;
        }
        Fragment[] last = stack.getLast();
        final FlowBaseFragment from = (FlowBaseFragment) last[0];
        FlowBaseFragment to = (FlowBaseFragment) last[1];

        if (to == null || !to.isAdded()) {
            closeAllFragment();
            context.finish();
            return;
        }
        if (from != null) {
            from.onClosed();
            if (to != null) {
                FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                transaction.show(to).commit();
            }
            View fromVie = from.getView();
            if (fromVie != null && quit_Out != null) {
                fromVie.startAnimation(quit_Out);
                quit_Out.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        stack.onBackPressed();
                        closeFragment(from);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                stack.onBackPressed();
                closeFragment(from);
            }
        }
        if (to != null) {
            View toView = to.getView();
            if (toView != null && quit_In != null) {
                toView.startAnimation(quit_In);
            }
        }
    }

    public static boolean isFirstClose = false;

    @Override
    public void close(final FlowBaseFragment fragment, boolean back) {
        if (back && fragment.isShow() && !dialogFragments.contains(fragment)) {
            onBackPressed();
            return;
        }
        if (isFirstClose) {
            View view = fragment.getView();
            if (view != null) {
                if (quit_Out != null) {
                    view.startAnimation(quit_Out);
                    quit_Out.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            fragment.onClosed();
                            stack.closeFragment(fragment);
                            closeFragment(fragment);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                } else {
                    fragment.onClosed();
                    stack.closeFragment(fragment);
                    closeFragment(fragment);
                }
            }
            isFirstClose = false;
        } else {
            fragment.onClosed();
            stack.closeFragment(fragment);
            closeFragment(fragment);
        }
    }

    @Override
    public void show(FlowBaseFragment fragment) {
        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        transaction.show(fragment).commit();
        View view = fragment.getView();
        if (view != null && quit_In != null) {
            view.startAnimation(quit_In);
        }
    }

    @IntDef({STANDARD, SINGLE_TOP, SINGLE_TASK, SINGLE_INSTANCE, KEEP_CURRENT})
    public @interface StackMode {
    }

    /**
     * get visible fragment
     *
     * @return visible fragment
     */
    public final FlowBaseFragment getFristFragment() {
        return (FlowBaseFragment) stack.getFrist();
    }

    /**
     * get visible fragment
     *
     * @return visible fragment
     */
    public final FlowBaseFragment getVisibleFragment() {
        if (!isShowDialog()) {
            Fragment[] last = stack.getLast();
            final FlowBaseFragment from = (FlowBaseFragment) last[0];
            return from;
        }
        return null;
    }
}
