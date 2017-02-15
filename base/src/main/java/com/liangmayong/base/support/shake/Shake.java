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
            ViewGroup contentView = (ViewGroup) content.getParent();
            if (contentView != null) {
                shakeView(contentView, duration, 30, 8, vibrate);
            }
        }
    }

    public static void shakeView(View view, int duration, boolean vibrate) {
        shakeView(view, duration, 30, 8, vibrate);
    }

    public static void shakeView(View view, int duration, int rate, int range, boolean vibrate) {
        final ShakeAnimation shakeAnimation = new ShakeAnimation(rate, range);
        shakeAnimation.setDuration(duration);
        if (view != null) {
            view.startAnimation(shakeAnimation);
            if (vibrate) {
                VibratorUtils.vibrate(view.getContext(), duration);
            }
        }
    }

    private static class ShakeAnimation extends Animation {

        private int rate = 30;
        private int range = 8;

        public ShakeAnimation(int rate, int range) {
            this.rate = rate;
            this.range = range;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            t.getMatrix().setTranslate((float) Math.sin(interpolatedTime * rate) * range, 0);
            super.applyTransformation(interpolatedTime, t);
        }
    }
}
