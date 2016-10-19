package com.liangmayong.base.sub;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.liangmayong.base.BaseActivity;
import com.liangmayong.base.R;
import com.liangmayong.base.widget.iconfont.Icon;

import java.util.LinkedList;


/**
 * Created by LiangMaYong on 2016/10/17.
 */
public abstract class BaseSubActivity extends BaseActivity {
    //mWatermark
    private static String mWatermark = "";

    /**
     * setWatermarkText
     *
     * @param watermark watermark
     */
    public static void setWatermarkText(String watermark) {
        BaseSubActivity.mWatermark = mWatermark;
    }

    //mFrameView
    private BaseSubFragmentManager mSubManager;

    //getSubManager
    public BaseSubFragmentManager getSubManager() {
        return mSubManager;
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generateContentView();
        BaseSubFragment fragment = generateSubFragment();
        if (fragment != null) {
            mSubManager = new BaseSubFragmentManager(this, generateFragmentId(), fragment);
            mSubManager.setAnim(R.anim.anim_next_in, R.anim.anim_next_out, R.anim.anim_quit_in, R.anim.anim_quit_out);
        } else {
            throw new IllegalArgumentException("generateSubFragment return can't is NULL");
        }
        if (getDefualtToolbar() != null) {
            getDefualtToolbar().leftOne().iconToLeft(Icon.icon_back).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    public abstract BaseSubFragment generateSubFragment();

    /**
     * generateFragmentId
     *
     * @return id
     */
    protected int generateFragmentId() {
        return R.id.base_sub_fragment_frame;
    }

    /**
     * generateInitView
     */
    protected void generateContentView() {
        setContentView(R.layout.base_defualt_sub_activity);
        TextView base_sub_watermark = (TextView) findViewById(R.id.base_sub_watermark);
        base_sub_watermark.setText(mWatermark);
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
                        if (mSubManager != null) {
                            mSubManager.onBackPressed();
                        } else {
                            return super.onKeyDown(keyCode, event);
                        }
                    }
                } else {
                    if (mSubManager != null) {
                        mSubManager.onBackPressed();
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
     * get visible fragment
     *
     * @return visible fragment
     */
    public final BaseSubFragment getVisibleFragment() {
        if (mSubManager != null) {
            return mSubManager.getVisibleFragment();
        }
        return null;
    }

    /**
     * BaseSubFragmentManager
     */
    public static class BaseSubFragmentManager {

        private boolean mLock = false;
        private final FragmentActivity mActivity;
        private BaseSubFragment mCurrentFragment = null;
        private LinkedList<BaseSubFragment> mFragments = new LinkedList<BaseSubFragment>();
        private int nextIn;
        private int nextOut;
        private Animation quit_In, quit_Out;
        @IdRes
        private int id;

        private BaseSubFragmentManager(FragmentActivity activity, int id, BaseSubFragment fristFragment) {
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
                closeFragment(mCurrentFragment);
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
        public void addFragment(BaseSubFragment mTargetFragment) {
            if (mLock) {
                return;
            }
            FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
            if (nextIn != 0 && nextOut != 0) {
                transaction.setCustomAnimations(nextIn, nextOut);
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
        public void closeFragment(final BaseSubFragment mTargetFragment) {
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
                    } else {
                        mActivity.getSupportFragmentManager().beginTransaction().remove(mTargetFragment).commit();
                    }
                    if (mCurrentFragment != null) {
                        View toView = mCurrentFragment.getView();
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
                } else {
                    transaction.remove(mTargetFragment).commit();
                }
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
            quit_In = AnimationUtils.loadAnimation(mActivity, quitIn);
            quit_Out = AnimationUtils.loadAnimation(mActivity, quitOut);
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
}
