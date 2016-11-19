package com.liangmayong.base.sub;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.liangmayong.base.BaseDrawerActivity;
import com.liangmayong.base.BaseFragment;
import com.liangmayong.base.ui.fragments.DefualtWebFragment;
import com.liangmayong.base.widget.iconfont.Icon;

/**
 * Created by LiangMaYong on 2016/10/17.
 */

public abstract class BaseSubFragment extends BaseFragment {

    // isInitView
    private boolean isInitView = false;

    @Override
    protected final void initView(View rootView) {
        if (isInitView) {
            return;
        }
        if (getDefualtToolbar() != null) {
            if (getActivity() instanceof BaseSubActivity) {
                getDefualtToolbar().leftOne().iconToLeft(Icon.icon_back).clicked(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            } else if (getActivity() instanceof BaseDrawerActivity) {
                if (((BaseDrawerActivity) getActivity()).getSubFragmentManager().getFragmentCount() > 1) {
                    getDefualtToolbar().leftOne().iconToLeft(Icon.icon_back).clicked(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                } else {
                    getDefualtToolbar().leftOne().iconToLeft(Icon.icon_menu).clicked(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((BaseDrawerActivity) getActivity()).openDrawer();
                        }
                    });
                }
            }
        }
        isInitView = true;
        initSubView(rootView);
    }

    protected abstract void initSubView(View rootView);


    @Override
    public BaseSubFragment initArguments(Bundle extras) {
        return (BaseSubFragment) super.initArguments(extras);
    }

    @Override
    protected void createView() {
        super.createView();
        isInitView = false;
    }

    /**
     * onKeyDown
     *
     * @param keyCode keyCode
     * @param event   event
     * @return event flag
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }


    /**
     * onKeyUp
     *
     * @param keyCode keyCode
     * @param event   event
     * @return event flag
     */
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    /**
     * onTouchEvent
     *
     * @param event event
     * @return event flag
     */
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    /**
     * onBackPressed
     *
     * @return event flag
     */
    public boolean onBackPressed() {
        return false;
    }

    /**
     * finish
     */
    public void finish() {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubFragmentManager().closeFragment(this, 0, 0);
        } else if (getActivity() instanceof BaseDrawerActivity) {
            ((BaseDrawerActivity) getActivity()).getSubFragmentManager().closeFragment(this, 0, 0);
        } else {
            getActivity().finish();
        }
    }

    /**
     * finish
     *
     * @param popEnter popEnter
     * @param popExit  popExit
     */
    public void finish(int popEnter, int popExit) {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubFragmentManager().closeFragment(this, popEnter, popExit);
        } else if (getActivity() instanceof BaseDrawerActivity) {
            ((BaseDrawerActivity) getActivity()).getSubFragmentManager().closeFragment(this, popEnter, popExit);
        } else {
            getActivity().finish();
        }
    }


    /**
     * open
     *
     * @param fragment fragment
     */
    public void open(BaseSubFragment fragment) {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubFragmentManager().addFragment(fragment, 0, 0);
        } else if (getActivity() instanceof BaseDrawerActivity) {
            ((BaseDrawerActivity) getActivity()).getSubFragmentManager().addFragment(fragment, 0, 0);
        }
    }

    /**
     * open
     *
     * @param fragment  fragment
     * @param enterAnim enterAnim
     * @param exitAnim  exitAnim
     */
    public void open(BaseSubFragment fragment, int enterAnim, int exitAnim) {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubFragmentManager().addFragment(fragment, enterAnim, exitAnim);
        } else if (getActivity() instanceof BaseDrawerActivity) {
            ((BaseDrawerActivity) getActivity()).getSubFragmentManager().addFragment(fragment, enterAnim, exitAnim);
        }
    }

    /**
     * replace
     *
     * @param fragment  fragment
     * @param enterAnim enterAnim
     * @param exitAnim  exitAnim
     */
    public void replace(BaseSubFragment fragment, int enterAnim, int exitAnim) {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubFragmentManager().replaceFragment(fragment, enterAnim, exitAnim);
        } else if (getActivity() instanceof BaseDrawerActivity) {
            ((BaseDrawerActivity) getActivity()).getSubFragmentManager().replaceFragment(fragment, enterAnim, exitAnim);
        }
    }


    /**
     * open
     *
     * @param fragment fragment
     */
    public void replace(BaseSubFragment fragment) {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubFragmentManager().replaceFragment(fragment, 0, 0);
        } else if (getActivity() instanceof BaseDrawerActivity) {
            ((BaseDrawerActivity) getActivity()).getSubFragmentManager().replaceFragment(fragment, 0, 0);
        }
    }

    /**
     * close
     *
     * @param fragment fragment
     */
    public void close(BaseSubFragment fragment) {
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubFragmentManager().closeFragment(fragment, 0, 0);
        } else if (getActivity() instanceof BaseDrawerActivity) {
            ((BaseDrawerActivity) getActivity()).getSubFragmentManager().closeFragment(fragment, 0, 0);
        }
    }

    @Override
    public void goTo(String title, String url) {
        if (getActivity() instanceof BaseSubActivity) {
            open(new DefualtWebFragment(title, url));
        } else if (getActivity() instanceof BaseDrawerActivity) {
            open(new DefualtWebFragment(title, url));
        } else {
            super.goTo(title, url);
        }
    }
}
