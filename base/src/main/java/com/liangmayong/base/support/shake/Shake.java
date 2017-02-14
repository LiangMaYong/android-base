package com.liangmayong.base.support.shake;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.liangmayong.base.support.utils.VibratorUtils;

/**
 * Created by LiangMaYong on 2017/2/14.
 */
public class Shake {

    public static void shakeActivity(Activity activity, int duration, boolean vibrate) {
        ViewGroup content = (ViewGroup) activity.findViewById(android.R.id.content);
        if (content != null) {
            shakeView(content, duration);
        }
        if (vibrate) {
            VibratorUtils.vibrate(activity, duration);
        }
    }

    public static void shakeView(View view, int duration) {
        final ShakeAnimation shakeAnimation = new ShakeAnimation();
        shakeAnimation.setDuration(duration);
        if (view != null) {
            view.startAnimation(shakeAnimation);
        }
    }

    private static class ShakeAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            t.getMatrix().setTranslate((float) Math.sin(interpolatedTime * 40) * 10, 0);
            super.applyTransformation(interpolatedTime, t);
        }
    }
}
