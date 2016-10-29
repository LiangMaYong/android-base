package com.liangmayong.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.liangmayong.base.activitys.WebActivity;
import com.liangmayong.base.fixbug.AndroidBug5497Workaround;
import com.liangmayong.base.interfaces.BaseInterface;
import com.liangmayong.base.presenter.BindP;
import com.liangmayong.base.presenter.Presenter;
import com.liangmayong.base.presenter.PresenterBind;
import com.liangmayong.base.presenter.PresenterHolder;
import com.liangmayong.base.utils.ToastUtils;
import com.liangmayong.base.viewbinding.ViewBinding;
import com.liangmayong.base.viewbinding.interfaces.TitleBindInterface;
import com.liangmayong.base.widget.statusbar.StatusBarCompat;
import com.liangmayong.base.widget.toolbar.DefualtToolbar;
import com.liangmayong.base.widget.skin.Skin;


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
        AndroidBug5497Workaround.assistActivity(this);
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ViewBinding.parserActivity(this);
        holder = PresenterBind.bind(this);
        Skin.registerSkinRefresh(this);
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
        return true;
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
        ToastUtils.showToast(getString(stringId));
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
    public void onRefreshSkin(Skin skin) {
        if (isTranslucentStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompat.translucentStatusBar(this, true);
        } else {
            int themeColor = skin.getThemeColor();
            if (isThinStatusBar()) {
                int color = Color.argb(Color.alpha(themeColor), Math.abs(Color.red(themeColor) - 0x15), Math.abs(Color.green(themeColor) - 0x15), Math.abs(Color.blue(themeColor) - 0x15));
                StatusBarCompat.setStatusBarColor(this, color);
            } else {
                StatusBarCompat.setStatusBarColor(this, themeColor);
            }
        }
    }

    public void goTo(Class<? extends Activity> cls) {
        goToForResult(cls, null, -1);
    }

    public void goTo(Class<? extends Activity> cls, Bundle extras) {
        goToForResult(cls, extras, -1);
    }

    public void goHome() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void goTo(String title, String url) {
        Bundle extras = new Bundle();
        extras.putString(BaseInterface.WEB_EXTRA_TITLE, title);
        extras.putString(BaseInterface.WEB_EXTRA_URL, url);
        goTo(WebActivity.class, extras);
    }

    public void goToForResult(Class<? extends Activity> cls, int requestCode) {
        goToForResult(cls, null, requestCode);
    }

    public void goToForResult(Class<? extends Activity> cls, Bundle extras, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        this.startActivityForResult(intent, requestCode);
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
                inputManager.showSoftInput(editText, 0);
            }
        }, 500);
    }
}
