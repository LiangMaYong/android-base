package com.liangmayong.base.sub;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.liangmayong.base.BaseActivity;
import com.liangmayong.base.R;
import com.liangmayong.base.widget.iconfont.Icon;


/**
 * Created by LiangMaYong on 2016/10/17.
 */
public abstract class BaseSubActivity extends BaseActivity {

    //mFrameViewgradlew
    private BaseSubFragmentManager mSubManager;

    //getSubFragmentManager
    public BaseSubFragmentManager getSubFragmentManager() {
        return mSubManager;
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generateContainerView();
        BaseSubFragment fragment = generateSubFragment();
        if (fragment != null) {
            mSubManager = new BaseSubFragmentManager(this, generateFragmentContainerId(), fragment);
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
        onCreateActivity(savedInstanceState);
    }

    protected void onCreateActivity(Bundle savedInstanceState) {
    }

    public abstract BaseSubFragment generateSubFragment();

    /**
     * generateFragmentContainerId
     *
     * @return id
     */
    protected int generateFragmentContainerId() {
        return R.id.base_sub_fragment;
    }

    /**
     * generateInitView
     */
    protected void generateContainerView() {
        setContentView(R.layout.base_defualt_sub_activity);
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

}
