package com.liangmayong.base.support.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by LiangMaYong on 2016/9/10.
 */
public class LoadingFragment extends DialogFragment {

    public LoadingFragment() {
    }

    private LinearLayout rootLayout = null;
    private TextView labelView = null;
    private String label = "loading";
    private float dimAmount = 0.0f;
    private int radius = 20;
    private int loadingColor = 0xffffffff;
    private int backgroundColor = 0x90333333;
    private LoadingProgressWheel progressWheel = null;
    private Handler handler = new Handler();

    /**
     * labelView
     *
     * @return loading
     */
    public TextView getLabelView() {
        return labelView;
    }

    /**
     * label
     *
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * setLabel
     *
     * @param label label
     */
    public void setLabel(String label) {
        this.label = label;
        if (labelView != null) {
            labelView.setText(label);
            if ("".equals(label) || label == null) {
                labelView.setVisibility(View.GONE);
            } else {
                labelView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        getDialog().setCanceledOnTouchOutside(false);
        window.getDecorView().setBackgroundColor(0x00ffffff);
        setDimAmount(dimAmount);
    }

    /**
     * setDefaultDimAmount
     *
     * @param dimAmount dimAmount
     */
    public void setDimAmount(float dimAmount) {
        if (dimAmount < 0) {
            dimAmount = 0.0f;
        } else if (dimAmount > 1) {
            dimAmount = 1.0f;
        }
        this.dimAmount = dimAmount;
        try {
            if (getDialog() != null && getDialog().getWindow() != null) {
                Window window = getDialog().getWindow();
                WindowManager.LayoutParams windowParams = window.getAttributes();
                windowParams.dimAmount = dimAmount;
                window.setAttributes(windowParams);
            }
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Context context = inflater.getContext();
        rootLayout = new LinearLayout(context);

        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        rootLayout.setMinimumWidth(dip2px(context, 80));
        rootLayout.setMinimumHeight(dip2px(context, 80));
        rootLayout.setGravity(Gravity.CENTER);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setPadding(dip2px(context, 20), dip2px(context, 10), dip2px(context, 20), dip2px(context, 10));
        rootLayout.setBackgroundDrawable(new RoundDrawable(radius, backgroundColor));
        progressWheel = getProgressWheel(context, loadingColor);
        rootLayout.addView(progressWheel);

        labelView = new TextView(context);
        labelView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        labelView.setSingleLine();
        labelView.setGravity(Gravity.CENTER);
        labelView.setTextSize(15);
        labelView.setTextColor(loadingColor);
        labelView.setText(label);
        if ("".equals(label) || label == null) {
            labelView.setVisibility(View.GONE);
        } else {
            labelView.setVisibility(View.VISIBLE);
        }
        rootLayout.addView(labelView);

        return rootLayout;
    }

    /**
     * getProgressWheel
     *
     * @return progressWheel
     */
    protected LoadingProgressWheel getProgressWheel(Context context, int color) {
        LoadingProgressWheel progressWheel = new LoadingProgressWheel(context);
        progressWheel.setBarColor(color);
        progressWheel.setRimColor(0x00ffffff);
        progressWheel.setRimWidth(5);
        progressWheel.setBarWidth(5);
        int width = dip2px(context, 30);
        progressWheel.setCircleRadius(width);
        progressWheel.setPadding(0, dip2px(context, 10), 0, dip2px(context, 10));
        if (!progressWheel.isSpinning()) {
            handler.postDelayed(runnable, 100);
        }
        return progressWheel;
    }

    /**
     * runnable
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (progressWheel != null) {
                // progressWheel.spin();
                progressWheel.spin();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
    }

    /**
     * setDefaultBackgroundColor
     *
     * @param color color
     */
    @SuppressWarnings("deprecation")
    public void setBackgroundColor(int color) {
        if (color == -1) {
            return;
        }
        backgroundColor = color;
        if (rootLayout != null) {
            rootLayout.setBackgroundDrawable(new RoundDrawable(radius, backgroundColor));
        }
    }

    /**
     * setDefaultRadius
     *
     * @param radius radius
     */
    @SuppressWarnings("deprecation")
    public void setRadius(int radius) {
        if (radius < 0) {
            return;
        }
        this.radius = radius;
        if (rootLayout != null) {
            rootLayout.setBackgroundDrawable(new RoundDrawable(radius, backgroundColor));
        }
    }

    /**
     * setDefaultLoadingColor
     *
     * @param color color
     */
    public void setLoadingColor(int color) {
        if (color == -1) {
            return;
        }
        this.loadingColor = color;
        if (progressWheel != null) {
            progressWheel.setBarColor(loadingColor);
        }
        if (labelView != null) {
            labelView.setTextColor(loadingColor);
        }
    }

    /**
     * dip2px
     *
     * @param context context
     * @param dpValue dpValue
     * @return px
     */
    protected int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * RoundDrawable
     */
    private static class RoundDrawable extends ColorDrawable {

        private int round;

        public RoundDrawable(int round, int color) {
            super(color);
            this.round = round;
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(new RoundCanvas(canvas, round));
        }

        private class RoundCanvas extends Canvas {

            private int round;
            private Canvas canvas;

            public RoundCanvas(Canvas canvas, int round) {
                this.round = round;
                this.canvas = canvas;
            }

            @Override
            public void drawRect(Rect r, Paint paint) {
                RectF rectF = new RectF(r);
                paint.setAntiAlias(true);
                canvas.drawRoundRect(rectF, round, round, paint);
            }
        }
    }
}
