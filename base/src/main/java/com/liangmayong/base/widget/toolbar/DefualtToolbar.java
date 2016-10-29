package com.liangmayong.base.widget.toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liangmayong.base.R;
import com.liangmayong.base.widget.iconfont.IconValue;
import com.liangmayong.base.widget.iconfont.IconView;
import com.liangmayong.base.widget.skin.OnSkinRefreshListener;
import com.liangmayong.base.widget.skin.Skin;
import com.liangmayong.base.widget.skin.SkinRelativeLayout;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
public class DefualtToolbar {

    private Context context;
    private SkinRelativeLayout toolbar_layout;
    private TextView toolbar_title, toolbar_subtitle;
    private ToolbarItem toolbar_right_one, toolbar_right_two, toolbar_right_three, toolbar_right_four;
    private ToolbarItem toolbar_left_one, toolbar_left_two, toolbar_left_three, toolbar_left_four;
    private ProgressBar toolbar_progress;
    private Skin.SkinType skinType = Skin.SkinType.defualt;
    private OnSkinRefreshListener skinRefreshListener = new OnSkinRefreshListener() {
        @Override
        public void onRefreshSkin(Skin skin) {
            if (toolbar_layout != null) {
                leftOne().getIconView().setTextColor(skin.getTextColor(skinType));
                leftTwo().getIconView().setTextColor(skin.getTextColor(skinType));
                leftThree().getIconView().setTextColor(skin.getTextColor(skinType));
                leftFour().getIconView().setTextColor(skin.getTextColor(skinType));
                rightOne().getIconView().setTextColor(skin.getTextColor(skinType));
                rightTwo().getIconView().setTextColor(skin.getTextColor(skinType));
                rightThree().getIconView().setTextColor(skin.getTextColor(skinType));
                rightFour().getIconView().setTextColor(skin.getTextColor(skinType));
                toolbar_title.setTextColor(skin.getTextColor(skinType));
                toolbar_subtitle.setTextColor(skin.getTextColor(skinType));
            }
        }
    };

    public DefualtToolbar(View view) throws Exception {
        toolbar_layout = (SkinRelativeLayout) view.findViewById(R.id.default_toolbar_layout);
        if (toolbar_layout == null) {
            throw new Exception("not include base_defualt_toolbar");
        }
        toolbar_layout.setSkinRefreshListener(skinRefreshListener);
        context = view.getContext();
        toolbar_title = (TextView) view.findViewById(R.id.default_toolbar_title);
        toolbar_subtitle = (TextView) view.findViewById(R.id.default_toolbar_subtitle);
        IconView right_one = (IconView) view.findViewById(R.id.default_toolbar_right_one);
        IconView right_two = (IconView) view.findViewById(R.id.default_toolbar_right_two);
        IconView right_three = (IconView) view.findViewById(R.id.default_toolbar_right_three);
        IconView right_four = (IconView) view.findViewById(R.id.default_toolbar_right_four);
        IconView left_one = (IconView) view.findViewById(R.id.default_toolbar_left_one);
        IconView left_two = (IconView) view.findViewById(R.id.default_toolbar_left_two);
        IconView left_three = (IconView) view.findViewById(R.id.default_toolbar_left_three);
        IconView left_four = (IconView) view.findViewById(R.id.default_toolbar_left_four);
        toolbar_right_one = new ToolbarItem(right_one);
        toolbar_right_two = new ToolbarItem(right_two);
        toolbar_right_three = new ToolbarItem(right_three);
        toolbar_right_four = new ToolbarItem(right_four);
        toolbar_left_one = new ToolbarItem(left_one);
        toolbar_left_two = new ToolbarItem(left_two);
        toolbar_left_three = new ToolbarItem(left_three);
        toolbar_left_four = new ToolbarItem(left_four);
        toolbar_progress = (ProgressBar) view.findViewById(R.id.default_toolbar_progress);
    }

    /**
     * gone
     */
    private void gone() {
        toolbar_layout.setVisibility(View.GONE);
    }

    /**
     * visible
     */
    private void visible() {
        toolbar_layout.setVisibility(View.VISIBLE);
    }

    /**
     * setSkinType
     *
     * @param skinType skinType
     */
    public void setSkinType(Skin.SkinType skinType) {
        if (this.skinType != skinType) {
            this.skinType = skinType;
        }
        toolbar_layout.setSkinType(skinType);
    }

    public DefualtToolbar(Activity activity) throws Exception {
        toolbar_layout = (SkinRelativeLayout) activity.findViewById(R.id.default_toolbar_layout);
        if (toolbar_layout == null) {
            throw new Exception("not include base_defualt_toolbar");
        }
        toolbar_layout.setSkinRefreshListener(skinRefreshListener);
        context = activity;
        toolbar_title = (TextView) activity.findViewById(R.id.default_toolbar_title);
        toolbar_subtitle = (TextView) activity.findViewById(R.id.default_toolbar_subtitle);
        IconView right_one = (IconView) activity.findViewById(R.id.default_toolbar_right_one);
        IconView right_two = (IconView) activity.findViewById(R.id.default_toolbar_right_two);
        IconView right_three = (IconView) activity.findViewById(R.id.default_toolbar_right_three);
        IconView right_four = (IconView) activity.findViewById(R.id.default_toolbar_right_four);
        IconView left_one = (IconView) activity.findViewById(R.id.default_toolbar_left_one);
        IconView left_two = (IconView) activity.findViewById(R.id.default_toolbar_left_two);
        IconView left_three = (IconView) activity.findViewById(R.id.default_toolbar_left_three);
        IconView left_four = (IconView) activity.findViewById(R.id.default_toolbar_left_four);
        toolbar_right_one = new ToolbarItem(right_one);
        toolbar_right_two = new ToolbarItem(right_two);
        toolbar_right_three = new ToolbarItem(right_three);
        toolbar_right_four = new ToolbarItem(right_four);
        toolbar_left_one = new ToolbarItem(left_one);
        toolbar_left_two = new ToolbarItem(left_two);
        toolbar_left_three = new ToolbarItem(left_three);
        toolbar_left_four = new ToolbarItem(left_four);
        toolbar_progress = (ProgressBar) activity.findViewById(R.id.default_toolbar_progress);
    }

    /**
     * setTitle
     *
     * @param title title
     */
    public void setTitle(String title) {
        if (toolbar_title != null) {
            if ("".equals(title) || title == null) {
                toolbar_title.setVisibility(View.GONE);
            } else {
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(title);
            }
        }
    }

    /**
     * setTitle
     *
     * @param resId resId
     */
    public void setTitle(int resId) {
        setTitle(context.getString(resId));
    }


    /**
     * setSubTitle
     *
     * @param subtitle subtitle
     */
    public void setSubTitle(String subtitle) {
        if (toolbar_subtitle != null) {
            if ("".equals(subtitle) || subtitle == null) {
                toolbar_subtitle.setVisibility(View.GONE);
            } else {
                toolbar_subtitle.setVisibility(View.VISIBLE);
                toolbar_subtitle.setText(subtitle);
            }
        }
    }

    /**
     * setSubTitle
     *
     * @param resId resId
     */
    public void setSubTitle(int resId) {
        setSubTitle(context.getString(resId));
    }

    /**
     * setProgress
     *
     * @param progress progress
     */
    public void setProgress(int progress) {
        if (toolbar_progress != null) {
            toolbar_progress.setProgress(progress);
        }
    }

    /**
     * leftOne
     *
     * @return leftOne
     */
    public ToolbarItem leftOne() {
        return toolbar_left_one;
    }

    /**
     * leftTwo
     *
     * @return leftTwo
     */
    public ToolbarItem leftTwo() {
        return toolbar_left_two;
    }

    /**
     * leftThree
     *
     * @return leftThree
     */
    public ToolbarItem leftThree() {
        return toolbar_left_three;
    }

    /**
     * leftFour
     *
     * @return leftFour
     */
    public ToolbarItem leftFour() {
        return toolbar_left_four;
    }

    /**
     * rightOne
     *
     * @return rightOne
     */
    public ToolbarItem rightOne() {
        return toolbar_right_one;
    }

    /**
     * rightTwo
     *
     * @return rightTwo
     */
    public ToolbarItem rightTwo() {
        return toolbar_right_two;
    }

    /**
     * rightThree
     *
     * @return rightThree
     */
    public ToolbarItem rightThree() {
        return toolbar_right_three;
    }

    /**
     * rightFour
     *
     * @return rightFour
     */
    public ToolbarItem rightFour() {
        return toolbar_right_four;
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
         * @param bold bold
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

        /**
         * toolbar
         *
         * @return toolbar
         */
        public DefualtToolbar toolbar() {
            return DefualtToolbar.this;
        }

        /**
         * hideBackground
         *
         * @return toolbar
         */
        public ToolbarItem hideBackground() {
            getIconView().setBackgroundColor(0x00ffffff);
            return this;
        }

        /**
         * showBackground
         *
         * @return toolbar
         */
        public ToolbarItem showBackground() {
            getIconView().setBackgroundResource(R.drawable.base_defualt_item_selector);
            return this;
        }

        /**
         * getIconView
         *
         * @return iconview
         */
        public IconView getIconView() {
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
