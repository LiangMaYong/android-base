package com.liangmayong.base.support.fixbug;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * Created by LiangMaYong on 2016/10/28.
 */
public class Android5497Workaround {

    public static void assistActivity(Activity activity) {
        new Android5497Workaround(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;
    private boolean isfirst = true;

    private Android5497Workaround(Activity activity) {
        try {
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            ViewGroup content = (ViewGroup) activity.findViewById(android.R.id.content);
            mChildOfContent = content.getChildAt(0);
            mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    if (isfirst) {
                        isfirst = false;
                    }
                    possiblyResizeChildOfContent();
                }
            });
            frameLayoutParams = (FrameLayout.LayoutParams)
                    mChildOfContent.getLayoutParams();
        } catch (Exception e) {
        }
    }

    private void possiblyResizeChildOfContent() {

        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
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
