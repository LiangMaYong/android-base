package com.liangmayong.base.widget.toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liangmayong.base.R;
import com.liangmayong.base.support.skin.handlers.SkinType;
import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.support.skin.listeners.OnSkinRefreshListener;
import com.liangmayong.base.widget.iconfont.IconValue;
import com.liangmayong.base.widget.iconfont.IconView;
import com.liangmayong.base.widget.skinview.SkinRelativeLayout;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
public class DefaultToolbar {

    private Animation showAnimation, hiddenAnimation;
    private int anim_time = 300;
    private Context context;
    private Handler handler = new Handler();
    private SkinRelativeLayout toolbar_layout;
    private TextView toolbar_title, toolbar_subtitle;
    private ToolbarMessage message;
    private ToolbarItem toolbar_right_one, toolbar_right_two, toolbar_right_three, toolbar_right_four;
    private ToolbarItem toolbar_left_one, toolbar_left_two, toolbar_left_three, toolbar_left_four;
    private ProgressBar toolbar_progress;
    private SkinType skinType = SkinType.default_type;
    private OnSkinRefreshListener skinRefreshListener = new OnSkinRefreshListener() {
        @Override
        public void onSkinRefresh(ISkin skin) {
            if (toolbar_layout != null) {
                int textColor = skin.getTextColor(skinType);
                int textPreColor = Color.argb(Color.alpha(textColor), Math.abs(Color.red(textColor) - 0x15), Math.abs(Color.green(textColor) - 0x15), Math.abs(Color.blue(textColor) - 0x15));
                leftOne().getIconView().setTextColor(textColor, textPreColor);
                leftTwo().getIconView().setTextColor(textColor, textPreColor);
                leftThree().getIconView().setTextColor(textColor, textPreColor);
                leftFour().getIconView().setTextColor(textColor, textPreColor);
                rightOne().getIconView().setTextColor(textColor, textPreColor);
                rightTwo().getIconView().setTextColor(textColor, textPreColor);
                rightThree().getIconView().setTextColor(textColor, textPreColor);
                rightFour().getIconView().setTextColor(textColor, textPreColor);
                toolbar_title.setTextColor(textColor);
                toolbar_subtitle.setTextColor(textColor);
            }
        }
    };

    public DefaultToolbar(View view) throws Exception {
        toolbar_layout = (SkinRelativeLayout) view.findViewById(R.id.default_toolbar_layout);
        if (toolbar_layout == null) {
            throw new Exception("not include base_default_toolbar");
        }
        toolbar_layout.setSkinRefreshListener(skinRefreshListener);
        context = view.getContext();
        toolbar_title = (TextView) view.findViewById(R.id.default_toolbar_title);
        toolbar_subtitle = (TextView) view.findViewById(R.id.default_toolbar_subtitle);
        IconView textView = (IconView) view.findViewById(R.id.default_toolbar_message);
        IconView right_one = (IconView) view.findViewById(R.id.default_toolbar_right_one);
        IconView right_two = (IconView) view.findViewById(R.id.default_toolbar_right_two);
        IconView right_three = (IconView) view.findViewById(R.id.default_toolbar_right_three);
        IconView right_four = (IconView) view.findViewById(R.id.default_toolbar_right_four);
        IconView left_one = (IconView) view.findViewById(R.id.default_toolbar_left_one);
        IconView left_two = (IconView) view.findViewById(R.id.default_toolbar_left_two);
        IconView left_three = (IconView) view.findViewById(R.id.default_toolbar_left_three);
        IconView left_four = (IconView) view.findViewById(R.id.default_toolbar_left_four);
        message = new ToolbarMessage(textView);
        toolbar_right_one = new ToolbarItem(right_one);
        toolbar_right_two = new ToolbarItem(right_two);
        toolbar_right_three = new ToolbarItem(right_three);
        toolbar_right_four = new ToolbarItem(right_four);
        toolbar_left_one = new ToolbarItem(left_one);
        toolbar_left_two = new ToolbarItem(left_two);
        toolbar_left_three = new ToolbarItem(left_three);
        toolbar_left_four = new ToolbarItem(left_four);
        toolbar_progress = (ProgressBar) view.findViewById(R.id.default_toolbar_progress);
        init();
    }

    public DefaultToolbar(Activity activity) throws Exception {
        toolbar_layout = (SkinRelativeLayout) activity.findViewById(R.id.default_toolbar_layout);
        if (toolbar_layout == null) {
            throw new Exception("not include base_default_toolbar");
        }
        toolbar_layout.setSkinRefreshListener(skinRefreshListener);
        context = activity;
        toolbar_title = (TextView) activity.findViewById(R.id.default_toolbar_title);
        toolbar_subtitle = (TextView) activity.findViewById(R.id.default_toolbar_subtitle);
        IconView textView = (IconView) activity.findViewById(R.id.default_toolbar_message);
        IconView right_one = (IconView) activity.findViewById(R.id.default_toolbar_right_one);
        IconView right_two = (IconView) activity.findViewById(R.id.default_toolbar_right_two);
        IconView right_three = (IconView) activity.findViewById(R.id.default_toolbar_right_three);
        IconView right_four = (IconView) activity.findViewById(R.id.default_toolbar_right_four);
        IconView left_one = (IconView) activity.findViewById(R.id.default_toolbar_left_one);
        IconView left_two = (IconView) activity.findViewById(R.id.default_toolbar_left_two);
        IconView left_three = (IconView) activity.findViewById(R.id.default_toolbar_left_three);
        IconView left_four = (IconView) activity.findViewById(R.id.default_toolbar_left_four);
        message = new ToolbarMessage(textView);
        toolbar_right_one = new ToolbarItem(right_one);
        toolbar_right_two = new ToolbarItem(right_two);
        toolbar_right_three = new ToolbarItem(right_three);
        toolbar_right_four = new ToolbarItem(right_four);
        toolbar_left_one = new ToolbarItem(left_one);
        toolbar_left_two = new ToolbarItem(left_two);
        toolbar_left_three = new ToolbarItem(left_three);
        toolbar_left_four = new ToolbarItem(left_four);
        toolbar_progress = (ProgressBar) activity.findViewById(R.id.default_toolbar_progress);
        init();
    }

    private void init() {
        Animation hide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        hide.setDuration(anim_time);
        setHiddenAnimation(hide);
        Animation show = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        show.setDuration(anim_time);
        setShowAnimation(show);
    }

    /**
     * setHiddenAnimation
     *
     * @param animation animation
     */
    public void setHiddenAnimation(Animation animation) {
        this.hiddenAnimation = animation;
        if (this.hiddenAnimation != null) {
            hiddenAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    toolbar_layout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    /**
     * setShowAnimation
     *
     * @param animation animation
     */
    public void setShowAnimation(Animation animation) {
        this.showAnimation = animation;
    }

    /**
     * _gone
     */
    public void gone() {
        if (toolbar_layout.getVisibility() == View.VISIBLE) {
            toolbar_layout.startAnimation(hiddenAnimation);
        }
    }

    /**
     * switchVisibility
     */
    public void switchVisibility() {
        if (toolbar_layout.getVisibility() == View.GONE) {
            visible();
        } else {
            gone();
        }
    }

    /**
     * _visible
     */
    public void visible() {
        if (toolbar_layout.getVisibility() == View.GONE) {
            toolbar_layout.setVisibility(View.VISIBLE);
            toolbar_layout.startAnimation(showAnimation);
        }
    }

    /**
     * reset
     */
    public void reset() {
        leftOne().reset();
        leftTwo().reset();
        leftThree().reset();
        leftFour().reset();
        rightOne().reset();
        rightTwo().reset();
        rightThree().reset();
        rightFour().reset();
        visible();
    }

    /**
     * setSkinType
     *
     * @param skinType skinType
     */
    public void setSkinType(SkinType skinType) {
        if (this.skinType != skinType) {
            this.skinType = skinType;
        }
        toolbar_layout.setSkinType(skinType);
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
     * message
     *
     * @return message
     */
    public ToolbarMessage message() {
        return message;
    }

    /**
     * ToolbarMessage
     */
    public class ToolbarMessage {
        private IconView message;
        private int mAnimTime = 300;
        private Animation mMessageShowAnimation, mMessageShow2Animation, mMessageHiddenAnimation;
        private View.OnClickListener onClickListener = null;
        private boolean isShow = false;

        private ToolbarMessage(IconView messageView) {
            this.message = messageView;
            this.message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v);
                    }
                }
            });
            this.mMessageHiddenAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
            this.mMessageHiddenAnimation.setDuration(mAnimTime);
            this.mMessageHiddenAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    _gone();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            this.mMessageShowAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.1f);
            this.mMessageShowAnimation.setDuration(mAnimTime);
            this.mMessageShowAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    getMessageView().startAnimation(mMessageShow2Animation);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            this.mMessageShow2Animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.1f, Animation.RELATIVE_TO_SELF, 0.0f);
            this.mMessageShow2Animation.setDuration(200);
        }

        private void _visible() {
            getMessageView().setVisibility(View.VISIBLE);
            isShow = true;
        }

        private void _gone() {
            getMessageView().setVisibility(View.GONE);
            isShow = false;
        }

        private Runnable hideRun = new Runnable() {
            @Override
            public void run() {
                hide();
            }
        };

        public ToolbarMessage click(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        /**
         * show
         *
         * @param text            text
         * @param textColor       textColor
         * @param backgroundColor backgroundColor
         * @param duration        duration
         */
        public void show(String text, int textColor, int backgroundColor, int duration) {
            show(null, text, textColor, backgroundColor, duration);
        }

        /**
         * getMessageView
         *
         * @return message
         */
        public IconView getMessageView() {
            return message;
        }

        /**
         * show
         *
         * @param icon            icon
         * @param text            text
         * @param textColor       textColor
         * @param backgroundColor backgroundColor
         * @param duration        duration
         */
        public void show(IconValue icon, String text, int textColor, int backgroundColor, int duration) {
            getMessageView().setIcon(" " + text, icon, IconView.ICON_LEFT);
            getMessageView().setTextColor(textColor);
            getMessageView().setBackgroundColor(backgroundColor);
            if (!isShow) {
                _visible();
                getMessageView().startAnimation(mMessageShowAnimation);
            }
            handler.removeCallbacks(hideRun);
            if (duration != -1) {
                handler.postDelayed(hideRun, duration < 1000 ? 1000 : duration);
            }
        }

        /**
         * hide
         */
        public void hide() {
            if (isShow) {
                getMessageView().startAnimation(mMessageHiddenAnimation);
            }
        }
    }

    /**
     * ToolbarItem
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
         * reset
         */
        public void reset() {
            this.getIconView().setText("");
            this.iconToLeft(null);
            this.clicked(null);
            this.getIconView().setClickable(false);
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
        public DefaultToolbar toolbar() {
            return DefaultToolbar.this;
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
         * _visible
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
         * icon bar _gone
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
         * @return icon bar
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
         * doGet view
         *
         * @return icon bar
         */
        public IconView getView() {
            return getIconView();
        }

        /**
         * setPaddingLeft
         *
         * @param left left
         * @return icon bar
         */
        public ToolbarItem paddingLeft(int left) {
            getIconView().setPadding(left, getIconView().getPaddingTop(), getIconView().getPaddingRight(),
                    getIconView().getPaddingBottom());
            return this;
        }

        /**
         * setPaddingLeftDip
         *
         * @param left left
         * @return icon bar
         */
        public ToolbarItem paddingLeftDip(int left) {
            getIconView().setPadding(dip2Px(left), getIconView().getPaddingTop(), getIconView().getPaddingRight(),
                    getIconView().getPaddingBottom());
            return this;
        }

        /**
         * setPaddingRight
         *
         * @param right right
         * @return icon bar
         */
        public ToolbarItem paddingRight(int right) {
            getIconView().setPadding(getIconView().getPaddingLeft(), getIconView().getPaddingTop(), right,
                    getIconView().getPaddingBottom());
            return this;
        }

        /**
         * setPaddingRightDip
         *
         * @param right right
         * @return icon bar
         */
        public ToolbarItem paddingRightDip(int right) {
            getIconView().setPadding(getIconView().getPaddingLeft(), getIconView().getPaddingTop(), dip2Px(right),
                    getIconView().getPaddingBottom());
            return this;
        }

        /**
         * stop showAnimation
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
         * start showAnimation
         *
         * @param animaRes showAnimation res id
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
        private ToolbarItem drawable(Drawable leftdrawable, Drawable rightdrawable) {
            if (leftdrawable != null) {
                leftdrawable.setBounds(0, 0, leftdrawable.getMinimumWidth(), leftdrawable.getMinimumHeight());
            }
            if (rightdrawable != null) {
                rightdrawable.setBounds(0, 0, rightdrawable.getMinimumWidth(), rightdrawable.getMinimumHeight());
            }
            getIconView().setCompoundDrawables(leftdrawable, null, rightdrawable, null);
            return this;
        }


        /**
         * drawableToRight
         *
         * @param resId resId
         * @return icon bar
         */
        public ToolbarItem drawableToRight(int resId) {
            Drawable drawable = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                drawable = getIconView().getContext().getDrawable(resId);
            } else {
                drawable = getIconView().getContext().getResources().getDrawable(resId);
            }
            drawable(null, drawable);
            return this;
        }

        /**
         * drawableToRight
         *
         * @param resId resId
         * @return icon bar
         */
        public ToolbarItem drawableToLeft(int resId) {
            Drawable drawable = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                drawable = getIconView().getContext().getDrawable(resId);
            } else {
                drawable = getIconView().getContext().getResources().getDrawable(resId);
            }
            drawable(drawable, null);
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
