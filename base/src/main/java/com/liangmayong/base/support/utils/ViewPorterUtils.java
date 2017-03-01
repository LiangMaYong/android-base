package com.liangmayong.base.support.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewPorterUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class ViewPorterUtils {

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

    private ViewPorterUtils(View view) {
        this.view = view;
        if (view == null) {
            throw new IllegalArgumentException("ViewPorterUtils init -> View == NULL!");
        }
    }

    /**
     * from
     *
     * @param view view
     * @return ViewUtils
     */
    public static ViewPorterUtils from(View view) {
        return new ViewPorterUtils(view);
    }

    /**
     * from
     *
     * @param parent parent
     * @param id     id
     * @return ViewUtils
     */
    public static ViewPorterUtils from(View parent, int id) {
        return from(parent.findViewById(id));
    }

    /**
     * from
     *
     * @param activity activity
     * @param id       id
     * @return ViewUtils
     */
    public static ViewPorterUtils from(Activity activity, int id) {
        return from(activity.findViewById(id));
    }

    /**
     * of
     *
     * @param parent parent
     * @return ViewUtils
     */
    public ViewPorterUtils of(View parent) {
        this.parent = parent;
        return this;
    }

    /**
     * ofScreen
     *
     * @return ViewUtils
     */
    public ViewPorterUtils ofScreen() {
        parent = null;
        return this;
    }

    /**
     * of
     *
     * @param activity activity
     * @return ViewUtils
     */
    public ViewPorterUtils of(Activity activity) {

        this.parent = activity.getWindow().getDecorView();

        return this;
    }

    /**
     * ofWidth
     *
     * @param divCount divCount
     * @return ViewUtils
     */
    public ViewPorterUtils ofWidth(int divCount) {
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
    public ViewPorterUtils ofHeight(int divCount) {
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
    public ViewPorterUtils divWidth(int divCount) {
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
    public ViewPorterUtils divHeight(int divCount) {
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
    public ViewPorterUtils div(int divCount) {
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
    public ViewPorterUtils castWidth(int toWidth) {
        this.toWidth = toWidth;
        return this;
    }

    /**
     * castHeight
     *
     * @param toHeight toHeight
     * @return ViewUtils
     */
    public ViewPorterUtils castHeight(int toHeight) {
        this.toHeight = toHeight;
        return this;
    }

    /**
     * fillWidth
     *
     * @return ViewUtils
     */
    public ViewPorterUtils fillWidth() {
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
    public ViewPorterUtils fillHeight() {
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
    public ViewPorterUtils fillWidthAndHeight() {
        fillWidth();
        fillHeight();
        return this;
    }

    /**
     * toAlpha
     *
     * @param alpha toAlpha
     * @return ViewUtils
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ViewPorterUtils alpha(float alpha) {
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
    public ViewPorterUtils sameAs(View another) {
        toWidth = another.getWidth();
        toHeight = another.getHeight();
        return this;
    }

}
