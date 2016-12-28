package com.liangmayong.base.sub;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.liangmayong.base.BaseDrawerActivity;
import com.liangmayong.base.BaseFragment;
import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.web.fragments.DefaultWebFragment;
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
        isInitView = true;
        initSubView(rootView);
    }

    @Override
    protected void initToolbar() {
        if (getDefaultToolbar() != null) {
            getDefaultToolbar().reset();
            if (getActivity() instanceof BaseSubActivity) {
                getDefaultToolbar().leftOne().iconToLeft(Icon.icon_back).clicked(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            } else if (getActivity() instanceof BaseDrawerActivity) {
                if (((BaseDrawerActivity) getActivity()).getSubFragmentManager().getFragmentCount() > 1) {
                    getDefaultToolbar().leftOne().iconToLeft(Icon.icon_back).clicked(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                } else {
                    getDefaultToolbar().leftOne().iconToLeft(Icon.icon_menu).clicked(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((BaseDrawerActivity) getActivity()).openDrawer();
                        }
                    });
                }
            }
        }
    }

    protected abstract void initSubView(View rootView);


    @Override
    public BaseSubFragment initArguments(Bundle extras) {
        return (BaseSubFragment) super.initArguments(extras);
    }

    @Override
    protected void onCreateView() {
        super.onCreateView();
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
     * openFragment
     *
     * @param fragment fragment
     */
    public void openFragment(BaseSubFragment fragment) {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubFragmentManager().addFragment(fragment, 0, 0);
        } else if (getActivity() instanceof BaseDrawerActivity) {
            ((BaseDrawerActivity) getActivity()).getSubFragmentManager().addFragment(fragment, 0, 0);
        }
    }

    /**
     * openFragment
     *
     * @param fragment  fragment
     * @param enterAnim enterAnim
     * @param exitAnim  exitAnim
     */
    public void openFragment(BaseSubFragment fragment, int enterAnim, int exitAnim) {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubFragmentManager().addFragment(fragment, enterAnim, exitAnim);
        } else if (getActivity() instanceof BaseDrawerActivity) {
            ((BaseDrawerActivity) getActivity()).getSubFragmentManager().addFragment(fragment, enterAnim, exitAnim);
        }
    }

    /**
     * replaceFragment
     *
     * @param fragment  fragment
     * @param enterAnim enterAnim
     * @param exitAnim  exitAnim
     */
    public void replaceFragment(BaseSubFragment fragment, int enterAnim, int exitAnim) {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubFragmentManager().replaceFragment(fragment, enterAnim, exitAnim);
        } else if (getActivity() instanceof BaseDrawerActivity) {
            ((BaseDrawerActivity) getActivity()).getSubFragmentManager().replaceFragment(fragment, enterAnim, exitAnim);
        }
    }

    /**
     * openFragment
     *
     * @param fragment fragment
     */
    public void replaceFragment(BaseSubFragment fragment) {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubFragmentManager().replaceFragment(fragment, 0, 0);
        } else if (getActivity() instanceof BaseDrawerActivity) {
            ((BaseDrawerActivity) getActivity()).getSubFragmentManager().replaceFragment(fragment, 0, 0);
        }
    }

    /**
     * closeFragment
     *
     * @param fragment fragment
     */
    public void closeFragment(BaseSubFragment fragment) {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubFragmentManager().closeFragment(fragment, 0, 0);
        } else if (getActivity() instanceof BaseDrawerActivity) {
            ((BaseDrawerActivity) getActivity()).getSubFragmentManager().closeFragment(fragment, 0, 0);
        }
    }

    @Override
    public void goTo(String title, String url) {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            openFragment(new DefaultWebFragment(title, url));
        } else if (getActivity() instanceof BaseDrawerActivity) {
            openFragment(new DefaultWebFragment(title, url));
        } else {
            super.goTo(title, url);
        }
    }

    @Override
    public void goToWeb(String title, String url) {
        if (getActivity() instanceof BaseSubActivity) {
            openFragment(new DefaultWebFragment(title, url, true));
        } else if (getActivity() instanceof BaseDrawerActivity) {
            openFragment(new DefaultWebFragment(title, url, true));
        } else {
            super.goTo(title, url);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
