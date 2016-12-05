package com.liangmayong.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.liangmayong.base.utils.BaseUtils;
import com.liangmayong.base.utils.ToastUtils;
import com.liangmayong.base.utils.fixbug.Android5497Workaround;
import com.liangmayong.base.widget.binding.Presenter;
import com.liangmayong.base.widget.binding.PresenterBind;
import com.liangmayong.base.widget.binding.PresenterHolder;
import com.liangmayong.base.widget.binding.ViewBinding;
import com.liangmayong.base.widget.binding.annotations.BindP;
import com.liangmayong.base.widget.binding.interfaces.TitleBindInterface;
import com.liangmayong.base.widget.interfaces.BaseInterface;
import com.liangmayong.base.widget.skin.Skin;
import com.liangmayong.base.widget.statusbar.StatusBarCompat;
import com.liangmayong.base.widget.toolbar.DefualtToolbar;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
@BindP({BasePresenter.class})
public class BaseActivity extends AppCompatActivity implements BaseInterface, TitleBindInterface {

    //holder
    private PresenterHolder holder = null;
    //defualtToolbar
    private DefualtToolbar defualtToolbar = null;
    //title
    private String title = "";
    //handler
    private final Handler handler = new Handler();
    //inputManager
    private InputMethodManager inputManager = null;


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (title != null && getDefualtToolbar() != null) {
            getDefualtToolbar().setTitle(title.toString());
        }
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (title != null && getDefualtToolbar() != null) {
            getDefualtToolbar().setTitle(titleId);
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
     * getDefualtToolbar
     *
     * @return defualtToolbar
     */
    @Override
    public DefualtToolbar getDefualtToolbar() {
        return defualtToolbar;
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
        Skin.registerSkinRefresh(this);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
            defualtToolbar = new DefualtToolbar(this);
            defualtToolbar.setTitle(getAnotationTitle() != null ? getAnotationTitle() : "");
        } catch (Exception e) {
            defualtToolbar = null;
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
        Skin.unregisterSkinRefresh(this);
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
        if (title != null && getDefualtToolbar() != null) {
            getDefualtToolbar().setTitle(title.toString());
        }
    }

    @Override
    public String getAnotationTitle() {
        return title;
    }

    @Override
    public void onSkinRefresh(Skin skin) {
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
        if (event.getAction() == MotionEvent.ACTION_UP) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, event)) {
                hideSoftKeyBoard();
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
