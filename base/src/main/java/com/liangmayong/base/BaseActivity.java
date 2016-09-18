package com.liangmayong.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.liangmayong.base.interfaces.BaseInterface;
import com.liangmayong.base.widget.toolbar.DefualtToolbar;
import com.liangmayong.presenter.BindP;
import com.liangmayong.presenter.Presenter;
import com.liangmayong.presenter.PresenterBind;
import com.liangmayong.presenter.PresenterHolder;
import com.liangmayong.skin.Skin;
import com.liangmayong.viewbinding.ViewBinding;
import com.liangmayong.viewbinding.interfaces.TitleInterface;

import java.util.HashMap;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
@BindP({BasePresenter.class})
public class BaseActivity extends AppCompatActivity implements BaseInterface, TitleInterface {

    //holder
    private PresenterHolder holder = null;
    //defualtToolbar
    private DefualtToolbar defualtToolbar = null;
    //title
    private String title = "";
    //basePresenter
    private BasePresenter basePresenter = null;
    //handler
    private final Handler handler = new Handler();

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (title != null || getDefualtToolbar() != null) {
            getDefualtToolbar().setTitle(title.toString());
        }
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (title != null || getDefualtToolbar() != null) {
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
    public final void hideSoftKeyBoard() {
        getBasePresenter().hideSoftKeyBoard();
    }

    @Override
    public final void showSoftKeyBoard(EditText editText) {
        getBasePresenter().showSoftKeyBoard(editText);
    }

    @Override
    public void addPresenter(Class<? extends Presenter>... presenterType) {
        PresenterBind.bind(getPresenterHolder(), presenterType);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ViewBinding.parserActivity(this);
        try {
            defualtToolbar = new DefualtToolbar(this);
            defualtToolbar.setTitle(getAnotationTitle());
        } catch (Exception e) {
            defualtToolbar = null;
        }
        holder = PresenterBind.bind(this);
        Skin.registerSkinRefresh(this);
    }

    /**
     * isTranslucentStatus
     *
     * @return true or false
     */
    protected boolean isTranslucentStatus() {
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
     * getBasePresenter
     *
     * @return basePresenter
     */
    private BasePresenter getBasePresenter() {
        if (basePresenter == null) {
            if (getPresenterHolder() != null) {
                basePresenter = getPresenterHolder().getPresenter(BasePresenter.class);
            }
        }
        return basePresenter;
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
    private void setTranslucentStatus(boolean on) {
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
    public Activity getActivity() {
        return this;
    }

    @Override
    public final void showToast(CharSequence text) {
        getBasePresenter().showToast(text);
    }

    @Override
    public final void showToast(int stringId) {
        getBasePresenter().showToast(stringId);
    }

    @Override
    public final void showToast(CharSequence text, int duration) {
        getBasePresenter().showToast(text, duration);
    }

    @Override
    public final void goTo(Class<? extends Activity> cls) {
        getBasePresenter().goTo(cls);
    }

    @Override
    public final void goTo(Class<? extends Activity> cls, Bundle extras) {
        getBasePresenter().goTo(cls, extras);
    }

    @Override
    public final void goTo(String title, String url) {
        getBasePresenter().goTo(title, url);
    }

    @Override
    public final void goTo(String title, String url, HashMap<String, String> headers) {
        getBasePresenter().goTo(title, url, headers);
    }

    @Override
    public final void goToForResult(Class<? extends Activity> cls, int requestCode) {
        getBasePresenter().goToForResult(cls, requestCode);
    }

    @Override
    public final void goToForResult(Class<? extends Activity> cls, Bundle extras, int requestCode) {
        getBasePresenter().goToForResult(cls, extras, requestCode);
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
    }

    @Override
    public String getAnotationTitle() {
        return title;
    }

    @Override
    public void onRefreshSkin(Skin skin) {
        if (isTranslucentStatus() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.setStatusBarColor(skin.getThemeColor());
            }
        }
        if (getDefualtToolbar() != null) {
            getDefualtToolbar().onRefreshSkin(skin);
        }
    }
}
