package com.liangmayong.base;

import android.annotation.TargetApi;
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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

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

    private FrameLayout frameLayout = null;
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
        View view = LayoutInflater.from(this).inflate(R.layout.base_defualt_activity, null);
        frameLayout = (FrameLayout) view.findViewById(R.id.content);
        super.setContentView(view);
        Android5497Workaround.assistActivity(this);
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ViewBinding.parserActivity(this);
        holder = PresenterBind.bind(this);
        Skin.registerSkinRefresh(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        frameLayout.addView(view);
        initToolbar();
    }

    @Override
    public void setContentView(View view) {
        frameLayout.addView(view);
        initToolbar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        frameLayout.addView(view, params);
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

    @TargetApi(19)
    private void setTranslucentStatusBar(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
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
            StatusBarCompat.setTranslucent(this);
        } else {
            int themeColor = skin.getThemeColor();
            if (isThinStatusBar()) {
                int color = Color.argb(Color.alpha(themeColor), Math.abs(Color.red(themeColor) - 0x15), Math.abs(Color.green(themeColor) - 0x15), Math.abs(Color.blue(themeColor) - 0x15));
                StatusBarCompat.setColor(this, color);
            } else {
                StatusBarCompat.setColor(this, themeColor);
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
        if (inputManager.isActive() && this.getCurrentFocus() != null) {
            if (this.getCurrentFocus().getWindowToken() != null) {
                inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void showSoftKeyBoard(final EditText editText) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                inputManager.showSoftInput(editText, 0);
            }
        }, 500);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideSoftKeyBoard();
            }
        }
        return super.dispatchTouchEvent(ev);
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
