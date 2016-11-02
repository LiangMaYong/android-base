package com.liangmayong.base.sub;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

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
            getDefualtToolbar().leftOne().iconToLeft(Icon.icon_back).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
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
    protected boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }


    /**
     * onKeyUp
     *
     * @param keyCode keyCode
     * @param event   event
     * @return event flag
     */
    protected boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    /**
     * onTouchEvent
     *
     * @param event event
     * @return event flag
     */
    protected boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    /**
     * onBackPressed
     *
     * @return event flag
     */
    protected boolean onBackPressed() {
        return false;
    }

    /**
     * finish
     */
    public void finish() {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubManager().closeFragment(this, 0, 0);
        } else {
            getActivity().finish();
        }
    }

    /**
     * finish
     */
    public void finish(int popEnter, int popExit) {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubManager().closeFragment(this, popEnter, popExit);
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
            ((BaseSubActivity) getActivity()).getSubManager().addFragment(fragment, 0, 0);
        }
    }

    /**
     * open
     *
     * @param fragment fragment
     */
    public void open(BaseSubFragment fragment, int enterAnim, int exitAnim) {
        hideSoftKeyBoard();
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubManager().addFragment(fragment, enterAnim, exitAnim);
        }
    }

    /**
     * close
     *
     * @param fragment fragment
     */
    public void close(BaseSubFragment fragment) {
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubManager().closeFragment(fragment, 0, 0);
        }
    }

    @Override
    public void goTo(String title, String url) {
        open(new DefualtWebFragment(title, url));
    }
}
