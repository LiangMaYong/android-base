package com.liangmayong.base.support.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * AnimationUtil
 */
public class AnimationUtil {

    public static void anyPropertyAnimation(View view, String property, float srcY, float destY, long duration,
                                            Animator.AnimatorListener listener, ValueAnimator.AnimatorUpdateListener updateListener) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            ObjectAnimator anim = null;
            anim = ObjectAnimator.ofFloat(view, property, srcY, destY).setDuration(duration);
            if (listener != null)
                anim.addListener(listener);
            if (updateListener != null) {
                anim.addUpdateListener(updateListener);
            }
            anim.setInterpolator(new DecelerateInterpolator());
            anim.start();
        }
    }

    /**
     * transitYAnimation
     *
     * @param view     view
     * @param srcY     srcY
     * @param destY    destY
     * @param duration duration
     * @param listener listener
     */
    public static void transitYAnimation(View view, float srcY, float destY, long duration, Animator.AnimatorListener listener) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationY", srcY, destY).setDuration(duration);
            if (listener != null)
                anim.addListener(listener);
            anim.start();
        }
    }

    /**
     * transitYAnimation
     *
     * @param view     view
     * @param destY    destY
     * @param duration duration
     * @param listener listener
     */
    public static void transitYAnimation(View view, float destY, long duration, Animator.AnimatorListener listener) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationY", destY).setDuration(duration);
            if (listener != null)
                anim.addListener(listener);
            anim.start();
        }
    }


}
