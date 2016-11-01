package com.liangmayong.base.sub;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.LinkedList;

/**
 * Created by LiangMaYong on 2016/10/29.
 */

public class BaseSubFragmentManager {

    private boolean mLock = false;
    private final FragmentActivity mActivity;
    private BaseSubFragment mCurrentFragment = null;
    private LinkedList<BaseSubFragment> mFragments = new LinkedList<BaseSubFragment>();
    @IdRes
    private final int id;

    public BaseSubFragmentManager(FragmentActivity activity, int id, BaseSubFragment fristFragment) {
        this.mActivity = activity;
        this.id = id;
        setFragment(fristFragment);
    }

    public BaseSubFragment getVisibleFragment() {
        return mCurrentFragment;
    }

    public void onBackPressed() {
        if (mLock) {
            return;
        }
        int count = mFragments.size();
        if (count == 1) {
            boolean flag = mCurrentFragment.onBackPressed();
            if (!flag) {
                closeAllFragment();
                mActivity.finish();
                mCurrentFragment = null;
                return;
            } else {
                return;
            }
        } else if (count < 1) {
            closeAllFragment();
            mActivity.finish();
            mCurrentFragment = null;
            return;
        }
        boolean flag = mCurrentFragment.onBackPressed();
        if (!flag) {
            closeFragment(mCurrentFragment, 0, 0);
        }
    }

    /**
     * Set the bottom of the fragment
     *
     * @param mTargetFragment bottom of the fragment
     */
    private void setFragment(BaseSubFragment mTargetFragment) {
        if (mLock) {
            return;
        }
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(id, mTargetFragment, mTargetFragment.getClass().getName()).commit();
        mCurrentFragment = mTargetFragment;
        mFragments.add(mTargetFragment);
    }

    /**
     * Jump to the specified fragment
     *
     * @param mTargetFragment next fragment
     */
    public void addFragment(BaseSubFragment mTargetFragment, int enterAnim, int exitAnim) {
        if (mLock) {
            return;
        }
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        if (enterAnim != 0 && exitAnim != 0) {
            transaction.setCustomAnimations(enterAnim, exitAnim);
        }
        transaction.add(id, mTargetFragment, mTargetFragment.getClass().getName()).hide(mCurrentFragment).commit();
        mCurrentFragment = mTargetFragment;
        mFragments.add(mTargetFragment);
    }

    /**
     * Closes the specified fragment
     *
     * @param mTargetFragment fragment
     */
    public void closeFragment(final BaseSubFragment mTargetFragment, int popEnter, int popExit) {
        if (mLock) {
            return;
        }
        if (mFragments.contains(mTargetFragment)) {
            if (mFragments.size() <= 1) {
                closeAllFragment();
                mActivity.finish();
                mCurrentFragment = null;
                return;
            }
            FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
            mFragments.remove(mTargetFragment);
            if (mTargetFragment.equals(mCurrentFragment)) {
                mCurrentFragment = mFragments.get(mFragments.size() - 1);
                transaction.show(mCurrentFragment).commit();
                View fromVie = mTargetFragment.getView();
                if (popExit != 0 && popEnter != 0) {
                    Animation quit_Out = AnimationUtils.loadAnimation(mActivity, popExit);
                    if (fromVie != null && quit_Out != null) {
                        fromVie.startAnimation(quit_Out);
                        quit_Out.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
                                transaction.remove(mTargetFragment).commit();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                } else {
                    mActivity.getSupportFragmentManager().beginTransaction().remove(mTargetFragment).commit();
                }
                if (mCurrentFragment != null) {
                    View toView = mCurrentFragment.getView();
                    if (popEnter != 0 && popExit != 0) {
                        Animation quit_In = AnimationUtils.loadAnimation(mActivity, popEnter);
                        if (toView != null && quit_In != null) {
                            toView.startAnimation(quit_In);
                            quit_In.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    mLock = true;
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    mLock = false;
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }
                }
            } else {
                transaction.remove(mTargetFragment).commit();
            }
        }
    }

    /**
     * Close all fragment
     */
    public void closeAllFragment() {
        if (mLock) {
            return;
        }
        int backStackCount = mActivity.getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            int backStackId = mActivity.getSupportFragmentManager().getBackStackEntryAt(i).getId();
            mActivity.getSupportFragmentManager().popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
