package com.liangmayong.base.support.loading;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

/**
 * Loading
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class Loading {

    //defualt loadingColor
    private static int loadingColor = -1;
    //defualt backgroundColor
    private static int backgroundColor = -1;
    //defualt dimAmount
    private static float dimAmount = 0.0f;
    //defualt radius
    private static int radius = 20;
    //fragment tag
    private static final String TAG = "LoadingFragment";

    /**
     * setDefaultBackgroundColor
     *
     * @param backgroundColor backgroundColor
     */
    public static void setDefaultBackgroundColor(int backgroundColor) {
        Loading.backgroundColor = backgroundColor;
    }

    /**
     * setDefaultDimAmount
     *
     * @param dimAmount dimAmount
     */
    public static void setDefaultDimAmount(float dimAmount) {
        Loading.dimAmount = dimAmount;
    }

    /**
     * setDefaultLoadingColor
     *
     * @param loadingColor loadingColor
     */
    public static void setDefaultLoadingColor(int loadingColor) {
        Loading.loadingColor = loadingColor;
    }

    /**
     * setDefaultLoadingColor
     *
     * @param radius radius
     */
    public static void setDefaultRadius(int radius) {
        Loading.radius = radius;
    }

    /**
     * showLoading
     *
     * @param activity activity
     */
    public static LoadingFragment showLoading(FragmentActivity activity) {
        return showLoading(activity, "", loadingColor, backgroundColor, radius, dimAmount);
    }

    /**
     * showLoading
     *
     * @param activity activity
     * @param label    label
     */
    public static LoadingFragment showLoading(FragmentActivity activity, String label) {
        return showLoading(activity, label, loadingColor, backgroundColor, radius, dimAmount);
    }

    /**
     * showLoading
     *
     * @param activity        activity
     * @param label           label
     * @param backgroundColor backgroundColor
     * @param dimAmount       dimAmount
     */
    public static LoadingFragment showLoading(FragmentActivity activity, String label, int loadingColor, int backgroundColor, int radius, float dimAmount) {
        try {
            synchronized (activity) {
                DialogFragment loadingFragment = (DialogFragment) activity.getSupportFragmentManager()
                        .findFragmentByTag(TAG);
                if (loadingFragment != null) {
                    ((LoadingFragment) loadingFragment).setLabel(label);
                    ((LoadingFragment) loadingFragment).setLoadingColor(loadingColor);
                    ((LoadingFragment) loadingFragment).setBackgroundColor(backgroundColor);
                    ((LoadingFragment) loadingFragment).setDimAmount(dimAmount);
                    ((LoadingFragment) loadingFragment).setRadius(radius);
                    if (loadingFragment.isAdded()) {
                        activity.getSupportFragmentManager().beginTransaction().show(loadingFragment).commit();
                    }
                } else {
                    loadingFragment = new LoadingFragment();
                    ((LoadingFragment) loadingFragment).setLabel(label);
                    ((LoadingFragment) loadingFragment).setLoadingColor(loadingColor);
                    ((LoadingFragment) loadingFragment).setBackgroundColor(backgroundColor);
                    ((LoadingFragment) loadingFragment).setDimAmount(dimAmount);
                    ((LoadingFragment) loadingFragment).setRadius(radius);
                    loadingFragment.show(activity.getSupportFragmentManager(), TAG);
                }
                return (LoadingFragment) loadingFragment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * cancelLoading
     *
     * @param activity activity
     */
    public static void cancelLoading(FragmentActivity activity) {
        try {
            DialogFragment loadingFragment = (DialogFragment) activity.getSupportFragmentManager()
                    .findFragmentByTag(TAG);
            if (loadingFragment != null) {
                activity.getSupportFragmentManager().beginTransaction().remove(loadingFragment).commit();
            }
        } catch (Exception e) {
        }
    }

}
