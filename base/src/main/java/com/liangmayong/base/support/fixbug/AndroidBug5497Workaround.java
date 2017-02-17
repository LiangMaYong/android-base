package com.liangmayong.base.support.fixbug;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by LiangMaYong on 2016/10/28.
 */
public class AndroidBug5497Workaround {

    public interface OnSoftKeyboardListener {
        void onSoftKeyboardStateChange(boolean visible);
    }

    /**
     * assistActivity
     *
     * @param activity activity
     */
    public static void assistActivity(Activity activity, OnSoftKeyboardListener softKeyboardListener) {
        View content = activity.findViewById(android.R.id.content);
        assistView(content, softKeyboardListener);
    }


    /**
     * assistActivity
     *
     * @param view view
     */
    public static void assistView(View view, OnSoftKeyboardListener softKeyboardListener) {
        if (view instanceof ViewGroup) {
            new AndroidBug5497Workaround((ViewGroup) view, softKeyboardListener);
        }
    }


    /**
     * unassistView
     *
     * @param view view
     */
    public static void unassistView(View view) {
        if (view instanceof ViewGroup) {
            try {
                View mChildOfContent = ((ViewGroup) view).getChildAt(0);
                mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(null);
            } catch (Exception e) {
            }
        }
    }

    /**
     * unassistActivity
     *
     * @param activity activity
     */
    public static void unassistActivity(Activity activity) {
        try {
            View content = activity.findViewById(android.R.id.content);
            unassistView(content);
        } catch (Exception e) {
        }
    }

    private View mContent;
    private View mChildOfContent;
    private int usableHeightPrevious;
    private ViewGroup.LayoutParams frameLayoutParams;
    private OnSoftKeyboardListener mSoftKeyboardListener;
    private boolean mShowSoftKeyboard = false;

    private AndroidBug5497Workaround(ViewGroup view, OnSoftKeyboardListener softKeyboardListener) {
        try {
            mContent = view;
            mSoftKeyboardListener = softKeyboardListener;
            mChildOfContent = view.getChildAt(0);
            mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    possiblyResizeChildOfContent();
                }
            });
            frameLayoutParams = mChildOfContent.getLayoutParams();
        } catch (Exception e) {
        }
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        boolean isChange = false;
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mContent.getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;

            if (heightDifference > (usableHeightSansKeyboard / 4)) {//modify by chengr
//              keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                if (!mShowSoftKeyboard) {
                    mShowSoftKeyboard = true;
                    isChange = true;
                }
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard;
                if (mShowSoftKeyboard) {
                    mShowSoftKeyboard = false;
                    isChange = true;
                }
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
        if (isChange) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mSoftKeyboardListener != null) {
                        mSoftKeyboardListener.onSoftKeyboardStateChange(mShowSoftKeyboard);
                    }
                }
            }, 200);
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

}
