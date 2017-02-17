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

import com.liangmayong.base.basic.interfaces.IBasic;
import com.liangmayong.base.binding.mvp.Presenter;
import com.liangmayong.base.binding.mvp.PresenterBinding;
import com.liangmayong.base.binding.mvp.PresenterHolder;
import com.liangmayong.base.binding.view.ViewBinding;
import com.liangmayong.base.binding.view.data.ViewData;
import com.liangmayong.base.support.fixbug.AndroidBug5497Workaround;
import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.support.statusbar.StatusBarCompat;
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
public abstract class AbstractBaseActivity extends AppCompatActivity implements IBasic {

    //defaultToolbar
    private DefaultToolbar defaultToolbar = null;
    private PresenterHolder presenterHolder = null;
    private InputMethodManager inputMethodManager = null;
    private final Handler handler = new Handler();
    private final List<View> mIgnoreTouchHideKeyboard = new ArrayList<View>();
    private Bundle savedInstanceState = null;
    private ExitActivityTransition activityTransition = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        if (shouldFixbug5497Workaround()) {
            AndroidBug5497Workaround.assistActivity(this, this);
        }
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        SkinManager.registerSkinRefresh(this);
        presenterHolder = PresenterBinding.bind(this);
        View view = ViewBinding.parserClassByLayout(this, this);
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

    protected abstract void onCreateAbstract(@Nullable Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.unregisterSkinRefresh(this);
        presenterHolder.onDettach();
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
     * callOnCreateAbstract
     */
    private final void callOnCreateAbstract() {
        ViewBinding.parserClassByViewSync(AbstractBaseActivity.this, getWindow().getDecorView(), new ViewBinding.OnViewBindingListener() {
            @Override
            public void onBind(ViewData data) {
                try {
                    defaultToolbar = new DefaultToolbar(AbstractBaseActivity.this);
                    if (data != null) {
                        defaultToolbar.setTitle(data.getTitle());
                    }
                    initDefaultToolbar(defaultToolbar);
                } catch (Exception e) {
                    defaultToolbar = null;
                }
                onSkinRefresh(SkinManager.get());
                onCreateAbstract(savedInstanceState);
            }
        });
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        callOnCreateAbstract();
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        callOnCreateAbstract();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        callOnCreateAbstract();
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
    public void initDefaultToolbar(DefaultToolbar defaultToolbar) {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////  Skin     ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onSkinRefresh(ISkin skin) {
        StatusBarCompat.setTransparent(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isStatusBarTranslucent()) {
                StatusBarCompat.setTransparent(this);
            } else {
                int themeColor = skin.getThemeColor();
                if (isStatusBarDark()) {
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
        if (!isStatusBarTranslucent()) {
            StatusBarCompat.compat(this, color);
        }
    }

    /**
     * isStatusBarTranslucent
     *
     * @return true or false
     */
    protected boolean isStatusBarTranslucent() {
        return false;
    }

    /**
     * isStatusBarDark
     *
     * @return true or false
     */
    protected boolean isStatusBarDark() {
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
    public void goTo(String title, String url) {
        GoToUtils.goTo(this, title, url);
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
    public void showSoftKeyBoard(final EditText editText) {
        try {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    inputMethodManager.showSoftInput(editText, 0);
                }
            }, 500);
        } catch (Exception e) {
        }
    }

    @Override
    public void ignoreTouchHideSoftKeyboard(View view) {
        if (!mIgnoreTouchHideKeyboard.contains(view)) {
            mIgnoreTouchHideKeyboard.add(view);
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
    public PresenterHolder getPresenterHolder() {
        return presenterHolder;
    }

    @Override
    public <T extends Presenter> T getPresenter(Class<T> cls) {
        if (presenterHolder == null) {
            return null;
        }
        return presenterHolder.getPresenter(cls);
    }

    @Override
    public void addPresenter(Class<? extends Presenter>... presenterType) {
        if (presenterHolder != null) {
            PresenterBinding.bind(presenterHolder, presenterType);
        }
    }
}
