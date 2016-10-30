package com.liangmayong.base.sub;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.liangmayong.base.BaseFragment;
import com.liangmayong.base.fragments.DefualtWebFragment;
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
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubManager().closeFragment(this);
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
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubManager().addFragment(fragment);
        }
    }

    /**
     * open
     *
     * @param fragment fragment
     * @param extras   extras
     */
    public void open(BaseSubFragment fragment, Bundle extras) {
        fragment.setArguments(extras);
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubManager().addFragment(fragment);
        }
    }

    /**
     * close
     *
     * @param fragment fragment
     */
    public void close(BaseSubFragment fragment) {
        if (getActivity() instanceof BaseSubActivity) {
            ((BaseSubActivity) getActivity()).getSubManager().closeFragment(fragment);
        }
    }

    @Override
    public void goTo(String title, String url) {
        open(new DefualtWebFragment(title, url));
    }
}
