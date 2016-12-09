package com.liangmayong.base;

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

import com.liangmayong.base.support.base.IBase;
import com.liangmayong.base.support.binding.Presenter;
import com.liangmayong.base.support.binding.PresenterBind;
import com.liangmayong.base.support.binding.PresenterHolder;
import com.liangmayong.base.support.binding.ViewBinding;
import com.liangmayong.base.support.binding.annotations.BindP;
import com.liangmayong.base.support.binding.interfaces.TitleBindInterface;
import com.liangmayong.base.support.fixbug.Android5497Workaround;
import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.support.statusbar.StatusBarCompat;
import com.liangmayong.base.utils.BaseUtils;
import com.liangmayong.base.utils.ToastUtils;
import com.liangmayong.base.widget.toolbar.DefaultToolbar;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
@BindP({BasePresenter.class})
public class BaseActivity extends AppCompatActivity implements IBase, TitleBindInterface {

    //holder
    private PresenterHolder holder = null;
    //defaultToolbar
    private DefaultToolbar defaultToolbar = null;
    //title
    private String title = "";
    //handler
    private final Handler handler = new Handler();
    //inputManager
    private InputMethodManager inputManager = null;
    //ignoreHideKeyboard
    private final List<View> ignoreHideKeyboard = new ArrayList<View>();

    /**
     * ignoreClickHideKeyboard
     *
     * @param view view
     */
    public void ignoreClickHideKeyboard(View view) {
        if (!ignoreHideKeyboard.contains(view)) {
            ignoreHideKeyboard.add(view);
        }
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
        if (title != null && getDefaultToolbar() != null) {
            getDefaultToolbar().setTitle(titleId);
        }
    }

    /**
     * postDelayed
     *
     * @param runnable    runnable
     * @param delayMillis delayMillis
     */
    public void postDelayed(Runnable runnable, long delayMillis) {
        handler.postDelayed(runnable, delayMillis);
    }

    /**
     * removeCallbacks
     *
     * @param runnable runnable
     */
    public void removeCallbacks(Runnable runnable) {
        handler.removeCallbacks(runnable);
    }

    /**
     * getDefaultToolbar
     *
     * @return defaultToolbar
     */
    @Override
    public DefaultToolbar getDefaultToolbar() {
        return defaultToolbar;
    }

    @Override
    public void addPresenter(Class<? extends Presenter>... presenterType) {
        PresenterBind.bind(getPresenterHolder(), presenterType);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Android5497Workaround.assistActivity(this);
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ViewBinding.parserActivity(this);
        holder = PresenterBind.bind(this);
        SkinManager.registerSkinRefresh(this);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initToolbar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initToolbar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        initToolbar();
    }

    private void initToolbar() {
        ViewBinding.parserClassByView(this, getWindow().getDecorView());
        try {
            defaultToolbar = new DefaultToolbar(this);
            defaultToolbar.setTitle(getAnotationTitle() != null ? getAnotationTitle() : "");
        } catch (Exception e) {
            defaultToolbar = null;
        }
    }

    /**
     * isTranslucentStatusBar
     *
     * @return true or false
     */
    protected boolean isTranslucentStatusBar() {
        return false;
    }

    /**
     * isThinStatusBar
     *
     * @return true or false
     */
    protected boolean isThinStatusBar() {
        return false;
    }

    /**
     * getPresenterHolder
     *
     * @return holder
     */
    public PresenterHolder getPresenterHolder() {
        return holder;
    }

    /**
     * getPresenter
     *
     * @param cls cls
     * @param <T> type
     * @return presenter
     */
    protected <T extends Presenter> T getPresenter(Class<T> cls) {
        if (getPresenterHolder() != null) {
            return getPresenterHolder().getPresenter(cls);
        }
        return null;
    }

    @Override
    public final void showToast(CharSequence text) {
        ToastUtils.showToast(text);
    }

    @Override
    public final void showToast(int stringId) {
        ToastUtils.showToast(stringId);
    }

    @Override
    public final void showToast(CharSequence text, int duration) {
        ToastUtils.showToast(text, duration);
    }

    @Override
    protected void onDestroy() {
        SkinManager.unregisterSkinRefresh(this);
        getPresenterHolder().onDettach();
        super.onDestroy();
        hideSoftKeyBoard();
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideSoftKeyBoard();
    }

    @Override
    public void setAnotationTitle(String title) {
        this.title = title;
        if (title != null && getDefaultToolbar() != null) {
            getDefaultToolbar().setTitle(title.toString());
        }
    }

    @Override
    public String getAnotationTitle() {
        return title;
    }

    @Override
    public void onSkinRefresh(ISkin skin) {
        if (isTranslucentStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompat.setTransparent(this);
        } else {
            int themeColor = skin.getThemeColor();
            if (isThinStatusBar()) {
                int color = Color.argb(Color.alpha(themeColor), Math.abs(Color.red(themeColor) - 0x15), Math.abs(Color.green(themeColor) - 0x15), Math.abs(Color.blue(themeColor) - 0x15));
                StatusBarCompat.compat(this, color);
            } else {
                StatusBarCompat.compat(this, themeColor);
            }
        }
    }

    public void goTo(Class<? extends Activity> cls) {
        BaseUtils.goTo(this, cls);
    }

    public void goTo(Class<? extends Activity> cls, Bundle extras) {
        BaseUtils.goTo(this, cls, extras);
    }

    public void goHome() {
        BaseUtils.goHome(this);
    }

    public void goTo(String title, String url) {
        BaseUtils.goTo(this, title, url);
    }

    public void goToWeb(String title, String url) {
        BaseUtils.goToWeb(this, title, url);
    }

    public void goToForResult(Class<? extends Activity> cls, int requestCode) {
        BaseUtils.goToForResult(this, cls, null, requestCode);
    }

    public void goToForResult(Class<? extends Activity> cls, Bundle extras, int requestCode) {
        BaseUtils.goToForResult(this, cls, extras, requestCode);
    }

    public void hideSoftKeyBoard() {
        try {
            if (inputManager.isActive() && this.getCurrentFocus() != null) {
                if (this.getCurrentFocus().getWindowToken() != null) {
                    inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
        }
    }

    public void showSoftKeyBoard(final EditText editText) {
        try {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    inputManager.showSoftInput(editText, 0);
                }
            }, 500);
        } catch (Exception e) {
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, event)) {
                hideSoftKeyBoard();
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * isShouldHideKeyboard
     *
     * @param v     v
     * @param event event
     * @return isShouldHideKeyboard
     */
    protected boolean isShouldHideKeyboard(View v, MotionEvent event) {
        boolean flag = false;
        for (int i = 0; i < ignoreHideKeyboard.size(); i++) {
            View view = ignoreHideKeyboard.get(i);
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
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }
}
