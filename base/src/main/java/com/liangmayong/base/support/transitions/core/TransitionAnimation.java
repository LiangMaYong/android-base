/*
 * Copyright (C) 2015 takahirom, shiraji
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

package com.liangmayong.base.support.transitions.core;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class TransitionAnimation {
    public static final Object lock = new Object();
    private static final String TAG = "Transition";
    private static final int MAX_TIME_TO_WAIT = 3000;
    public static WeakReference<Bitmap> bitmapCache;
    public static boolean isImageFileReady = false;

    public static TransitionMoveData startAnimation(Context context, final View toView, final View bgView, float toAlpha, float bgAlpha, Bundle transitionBundle, Bundle savedInstanceState, final int duration, final TimeInterpolator interpolator, final Animator.AnimatorListener listener) {
        final TransitionData transitionData = new TransitionData(context, transitionBundle);
        if (transitionData.imageFilePath != null) {
            setImageToView(toView, transitionData.imageFilePath);
        }
        final TransitionMoveData moveData = new TransitionMoveData();
        moveData.toView = toView;
        moveData.bgView = bgView;
        moveData.toAlpha = toAlpha;
        moveData.bgAlpha = bgAlpha;
        moveData.duration = duration;
        if (savedInstanceState == null) {
            ViewTreeObserver observer = toView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    toView.getViewTreeObserver().removeOnPreDrawListener(this);

                    int[] screenLocation = new int[2];
                    toView.getLocationOnScreen(screenLocation);
                    moveData.leftDelta = transitionData.thumbnailLeft - screenLocation[0];
                    moveData.topDelta = transitionData.thumbnailTop - screenLocation[1];

                    moveData.widthScale = (float) transitionData.thumbnailWidth / toView.getWidth();
                    moveData.heightScale = (float) transitionData.thumbnailHeight / toView.getHeight();
                    runEnterAnimation(moveData, interpolator, listener);
                    return true;
                }
            });
        }
        return moveData;
    }


    private static void runEnterAnimation(TransitionMoveData moveData, TimeInterpolator interpolator, Animator.AnimatorListener listener) {
        final View toView = moveData.toView;
        toView.setPivotX(0);
        toView.setPivotY(0);
        toView.setScaleX(moveData.widthScale);
        toView.setScaleY(moveData.heightScale);
        toView.setTranslationX(moveData.leftDelta);
        toView.setTranslationY(moveData.topDelta);
        toView.setAlpha(moveData.toAlpha);

        toView.animate()
                .alpha(1.0f)
                .setDuration(moveData.duration)
                .scaleX(1)
                .scaleY(1)
                .translationX(0)
                .translationY(0)
                .setListener(listener)
                .setInterpolator(interpolator);
        final View bgView = moveData.bgView;
        if (bgView != null) {
            bgView.setAlpha(moveData.bgAlpha);
            bgView.animate()
                    .setDuration(moveData.duration)
                    .alpha(1.0f)
                    .setListener(listener)
                    .setInterpolator(interpolator);
        }
    }

    private static void setImageToView(View toView, String imageFilePath) {
        Bitmap bitmap;
        if (bitmapCache == null || (bitmap = bitmapCache.get()) == null) {
            synchronized (lock) {
                while (!isImageFileReady) {
                    try {
                        lock.wait(MAX_TIME_TO_WAIT);
                    } catch (InterruptedException e) {
                    }
                }
            }
            // Cant get bitmap by static field
            bitmap = BitmapFactory.decodeFile(imageFilePath);
        } else {
            bitmapCache.clear();
        }
        setImageToView(toView, bitmap);
    }

    private static void setImageToView(View toView, Bitmap bitmap) {
        if (toView instanceof ImageView) {
            final ImageView toImageView = (ImageView) toView;
            toImageView.setImageBitmap(bitmap);
        } else {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                toView.setBackground(new BitmapDrawable(toView.getResources(), bitmap));
            } else {
                toView.setBackgroundDrawable(new BitmapDrawable(toView.getResources(), bitmap));
            }
        }
    }

    public static void startExitAnimation(TransitionMoveData moveData, TimeInterpolator interpolator, final Runnable endAction, Animator.AnimatorListener listener) {
        View view = moveData.toView;
        int duration = moveData.duration;
        int leftDelta = moveData.leftDelta;
        int topDelta = moveData.topDelta;
        float widthScale = moveData.widthScale;
        float heightScale = moveData.heightScale;
        view.animate()
                .alpha(moveData.toAlpha)
                .setDuration(duration)
                .scaleX(widthScale)
                .scaleY(heightScale)
                .setInterpolator(interpolator)
                .translationX(leftDelta)
                .setListener(listener)
                .translationY(topDelta);
        view.postDelayed(endAction, duration);
        // bg view
        View bgview = moveData.bgView;
        if (bgview != null) {
            bgview.animate()
                    .alpha(moveData.bgAlpha)
                    .setDuration(duration);
        }
    }
}
