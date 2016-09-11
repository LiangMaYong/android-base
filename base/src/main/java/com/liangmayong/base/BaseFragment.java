package com.liangmayong.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.liangmayong.base.bind.BindMVP;
import com.liangmayong.base.bind.BindView;
import com.liangmayong.base.bind.Presenter;
import com.liangmayong.base.bind.annotations.BindPresenter;
import com.liangmayong.base.interfaces.AnotationTitle;
import com.liangmayong.base.interfaces.HandleBridge;
import com.liangmayong.base.presenters.BasePresenter;
import com.liangmayong.base.presenters.interfaces.BaseInterfaces;
import com.liangmayong.base.widget.themeskin.Skin;
import com.liangmayong.base.widget.toolbar.DefualtToolbar;
import com.liangmayong.preferences.Preferences;

import java.util.HashMap;

/**
 * Created by LiangMaYong on 2016/8/22.
 */
@BindPresenter({BasePresenter.class})
public abstract class BaseFragment extends Fragment implements BaseInterfaces.IView, AnotationTitle {

    //holder
    private Presenter.PresenterHolder holder = null;
    //defualtToolbar
    private DefualtToolbar defualtToolbar = null;
    //handler
    private Handler handler = new Handler();
    //rootView
    private View rootView = null;
    //activity handler
    private Handler activityHandler = null;
    //title
    private String title = "";
    //basePresenter
    private BasePresenter basePresenter = null;

    /**
     * getActivityHandler
     *
     * @return activityHandler
     */
    public Handler getActivityHandler() {
        return activityHandler;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof HandleBridge) {
            activityHandler = ((HandleBridge) activity).getHandler();
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

    /**
     * getPresenterHolder
     *
     * @return holder
     */
    public Presenter.PresenterHolder getPresenterHolder() {
        return holder;
    }

    /**
     * getBasePresenter
     *
     * @return base presenter
     */
    protected BasePresenter getBasePresenter() {
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
     * @param <T> presenterType
     * @return presenter
     */
    protected <T extends Presenter> T getPresenter(Class<T> cls) {
        if (getPresenterHolder() != null) {
            return getPresenterHolder().getPresenter(cls);
        }
        return null;
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Preferences.bind(this);
        rootView = null;
        if (getContainerViewId() > 0) {
            rootView = inflater.inflate(getContainerViewId(), null);
            BindView.parserClassByView(this, rootView);
        } else {
            rootView = BindView.parserFragment(this, container);
        }
        try {
            defualtToolbar = new DefualtToolbar(rootView);
            defualtToolbar.setTitle(getAnotationTitle());
        } catch (Exception e) {
            defualtToolbar = null;
        }
        initFragment(rootView);
        holder = BindMVP.bindPresenter(this);
        return rootView;
    }

    /**
     * getRootView
     *
     * @return rootView
     */
    public View getRootView() {
        return rootView;
    }

    /**
     * getContainerViewId
     *
     * @return containerViewId
     */
    protected int getContainerViewId() {
        return -1;
    }

    /**
     * initFragment
     *
     * @param rootView rootView
     */
    protected abstract void initFragment(View rootView);

    @Override
    public void showToast(CharSequence text) {
        getBasePresenter().showToast(text);
    }

    @Override
    public void showToast(int stringId) {
        getBasePresenter().showToast(stringId);
    }

    @Override
    public void showToast(CharSequence text, int duration) {
        getBasePresenter().showToast(text, duration);
    }

    @Override
    public void goTo(Class<? extends Activity> cls) {
        getBasePresenter().goTo(cls);
    }

    @Override
    public void goTo(Class<? extends Activity> cls, Bundle extras) {
        getBasePresenter().goTo(cls, extras);
    }

    @Override
    public void goTo(String title, String url) {
        getBasePresenter().goTo(title, url);
    }

    @Override
    public void goTo(String title, String url, HashMap<String, String> headers) {
        getBasePresenter().goTo(title, url, headers);
    }

    @Override
    public void goToForResult(Class<? extends Activity> cls, int requestCode) {
        getBasePresenter().goToForResult(cls, requestCode);
    }

    @Override
    public void goToForResult(Class<? extends Activity> cls, Bundle extras, int requestCode) {
        getBasePresenter().goToForResult(cls, extras, requestCode);
    }

    @Override
    public void refreshThemeSkin(Skin skin) {
        if (getDefualtToolbar() != null) {
            getDefualtToolbar().refreshThemeSkin(skin);
        }
    }

    @Override
    public void setThemeColor(int color) {
        getBasePresenter().setThemeColor(color);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPresenterHolder().onDettach();
    }


    @Override
    public void hideSoftKeyBoard() {
        getBasePresenter().hideSoftKeyBoard();
    }

    @Override
    public void showSoftKeyBoard(EditText editText) {
        getBasePresenter().showSoftKeyBoard(editText);
    }

    @Override
    public void addPresenter(Class<? extends Presenter>... presenterType) {
        BindMVP.addPresenter(getPresenterHolder(), presenterType);
    }

    @Override
    public void setAnotationTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAnotationTitle() {
        return title;
    }
}
