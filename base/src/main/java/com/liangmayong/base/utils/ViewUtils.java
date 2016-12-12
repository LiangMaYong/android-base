package com.liangmayong.base.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class ViewUtils {

    /**
     * view
     */
    private View view;
    /**
     * parent
     */
    private View parent;
    /**
     * toWidth
     */
    private int toWidth;
    /**
     * toHeight
     */
    private int toHeight;

    private ViewUtils(View view) {
        this.view = view;
        if (view == null) {
            throw new IllegalArgumentException("LViewPorter init -> View == NULL!");
        }
    }

    /**
     * from
     *
     * @param view view
     * @return ViewUtils
     */
    public static ViewUtils from(View view) {
        return new ViewUtils(view);
    }

    /**
     * from
     *
     * @param parent parent
     * @param id     id
     * @return ViewUtils
     */
    public static ViewUtils from(View parent, int id) {
        return from(parent.findViewById(id));
    }

    /**
     * from
     *
     * @param activity activity
     * @param id       id
     * @return ViewUtils
     */
    public static ViewUtils from(Activity activity, int id) {
        return from(activity.findViewById(id));
    }

    /**
     * of
     *
     * @param parent parent
     * @return ViewUtils
     */
    public ViewUtils of(View parent) {
        this.parent = parent;
        return this;
    }

    /**
     * ofScreen
     *
     * @return ViewUtils
     */
    public ViewUtils ofScreen() {
        parent = null;
        return this;
    }

    /**
     * of
     *
     * @param activity activity
     * @return ViewUtils
     */
    public ViewUtils of(Activity activity) {

        this.parent = activity.getWindow().getDecorView();

        return this;
    }

    /**
     * ofWidth
     *
     * @param divCount divCount
     * @return ViewUtils
     */
    public ViewUtils ofWidth(int divCount) {
        if (parent != null) {
            int width = parent.getWidth();
            toWidth = width / divCount;
        } else {
            toWidth = ScreenUtils.getScreenWidth(view.getContext()) / 2;
        }

        return this;
    }

    /**
     * ofHeight
     *
     * @param divCount divCount
     * @return ViewUtils
     */
    public ViewUtils ofHeight(int divCount) {
        if (parent != null) {
            int height = parent.getHeight();
            toHeight = height / divCount;
        } else {
            toHeight = ScreenUtils.getScreenHeight(view.getContext()) / 2;
        }
        return this;
    }

    /**
     * divWidth
     *
     * @param divCount divCount
     * @return ViewUtils
     */
    public ViewUtils divWidth(int divCount) {
        if (toWidth != 0) {
            toWidth /= divCount;
        } else {
            toWidth = ScreenUtils.getScreenWidth(view.getContext()) / divCount;
        }
        return this;
    }

    /**
     * divHeight
     *
     * @param divCount divCount
     * @return ViewUtils
     */
    public ViewUtils divHeight(int divCount) {
        if (toHeight != 0) {
            toHeight /= divCount;
        } else {
            toHeight = ScreenUtils.getScreenHeight(view.getContext()) / divCount;
        }
        return this;
    }

    /**
     * div
     *
     * @param divCount divCount
     * @return ViewUtils
     */
    public ViewUtils div(int divCount) {
        divWidth(divCount);
        divHeight(divCount);
        return this;
    }

    /**
     * castWidth
     *
     * @param toWidth toWidth
     * @return ViewUtils
     */
    public ViewUtils castWidth(int toWidth) {
        this.toWidth = toWidth;
        return this;
    }

    /**
     * castHeight
     *
     * @param toHeight toHeight
     * @return ViewUtils
     */
    public ViewUtils castHeight(int toHeight) {
        this.toHeight = toHeight;
        return this;
    }

    /**
     * fillWidth
     *
     * @return ViewUtils
     */
    public ViewUtils fillWidth() {
        if (parent != null) {
            toWidth = parent.getWidth();
            return this;
        } else {
            View viewParent = (View) view.getParent();
            if (viewParent != null) {
                toWidth = viewParent.getWidth();
            } else {
                toWidth = ScreenUtils.getScreenWidth(view.getContext());
            }
        }

        return this;
    }

    /**
     * fillHeight
     *
     * @return ViewUtils
     */
    public ViewUtils fillHeight() {
        if (parent != null) {
            toWidth = parent.getWidth();
            return this;
        } else {
            View viewParent = (View) view.getParent();
            if (viewParent != null) {
                toWidth = viewParent.getHeight();
            } else {
                toWidth = ScreenUtils.getScreenHeight(view.getContext());
            }
        }
        return this;
    }

    /**
     * fillWidthAndHeight
     *
     * @return ViewUtils
     */
    public ViewUtils fillWidthAndHeight() {
        fillWidth();
        fillHeight();
        return this;
    }

    /**
     * alpha
     *
     * @param alpha alpha
     * @return ViewUtils
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ViewUtils alpha(float alpha) {
        view.setAlpha(alpha);
        return this;
    }

    /**
     * commit
     */
    public void commit() {
        ViewGroup.LayoutParams params = view.getLayoutParams();

        if (toWidth != 0) {
            params.width = toWidth;
        }
        if (toHeight != 0) {
            params.height = toHeight;
        }

        view.setLayoutParams(params);
        view.invalidate();

    }

    /**
     * sameAs
     *
     * @param another another
     * @return ViewUtils
     */
    public ViewUtils sameAs(View another) {
        toWidth = another.getWidth();
        toHeight = another.getHeight();
        return this;
    }

}
