package com.liangmayong.base.support.statusbar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.liangmayong.base.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        int r = Color.red(statusColor);
        int g = Color.green(statusColor);
        int b = Color.blue(statusColor);
        double grayLevel = r * 0.299 + g * 0.587 + b * 0.114;
        if (grayLevel >= 192) {
            compat(activity, statusColor, 0xff000000, true);
        } else {
            compat(activity, statusColor, 0xff000000, false);
        }
    }

    /**
     * compat
     *
     * @param activity    activity
     * @param statusColor statusColor
     * @param navColor    navColor
     * @param lightMode   lightMode
     */
    public static void compat(Activity activity, int statusColor, int navColor, boolean lightMode) {
        if (lightMode) {
            lightMode(activity);
        } else {
            clearLightMode(activity);
        }
        untransparentStatusBar(activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup content = (ViewGroup) activity.findViewById(android.R.id.content);
            if (content != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) content.getLayoutParams();
                layoutParams.weight = 1;
                layoutParams.height = 0;
                layoutParams.topMargin = 0;
                content.setLayoutParams(layoutParams);
                ViewGroup contentView = (ViewGroup) content.getParent();
                if (contentView instanceof LinearLayout) {
                    transparentStatusBar(activity);
                    View statusBarView = activity.findViewById(R.id.base_default_status_bar);
                    if (statusBarView != null) {
                        statusBarView.setLayoutParams(getStatusBarLayoutParams(activity, contentView));
                        statusBarView.setBackgroundColor(statusColor);
                        return;
                    } else {
                        statusBarView = new View(activity);
                        statusBarView.setId(R.id.base_default_status_bar);
                        statusBarView.setBackgroundColor(statusColor);
                        contentView.addView(statusBarView, 0, getStatusBarLayoutParams(activity, contentView));
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getNavBarHeight(activity) > 0) {
                        View navBarView = activity.findViewById(R.id.base_default_navigation);
                        if (navBarView != null) {
                            navBarView.setLayoutParams(getStatusBarLayoutParams(activity, contentView));
                            navBarView.setBackgroundColor(statusColor);
                            return;
                        } else {
                            navBarView = new View(activity);
                            navBarView.setId(R.id.base_default_navigation);
                            navBarView.setBackgroundColor(navColor);
                            contentView.addView(navBarView, getNavBarLayoutParams(activity, contentView));
                        }
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        activity.getWindow().setStatusBarColor(statusColor);
                        return;
                    }
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    activity.getWindow().setStatusBarColor(statusColor);
                    return;
                }
            }
        }
    }


    /**
     * lightMode
     *
     * @param activity activity
     * @return result
     */
    public static int lightMode(Activity activity) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = 3;
            } else if (MIUISetStatusBarLightMode(activity.getWindow(), true)) {
                result = 1;
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                result = 2;
            }
        }
        return result;
    }

    /**
     * clearLightMode
     *
     * @param activity activity
     * @return result
     */
    public static int clearLightMode(Activity activity) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                result = 3;
            } else if (MIUISetStatusBarLightMode(activity.getWindow(), false)) {
                result = 1;
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), false)) {
                result = 2;
            }
        }
        return result;
    }

    /**
     * FlymeSetStatusBarLightMode
     *
     * @param window window
     * @param dark   dark
     * @return boolean
     */
    private static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * FlymeSetStatusBarLightMode
     *
     * @param window window
     * @param dark   dark
     * @return boolean
     */
    private static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
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
        int height = getStatusBarHeight(activity);
        if (group instanceof FrameLayout) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    height);
            return layoutParams;
        } else if (group instanceof LinearLayout) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    height);
            return layoutParams;
        } else if (group instanceof RelativeLayout) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    height);
            return layoutParams;
        } else {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    height);
            return layoutParams;
        }
    }

    /**
     * getStatusBarLayoutParams
     *
     * @param activity activity
     * @param group    group
     * @return layout params
     */
    private static ViewGroup.LayoutParams getNavBarLayoutParams(Activity activity, ViewGroup group) {
        if (group == null) {
            return null;
        }
        int height = getNavBarHeight(activity);
        if (group instanceof FrameLayout) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    height);
            return layoutParams;
        } else if (group instanceof LinearLayout) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    height);
            return layoutParams;
        } else if (group instanceof RelativeLayout) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    height);
            return layoutParams;
        } else {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    height);
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
    public static int getStatusBarHeight(Activity context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * getNavBarHeight
     *
     * @param context context
     * @return status bar height
     */
    public static int getNavBarHeight(Activity context) {
        if (hasNavigationBar(context)) {
            int result = 0;
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }
        return 0;
    }

    /**
     * hasNavigationBar
     *
     * @param context context
     * @return flag
     */
    public static boolean hasNavigationBar(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = context.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        int screenHeight = dm.heightPixels;

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(realDisplayMetrics);
        } else {
            Class c;
            try {
                c = Class.forName("android.view.Display");
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, realDisplayMetrics);
            } catch (Exception e) {
                realDisplayMetrics.setToDefaults();
                e.printStackTrace();
            }
        }
        int screenRealHeight = realDisplayMetrics.heightPixels;
        return (screenRealHeight - screenHeight) > 0;
    }
}
