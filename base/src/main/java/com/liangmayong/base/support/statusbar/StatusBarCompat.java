package com.liangmayong.base.support.statusbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.liangmayong.base.R;

/**
 * Created by LiangMaYong on 2016/11/8.
 */
public class StatusBarCompat {

    /**
     * compat
     *
     * @param activity    activity
     * @param statusColor statusColor
     */
    public static void compat(Activity activity, int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(statusColor);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            if (contentView != null) {
                transparentStatusBar(activity);
                FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.base_drawer_fragment);
                if (frameLayout != null) {
                    frameLayout.setPadding(0, getStatusBarHeight(activity), 0, 0);
                }
                View statusBarViewBg = contentView.findViewById(R.id.default_toolbar_status_bar);
                if (statusBarViewBg != null) {
                    statusBarViewBg.setLayoutParams(getStatusBarLayoutParams(activity, contentView));
                    statusBarViewBg.setBackgroundColor(statusColor);
                    return;
                } else {
                    for (int i = 0; i < contentView.getChildCount(); i++) {
                        View view = contentView.getChildAt(i);
                        view.setPadding(view.getPaddingLeft(), view.getTop() + getStatusBarHeight(activity), view.getPaddingRight(), view.getPaddingBottom());
                    }
                    statusBarViewBg = new View(activity);
                    statusBarViewBg.setId(R.id.default_toolbar_status_bar);
                    statusBarViewBg.setBackgroundColor(statusColor);
                    contentView.addView(statusBarViewBg, getStatusBarLayoutParams(activity, contentView));
                }
            } else {
                untransparentStatusBar(activity);
            }
        }
    }


    /**
     * setTransparent
     *
     * @param activity activity
     */
    public static void setTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        transparentStatusBar(activity);
    }

    /**
     * transparentStatusBar
     *
     * @param activity activity
     */
    private static void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * getStatusBarLayoutParams
     *
     * @param activity activity
     * @param group    group
     * @return layout params
     */
    private static ViewGroup.LayoutParams getStatusBarLayoutParams(Activity activity, ViewGroup group) {
        if (group == null) {
            return null;
        }
        if (group instanceof FrameLayout) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            return layoutParams;
        } else if (group instanceof LinearLayout) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            return layoutParams;
        } else if (group instanceof RelativeLayout) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            return layoutParams;
        } else {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            return layoutParams;
        }
    }

    /**
     * transparentStatusBar
     *
     * @param activity activity
     */
    private static void untransparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(0xff333333);
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * getStatusBarHeight
     *
     * @param context context
     * @return status bar height
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
