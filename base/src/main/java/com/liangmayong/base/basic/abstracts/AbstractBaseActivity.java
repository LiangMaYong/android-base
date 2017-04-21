package com.liangmayong.base.basic.abstracts;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.liangmayong.base.basic.interfaces.IBase;
import com.liangmayong.base.binding.mvp.Presenter;
import com.liangmayong.base.binding.mvp.PresenterBinding;
import com.liangmayong.base.binding.view.ViewBinding;
import com.liangmayong.base.support.fixbug.AndroidBug5497Workaround;
import com.liangmayong.base.support.logger.Logger;
import com.liangmayong.base.support.statusbar.StatusBarCompat;
import com.liangmayong.base.support.theme.Theme;
import com.liangmayong.base.support.theme.ThemeManager;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.support.transitions.ActivityTransition;
import com.liangmayong.base.support.transitions.ExitActivityTransition;
import com.liangmayong.base.support.utils.GoToUtils;
import com.liangmayong.base.support.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangMaYong on 2016/12/29.
 */
public abstract class AbstractBaseActivity extends AppCompatActivity implements IBase {

    //defaultToolbar
    private DefaultToolbar defaultToolbar = null;
    private PresenterBinding presenterBinding = null;
    private InputMethodManager inputMethodManager = null;
    private final Handler handler = new Handler();
    private final List<View> mIgnoreTouchHideKeyboard = new ArrayList<View>();
    private Bundle savedInstanceState = null;
    private ExitActivityTransition activityTransition = null;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        if (shouldFixbug5497Workaround()) {
            AndroidBug5497Workaround.assistActivity(this, this);
        }
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ThemeManager.registerThemeListener(this);
        if (presenterBinding == null) {
            long start_time = System.currentTimeMillis();
            presenterBinding = PresenterBinding.binding(this);
            long end_time = System.currentTimeMillis();
            Logger.d("BindPresenter " + getClass().getName() + ":+" + (end_time - start_time) + "ms " + start_time + " to " + end_time);
        }
        View view = ViewBinding.parserLayoutByObject(AbstractBaseActivity.this, (ViewGroup) getWindow().getDecorView());
        if (view != null) {
            setContentView(view);
        }
    }

    /**
     * transitionStart
     */
    protected void transitionStart(View to, View root) {
        if (to == null) {
            return;
        }
        if (root == null) {
            root = getWindow().getDecorView();
        }
        activityTransition = ActivityTransition.with(getIntent()).to(to).bg(root).start(savedInstanceState);
    }

    /**
     * transitionExit
     */
    protected void transitionExit() {
        if (activityTransition != null) {
            activityTransition.exit(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (activityTransition != null) {
            activityTransition.exit(this);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThemeManager.unregisterThemeListener(this);
        if (presenterBinding != null) {
            presenterBinding.unbinding();
            presenterBinding = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideSoftKeyBoard();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (title != null && getDefaultToolbar() != null) {
            getDefaultToolbar().setTitle(title.toString());
        }
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (getDefaultToolbar() != null) {
            getDefaultToolbar().setTitle(titleId);
        }
    }

    /**
     * callOnRebindingView
     */
    private final void callOnRebindingView() {
        final ViewBinding.Data data = ViewBinding.parserViewByObject(AbstractBaseActivity.this, getWindow().getDecorView());
        try {
            defaultToolbar = new DefaultToolbar(AbstractBaseActivity.this);
            if (data != null) {
                defaultToolbar.setTitle(data.getTitle());
            }
            onInitDefaultToolbar(defaultToolbar);
        } catch (Exception e) {
            defaultToolbar = null;
        }
        ThemeManager.notifyTheme(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        callOnRebindingView();
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        callOnRebindingView();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        callOnRebindingView();
    }

    protected boolean shouldFixbug5497Workaround() {
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////  Toolbar   ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public DefaultToolbar getDefaultToolbar() {
        return defaultToolbar;
    }

    @Override
    public void onInitDefaultToolbar(DefaultToolbar defaultToolbar) {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////  Theme     ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onThemeEdited(Theme theme) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (shouldStatusBarTranslucent()) {
                StatusBarCompat.setTransparent(this);
            } else {
                int themeColor = theme.getThemeColor();
                if (shouldStatusBarDark()) {
                    int color = Color.argb(Color.alpha(themeColor), Math.abs(Color.red(themeColor) - 0x15), Math.abs(Color.green(themeColor) - 0x15), Math.abs(Color.blue(themeColor) - 0x15));
                    setStatusBarColor(color);
                } else {
                    setStatusBarColor(themeColor);
                }
            }
        }
    }

    /**
     * setStatusBarColor
     *
     * @param color color
     */
    public void setStatusBarColor(int color) {
        if (!shouldStatusBarTranslucent()) {
            StatusBarCompat.compat(this, color);
        }
    }

    /**
     * shouldStatusBarTranslucent
     *
     * @return true or false
     */
    protected boolean shouldStatusBarTranslucent() {
        return false;
    }

    /**
     * shouldStatusBarDark
     *
     * @return true or false
     */
    protected boolean shouldStatusBarDark() {
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////  Toast   ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void showToast(CharSequence text) {
        ToastUtils.showToast(text);
    }

    @Override
    public void showToast(int stringId) {
        ToastUtils.showToast(stringId);
    }

    @Override
    public void showToast(CharSequence text, int duration) {
        ToastUtils.showToast(text, duration);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////   Go to   ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void goTo(Class<? extends Activity> cls) {
        GoToUtils.goTo(this, cls);
    }

    @Override
    public void goTo(Class<? extends Activity> cls, Bundle extras) {
        GoToUtils.goTo(this, cls, extras);
    }

    @Override
    public void goToForResult(Class<? extends Activity> cls, int requestCode) {
        GoToUtils.goToForResult(this, cls, null, requestCode);
    }

    @Override
    public void goToForResult(Class<? extends Activity> cls, Bundle extras, int requestCode) {
        GoToUtils.goToForResult(this, cls, extras, requestCode);
    }

    @Override
    public void goHome() {
        GoToUtils.goHome(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////// KeyBoard  ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (shouldTouchHideKeyboard(v, event)) {
                hideSoftKeyBoard();
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void hideSoftKeyBoard() {
        try {
            if (inputMethodManager.isActive() && this.getCurrentFocus() != null) {
                if (getCurrentFocus() != null) {
                    View view = getCurrentFocus();
                    if (view.getWindowToken() != null) {
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    view.setFocusableInTouchMode(false);
                    view.clearFocus();
                    view.setFocusableInTouchMode(true);
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void showSoftKeyBoard(final EditText editText, long delay) {
        try {
            if (delay > 1000) {
                delay = 1000;
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    inputMethodManager.showSoftInput(editText, 0);
                }
            }, delay);
        } catch (Exception e) {
        }
    }

    @Override
    public void ignoreTouchHideSoftKeyboard(View view) {
        if (!mIgnoreTouchHideKeyboard.contains(view)) {
            mIgnoreTouchHideKeyboard.add(view);
        }
    }

    @Override
    public void touchHideSoftKeyboard(View view) {
        if (!mIgnoreTouchHideKeyboard.contains(view)) {
            mIgnoreTouchHideKeyboard.remove(view);
        }
    }

    /**
     * shouldTouchHideKeyboard
     *
     * @param v     v
     * @param event event
     * @return shouldTouchHideKeyboard
     */
    protected boolean shouldTouchHideKeyboard(View v, MotionEvent event) {
        boolean flag = false;
        for (int i = 0; i < mIgnoreTouchHideKeyboard.size(); i++) {
            View view = mIgnoreTouchHideKeyboard.get(i);
            if (view != null) {
                int[] l = {0, 0};
                view.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + view.getHeight(),
                        right = left + view.getWidth();
                flag = flag || (event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom);
            }
        }
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            flag = flag || (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
            return !flag;
        }
        return false;
    }

    @Override
    public void onSoftKeyboardStateChange(boolean visible) {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////// Presenter ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public <T extends Presenter> T getPresenter(Class<T> cls) {
        if (presenterBinding == null) {
            return null;
        }
        return presenterBinding.getPresenter(cls);
    }

}
