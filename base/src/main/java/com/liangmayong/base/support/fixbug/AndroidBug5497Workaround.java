package com.liangmayong.base.support.fixbug;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by LiangMaYong on 2016/10/28.
 */
public class AndroidBug5497Workaround {

    /**
     * assistActivity
     *
     * @param activity activity
     */
    public static void assistActivity(Activity activity) {
        View content = activity.findViewById(android.R.id.content);
        assistView(content);
    }


    /**
     * assistActivity
     *
     * @param view view
     */
    public static void assistView(View view) {
        if (view instanceof ViewGroup) {
            new AndroidBug5497Workaround((ViewGroup) view);
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

    private AndroidBug5497Workaround(ViewGroup view) {
        try {
            mContent = view;
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
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mContent.getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {//modify by chengr
//              keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

}
