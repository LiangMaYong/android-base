/*
 * Copyright (C) 2015 takahirom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.liangmayong.base.support.transitions;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.liangmayong.base.support.transitions.core.TransitionAnimation;
import com.liangmayong.base.support.transitions.core.TransitionMoveData;

public class ActivityTransition {
    private int duration = 450;
    private float bgAlpha = 0.0f;
    private float toAlpha = 1.0f;
    private View toView;
    private View bgView;
    private TimeInterpolator interpolator;
    private Animator.AnimatorListener listener;
    private Intent fromIntent;

    private ActivityTransition(Intent intent) {
        this.fromIntent = intent;
    }

    public static ActivityTransition with(Intent intent) {
        return new ActivityTransition(intent);
    }

    public ActivityTransition to(View toView) {
        this.toView = toView;
        return this;
    }

    public ActivityTransition bg(View bgView) {
        this.bgView = bgView;
        return this;
    }

    public ActivityTransition toAlpha(float alpha) {
        this.toAlpha = alpha;
        return this;
    }

    public ActivityTransition bgAlpha(float alpha) {
        this.bgAlpha = alpha;
        return this;
    }

    public ActivityTransition duration(int duration) {
        this.duration = duration;
        return this;
    }

    public ActivityTransition interpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public ActivityTransition enterListener(Animator.AnimatorListener listener) {
        this.listener = listener;
        return this;
    }

    public ExitActivityTransition start(Bundle savedInstanceState) {
        if (interpolator == null) {
            interpolator = new DecelerateInterpolator();
        }
        final Context context = toView.getContext();
        final Bundle bundle = fromIntent.getExtras();
        final TransitionMoveData moveData = TransitionAnimation.startAnimation(context, toView, bgView, toAlpha, bgAlpha, bundle, savedInstanceState, duration, interpolator, listener);
        return new ExitActivityTransition(moveData);
    }

}
