package com.liangmayong.base.sub;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.liangmayong.base.BaseActivity;
import com.liangmayong.base.R;
import com.liangmayong.base.widget.iconfont.Icon;


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
        BaseSubActivity.mWatermark = watermark;
    }

    //mFrameViewgradlew
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

    protected String generateWatermarkText() {
        return null;
    }

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
        String water = generateWatermarkText();
        if (water == null) {
            base_sub_watermark.setText(mWatermark);
        } else {
            base_sub_watermark.setText(water);
        }
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
