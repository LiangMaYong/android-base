package com.liangmayong.base.weight.toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liangmayong.base.R;
import com.liangmayong.base.weight.iconfont.IconValue;
import com.liangmayong.base.weight.IconView;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
public class DefualtToolbar {

    private Context context;
    private RelativeLayout toolbar_layout;
    private TextView toolbar_title;
    private ToolbarItem toolbar_right_one, toolbar_right_two;
    private ToolbarItem toolbar_left_one, toolbar_left_two;
    private ProgressBar toolbar_progress;

    public DefualtToolbar(View view) throws Exception {
        toolbar_layout = (RelativeLayout) view.findViewById(R.id.default_toolbar_layout);
        if (toolbar_layout == null) {
            throw new Exception("not include defualt_toolbar");
        }
        context = view.getContext();
        toolbar_title = (TextView) view.findViewById(R.id.default_toolbar_title);
        IconView right_one = (IconView) view.findViewById(R.id.default_toolbar_right_one);
        IconView right_two = (IconView) view.findViewById(R.id.default_toolbar_right_two);
        IconView left_one = (IconView) view.findViewById(R.id.default_toolbar_left_one);
        IconView left_two = (IconView) view.findViewById(R.id.default_toolbar_left_two);
        toolbar_right_one = new ToolbarItem(right_one);
        toolbar_right_two = new ToolbarItem(right_two);
        toolbar_left_one = new ToolbarItem(left_one);
        toolbar_left_two = new ToolbarItem(left_two);
        toolbar_progress = (ProgressBar) view.findViewById(R.id.default_toolbar_progress);
    }

    public DefualtToolbar(Activity activity) throws Exception {
        toolbar_layout = (RelativeLayout) activity.findViewById(R.id.default_toolbar_layout);
        if (toolbar_layout == null) {
            throw new Exception("not include defualt_toolbar");
        }
        context = activity;
        toolbar_title = (TextView) activity.findViewById(R.id.default_toolbar_title);
        IconView right_one = (IconView) activity.findViewById(R.id.default_toolbar_right_one);
        IconView right_two = (IconView) activity.findViewById(R.id.default_toolbar_right_two);
        IconView left_one = (IconView) activity.findViewById(R.id.default_toolbar_left_one);
        IconView left_two = (IconView) activity.findViewById(R.id.default_toolbar_left_two);
        toolbar_right_one = new ToolbarItem(right_one);
        toolbar_right_two = new ToolbarItem(right_two);
        toolbar_left_one = new ToolbarItem(left_one);
        toolbar_left_two = new ToolbarItem(left_two);
        toolbar_progress = (ProgressBar) activity.findViewById(R.id.default_toolbar_progress);
    }

    public void setTitle(String title) {
        if (toolbar_title != null) {
            toolbar_title.setText(title);
        }
    }

    public void setTitle(int resId) {
        if (toolbar_title != null) {
            toolbar_title.setText(context.getString(resId));
        }
    }

    public void setProgress(int progress) {
        if (toolbar_progress != null) {
            toolbar_progress.setProgress(progress);
        }
    }

    public ToolbarItem leftOne() {
        return toolbar_left_one;
    }

    public ToolbarItem leftTwo() {
        return toolbar_left_two;
    }

    public ToolbarItem rightOne() {
        return toolbar_right_one;
    }

    public ToolbarItem rightTwo() {
        return toolbar_right_two;
    }

    public void refreshThemeColor(int color) {
        if (toolbar_layout != null) {
            toolbar_layout.setBackgroundColor(color);
        }
    }

    /**
     * icon bar
     *
     * @author LiangMaYong
     * @version 1.0
     */
    public class ToolbarItem {
        private IconView actionView;

        public int getId() {
            return actionView.getId();
        }

        /**
         * init
         *
         * @param actionView actionView
         */
        private ToolbarItem(IconView actionView) {
            this.actionView = actionView;
        }

        /**
         * textColor
         *
         * @param color   color
         * @param pressed pressed
         * @return ToolbarItem
         */
        public ToolbarItem textColor(int color, int pressed) {
            int statePressed = android.R.attr.state_pressed;
            int stateFocesed = android.R.attr.state_focused;
            int[][] state = {{statePressed}, {-statePressed}, {stateFocesed}, {-stateFocesed}};
            ColorStateList colors = new ColorStateList(state, new int[]{pressed, color, pressed, color});
            getIconView().setTextColor(colors);
            return this;
        }

        /**
         * set textColor
         *
         * @param color color
         * @return ToolbarItem
         */
        public ToolbarItem textColor(int color) {
            this.getIconView().setTextColor(color);
            return this;
        }

        /**
         * set textSize
         *
         * @param size size
         * @return ToolbarItem
         */
        public ToolbarItem textSize(float size) {
            this.getIconView().setTextSize(size);
            return this;
        }

        /**
         * bold
         *
         * @return ToolbarItem
         */
        public ToolbarItem bold(boolean bold) {
            this.getIconView().getPaint().setFakeBoldText(bold);
            this.getIconView().invalidate();
            return this;
        }

        /**
         * set textSize
         *
         * @param size size
         * @return ToolbarItem
         */
        public ToolbarItem iconSize(float size) {
            this.getIconView().setIconSize(size);
            return this;
        }

        /**
         * set iconLeft
         *
         * @param left left
         * @return ToolbarItem
         */
        public ToolbarItem iconToLeft(IconValue left) {
            this.getIconView().setIcon(this.getIconView().getText(), left, IconView.ICON_LEFT);
            return this;
        }

        /**
         * set iconRight
         *
         * @param right right
         * @return ToolbarItem
         */
        public ToolbarItem iconToRight(IconValue right) {
            this.getIconView().setIcon(this.getIconView().getText(), right, IconView.ICON_RIGHT);
            return this;
        }

        private IconView getIconView() {
            return actionView;
        }

        /**
         * visible
         *
         * @return ToolbarItem
         */
        public ToolbarItem visible() {
            if (actionView != null) {
                actionView.setVisibility(View.VISIBLE);
            }
            return this;
        }

        /**
         * icon bar gone
         *
         * @return icon bar
         */
        public ToolbarItem gone() {
            if (actionView != null) {
                actionView.setVisibility(View.GONE);
            }
            return this;
        }

        /**
         * text
         *
         * @param text text
         * @return ToolbarItem
         */
        public ToolbarItem text(CharSequence text) {
            this.getIconView().setText(text, null);
            return this;
        }

        /**
         * set enabled
         *
         * @param enabled enabled
         * @return icon bar
         */
        public ToolbarItem enabled(boolean enabled) {
            getIconView().setEnabled(enabled);
            return this;
        }

        /**
         * set clicked
         *
         * @param clickListener clickListener
         * @return icon bar
         */
        public ToolbarItem clicked(View.OnClickListener clickListener) {
            getIconView().setOnClickListener(clickListener);
            enabled(true);
            return this;
        }

        /**
         * set clicked
         *
         * @param longClickListener longClickListener
         * @return icon bar
         */
        public ToolbarItem longClicked(View.OnLongClickListener longClickListener) {
            getIconView().setOnLongClickListener(longClickListener);
            enabled(true);
            return this;
        }

        /**
         * IBActionButton VIEW
         *
         * @return IBActionButton
         */
        public IconView getView() {
            return getIconView();
        }

        /**
         * setPaddingLeft
         *
         * @param left left
         * @return ToolbarItem
         */
        public ToolbarItem setPaddingLeft(int left) {
            getIconView().setPadding(left, getIconView().getPaddingTop(), getIconView().getPaddingRight(),
                    getIconView().getPaddingBottom());
            return this;
        }

        /**
         * setPaddingLeftDip
         *
         * @param left left
         * @return ToolbarItem
         */
        public ToolbarItem setPaddingLeftDip(int left) {
            getIconView().setPadding(dip2Px(left), getIconView().getPaddingTop(), getIconView().getPaddingRight(),
                    getIconView().getPaddingBottom());
            return this;
        }

        /**
         * setPaddingRight
         *
         * @param right right
         * @return ToolbarItem
         */
        public ToolbarItem setPaddingRight(int right) {
            getIconView().setPadding(getIconView().getPaddingLeft(), getIconView().getPaddingTop(), right,
                    getIconView().getPaddingBottom());
            return this;
        }

        /**
         * setPaddingRightDip
         *
         * @param right right
         * @return ToolbarItem
         */
        public ToolbarItem setPaddingRightDip(int right) {
            getIconView().setPadding(getIconView().getPaddingLeft(), getIconView().getPaddingTop(), dip2Px(right),
                    getIconView().getPaddingBottom());
            return this;
        }

        /**
         * stop animation
         *
         * @return icon bar
         */
        public ToolbarItem stopAnimation() {
            if (getIconView() != null) {
                getIconView().stopAnimation();
            }
            return this;
        }

        /**
         * start animation
         *
         * @param animaRes animation res id
         * @return icon bar
         */
        public ToolbarItem startAnimation(int animaRes) {
            if (getIconView() != null) {
                getIconView().startAnimation(animaRes);
            }
            return this;
        }

        /**
         * set drawable
         *
         * @param leftdrawable  leftdrawable
         * @param rightdrawable rightdrawable
         * @return icon bar
         */
        public ToolbarItem drawable(Drawable leftdrawable, Drawable rightdrawable) {
            if (leftdrawable != null) {
                leftdrawable.setBounds(0, 0, leftdrawable.getMinimumWidth(), leftdrawable.getMinimumHeight());
            }
            if (rightdrawable != null) {
                rightdrawable.setBounds(0, 0, rightdrawable.getMinimumWidth(), rightdrawable.getMinimumHeight());
            }
            getIconView().setCompoundDrawables(leftdrawable, rightdrawable, null, null);
            return this;
        }

        /*
         * converts dip to px
         */
        private int dip2Px(float dip) {
            return (int) (dip * getIconView().getContext().getResources().getDisplayMetrics().density + 0.5f);
        }
    }

}
