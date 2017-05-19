package com.liangmayong.base.widget.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liangmayong.base.R;
import com.liangmayong.base.support.utils.DimenUtils;

import java.util.ArrayList;
import java.util.List;

public class SegmentView extends LinearLayout {
    private int mSelectedBackgroundColors = 0xff3399ff;
    private int mUnSelectedBackgroundColors = 0xffffffff;
    private int mSelectedTextColors = 0xffffffff;
    private int mUnSelectedTextColors = 0xff3399ff;
    private int mCornerRadius = 5;
    private LinearLayout contentView;
    private RadiusDrawable mBackgroundDrawable;
    private List<SegmentItem> items = new ArrayList<SegmentItem>();
    private OnSegmentSelectedListener segmentSelectedListener;
    private int selectedIndex = -1;

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public interface OnSegmentSelectedListener {
        void onSelected(String item, int index);
    }

    /**
     * setOnSegmentSelectedListener
     *
     * @param segmentSelectedListener
     */
    public void setOnSegmentSelectedListener(OnSegmentSelectedListener segmentSelectedListener) {
        this.segmentSelectedListener = segmentSelectedListener;
    }

    public SegmentView(Context context) {
        super(context);
        initViews(null);
    }

    public SegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SegmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(attrs);
    }

    private void initViews(AttributeSet attrs) {
        removeAllViews();
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        contentView = new LinearLayout(getContext());
        int padding = DimenUtils.dip2px(getContext(), 1);
        contentView.setPadding(padding, padding, padding, padding);
        contentView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        if (attrs != null) {
            TypedArray a = getContext().getTheme()
                    .obtainStyledAttributes(attrs, R.styleable.SegmentView, 0, 0);
            try {
                mSelectedBackgroundColors = a.getColor(R.styleable.SegmentView_segmentBackgroundColor, mSelectedBackgroundColors);
                mUnSelectedBackgroundColors = a.getColor(R.styleable.SegmentView_segmentUnSelectBackgroundColor, mUnSelectedBackgroundColors);
                mUnSelectedTextColors = a.getColor(R.styleable.SegmentView_segmentUnSelectTextColor, mSelectedBackgroundColors);
                mSelectedTextColors = a.getColor(R.styleable.SegmentView_segmentTextColor, mUnSelectedBackgroundColors);
            } finally {
                a.recycle();
            }
        }
        initBackground();
        setItems(0, "Frist", "Second");
        addView(contentView);
    }

    @SuppressWarnings("deprecation")
    public void initBackground() {
        mBackgroundDrawable = new RadiusDrawable(mCornerRadius, true, mSelectedBackgroundColors);
        contentView.setOrientation(HORIZONTAL);
        if (Build.VERSION.SDK_INT < 16) {
            contentView.setBackgroundDrawable(mBackgroundDrawable);
        } else {
            contentView.setBackground(mBackgroundDrawable);
        }
    }

    public void setSegmentColors(int color, int selected) {
        setSegmentBackgroundColor(selected);
        setSegmentSelectedBackgroundColor(selected);
        setSegmentSelectedTextColor(color);
        setSegmentUnSelectedBackgroundColor(color);
        setSegmentUnSelectedTextColor(selected);
    }

    public void setSegmentUnSelectedBackgroundColor(int color) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setUnselectedBackgroundColor(color);
        }
    }

    public void setSegmentUnSelectedTextColor(int color) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setUnselectedTextColor(color);
        }
    }

    public void setSegmentBackgroundColor(int color) {
        this.mSelectedBackgroundColors = color;
        initBackground();
    }

    public void setSegmentSelectedBackgroundColor(int color) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setSelectedBackgroundColor(color);
        }
    }

    public void setSegmentSelectedTextColor(int color) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setSelectedTextColor(color);
        }
    }

    public void setSegmentRadius(int radius) {
        this.mCornerRadius = radius;
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setRadius(radius);
        }
        initBackground();
    }

    public void setItems(int index, String... text) {
        selectedIndex = -1;
        contentView.removeAllViews();
        //noinspection CollectionAddedToSelf
        items.removeAll(items);
        for (int i = 0; i < text.length; i++) {
            SegmentItem item = item(text[i]);
            items.add(item);
            if (i == 0) {
                item.setFrist(true);
            }
            if (i == text.length - 1) {
                LayoutParams layoutParams = (LayoutParams) item.getLayoutParams();
                layoutParams.rightMargin = 0;
                item.setEnd(true);
            }
            final int selected = i;
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected(selected);
                }
            });
            contentView.addView(item);
        }
        selected(index);
    }

    public void selected(int index) {
        if (index >= items.size()) {
            return;
        }
        if (index < 0) {
            return;
        }
        selectedIndex = index;
        for (int i = 0; i < items.size(); i++) {
            items.get(i).selected(false);
            if (i == index) {
                items.get(i).selected(true);
                if (segmentSelectedListener != null) {
                    segmentSelectedListener.onSelected(items.get(i).getText().toString(), index);
                }
            }
        }
    }

    private SegmentItem item(String msg) {
        SegmentItem item = new SegmentItem(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        layoutParams.rightMargin = 2;
        item.setLayoutParams(layoutParams);
        item.selected(false);
        item.setText(msg);
        return item;
    }

    private class SegmentItem extends TextView {

        private int mCornerRadius = 5;
        private boolean selected = false;
        private boolean isFrist = false;
        private boolean isEnd = false;

        @Override
        public boolean isSelected() {
            return selected;
        }

        public void setFrist(boolean isFrist) {
            this.isFrist = isFrist;
            if (isFrist) {
                isEnd = false;
            }
        }

        public void setEnd(boolean isEnd) {
            this.isEnd = isEnd;
            if (isEnd) {
                isFrist = false;
            }
        }

        public void setRadius(int radius) {
            this.mCornerRadius = radius;
            selected(isSelected());
        }

        public void setSelectedTextColor(int color) {
            mSelectedTextColors = color;
            selected(isSelected());
        }

        public void setUnselectedTextColor(int color) {
            mUnSelectedTextColors = color;
            selected(isSelected());
        }

        public void setSelectedBackgroundColor(int color) {
            mSelectedBackgroundColors = color;
            selected(isSelected());
        }

        public void setUnselectedBackgroundColor(int color) {
            mUnSelectedBackgroundColors = color;
            selected(isSelected());
        }

        private RadiusDrawable mBackgroundDrawable;

        public SegmentItem(Context context) {
            super(context);
            initViews();
        }

        private void initViews() {
            setPadding(DimenUtils.dip2px(getContext(), 10), 0,
                    DimenUtils.dip2px(getContext(), 10), 0);
            setGravity(Gravity.CENTER);
            setSingleLine();
            selected(false);
        }

        /**
         * set textColor
         *
         * @param color    color
         * @param selected selected
         */
        public void setTextColor(int color, int selected) {
            int stateSelected = android.R.attr.state_selected;
            int stateFocesed = android.R.attr.state_focused;
            int[][] state = {{stateSelected}, {-stateSelected}, {stateFocesed}, {-stateFocesed}};
            ColorStateList colors = new ColorStateList(state, new int[]{selected, color, selected, color});
            setTextColor(colors);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (!isSelected()) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mBackgroundDrawable.setColor(mUnSelectedBackgroundColors - 0x00111111);
                    invalidate();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mBackgroundDrawable.setColor(mUnSelectedBackgroundColors);
                    invalidate();
                }
            }
            return super.onTouchEvent(event);
        }

        @SuppressWarnings("deprecation")
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public void selected(boolean select) {
            this.selected = select;
            if (select) {
                setEnabled(false);
                mBackgroundDrawable = new RadiusDrawable(0, true, mSelectedBackgroundColors);
                mBackgroundDrawable.setStrokeWidth(1);
                mBackgroundDrawable.setStrokeColor(mSelectedBackgroundColors);
                if (isFrist) {
                    mBackgroundDrawable.setRadiuses(mCornerRadius, 0, mCornerRadius, 0);
                } else if (isEnd) {
                    mBackgroundDrawable.setRadiuses(0, mCornerRadius, 0, mCornerRadius);
                }
                setTextColor(mSelectedBackgroundColors);
                if (Build.VERSION.SDK_INT < 16) {
                    setBackgroundDrawable(mBackgroundDrawable);
                } else {
                    setBackground(mBackgroundDrawable);
                }
                setTextColor(mSelectedTextColors, mSelectedTextColors - 0xA1000000);
            } else {
                setEnabled(true);
                mBackgroundDrawable = new RadiusDrawable(0, true, mUnSelectedBackgroundColors);
                setTextColor(mSelectedBackgroundColors);
                mBackgroundDrawable.setStrokeWidth(1);
                if (isFrist) {
                    mBackgroundDrawable.setRadiuses(mCornerRadius, 0, mCornerRadius, 0);
                } else if (isEnd) {
                    mBackgroundDrawable.setRadiuses(0, mCornerRadius, 0, mCornerRadius);
                }
                mBackgroundDrawable.setStrokeColor(mSelectedBackgroundColors);
                if (Build.VERSION.SDK_INT < 16) {
                    setBackgroundDrawable(mBackgroundDrawable);
                } else {
                    setBackground(mBackgroundDrawable);
                }
                setTextColor(mUnSelectedTextColors, mUnSelectedTextColors - 0xA1000000);
            }
        }
    }

    /**
     * RadiusDrawable
     *
     * @author LiangMaYong
     * @version 1.0
     */
    public static class RadiusDrawable extends Drawable {


        private int topLeftRadius;
        private int topRightRadius;
        private int bottomLeftRadius;
        private int bottomRightRadius;


        private int left;
        private int top;
        private int right;
        private int bottom;


        @SuppressWarnings("unused")
        private int width;
        @SuppressWarnings("unused")
        private int height;


        @SuppressWarnings("unused")
        private int centerX;
        @SuppressWarnings("unused")
        private int centerY;


        private final Paint paint;
        private int color;
        private final boolean isStroke;
        private int strokeWidth = 0;
        private int strokeColor;


        private Path path;


        public RadiusDrawable(int topLeftRadius, int topRightRadius, int bottomLeftRadius,
                              int bottomRightRadius, boolean isStroke, int color) {
            this.topLeftRadius = topLeftRadius;
            this.topRightRadius = topRightRadius;
            this.bottomLeftRadius = bottomLeftRadius;
            this.bottomRightRadius = bottomRightRadius;


            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.isStroke = isStroke;
            this.color = color;
        }


        public RadiusDrawable(int radius, boolean isStroke, int color) {
            this.topLeftRadius =
                    this.topRightRadius = this.bottomLeftRadius = this.bottomRightRadius = radius;


            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.isStroke = isStroke;
            this.color = color;


        }


        public void setStrokeWidth(int width) {
            strokeWidth = width;
            setBounds(left, top, right, bottom);
        }


        public void setStrokeColor(int strokeColor) {
            this.strokeColor = strokeColor;
        }


        public void setColor(int color) {
            this.color = color;
        }


        public void setRadius(int radius) {
            this.topLeftRadius = this.topRightRadius = this.bottomLeftRadius = this.bottomRightRadius = radius;
        }


        public void setRadiuses(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius) {
            this.topLeftRadius = topLeftRadius;
            this.topRightRadius = topRightRadius;
            this.bottomLeftRadius = bottomLeftRadius;
            this.bottomRightRadius = bottomRightRadius;
        }


        @Override
        public void setBounds(int left, int top, int right, int bottom) {
            super.setBounds(left, top, right, bottom);


            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;


            if (isStroke) {
                int halfStrokeWidth = strokeWidth / 2;
                left += halfStrokeWidth;
                top += halfStrokeWidth;
                right -= halfStrokeWidth;
                bottom -= halfStrokeWidth;
            }


            path = new Path();
            path.moveTo(left + topLeftRadius, top);
            path.lineTo(right - topRightRadius, top);
            path.arcTo(new RectF(right - topRightRadius * 2, top, right, top + topRightRadius * 2),
                    -90, 90);
            // path.quadTo(right, top, right, top + topRightRadius);
            path.lineTo(right, bottom - bottomRightRadius);
            path.arcTo(new RectF(right - bottomRightRadius * 2, bottom - bottomRightRadius * 2, right,
                    bottom), 0, 90);
            // path.quadTo(right, bottom, right - bottomRightRadius, bottom);
            path.lineTo(left + bottomLeftRadius, bottom);
            path.arcTo(new RectF(left, bottom - bottomLeftRadius * 2, left + bottomLeftRadius * 2,
                    bottom), 90, 90);
            // path.quadTo(left, bottom, left, bottom - bottomLeftRadius);
            path.lineTo(left, top + topLeftRadius);
            path.arcTo(new RectF(left, top, left + topLeftRadius * 2, top + topLeftRadius * 2), 180, 90);
            // path.quadTo(left, top, left + topLeftRadius, top);
            path.close();
        }


        @Override
        public void draw(Canvas canvas) {
            if (color != 0) {
                paint.setColor(color);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawPath(path, paint);
            }


            if (strokeWidth > 0) {
                paint.setColor(strokeColor);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeJoin(Paint.Join.MITER);
                paint.setStrokeWidth(strokeWidth);
                canvas.drawPath(path, paint);
            }
        }


        @Override
        public void setAlpha(int alpha) {


        }


        @Override
        public void setColorFilter(ColorFilter cf) {


        }


        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }


    }
}
