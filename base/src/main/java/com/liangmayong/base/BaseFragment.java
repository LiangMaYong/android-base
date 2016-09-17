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

import com.liangmayong.base.widget.viewbinding.ViewBinding;
import com.liangmayong.base.interfaces.BaseInterface;
import com.liangmayong.base.widget.viewbinding.interfaces.TitleInterface;
import com.liangmayong.base.widget.themeskin.Skin;
import com.liangmayong.base.widget.toolbar.DefualtToolbar;
import com.liangmayong.presenter.BindP;
import com.liangmayong.presenter.Presenter;
import com.liangmayong.presenter.PresenterBind;
import com.liangmayong.presenter.PresenterHolder;

import java.util.HashMap;

/**
 * Created by LiangMaYong on 2016/8/22.
 */
@BindP({BasePresenter.class})
public abstract class BaseFragment extends Fragment implements BaseInterface, TitleInterface {

    //holder
    private PresenterHolder holder = null;
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
    public PresenterHolder getPresenterHolder() {
        return holder;
    }

    /**
     * getBasePresenter
     *
     * @return base presenter
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
        rootView = null;
        if (generateContainerViewId() > 0) {
            rootView = inflater.inflate(generateContainerViewId(), null);
            ViewBinding.parserClassByView(this, rootView);
        } else {
            rootView = ViewBinding.parserFragment(this, container);
        }
        try {
            defualtToolbar = new DefualtToolbar(rootView);
            defualtToolbar.setTitle(getAnotationTitle());
        } catch (Exception e) {
            defualtToolbar = null;
        }
        holder = PresenterBind.bind(this);
        Skin.registerSkinRefresh(this);
        return rootView;
    }

    /**
     * getRootView
     *
     * @return rootView
     */
    public final View getRootView() {
        return rootView;
    }

    /**
     * generateContainerViewId
     *
     * @return containerViewId
     */
    protected int generateContainerViewId() {
        return -1;
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
    public void hideSoftKeyBoard() {
        getBasePresenter().hideSoftKeyBoard();
    }

    @Override
    public void showSoftKeyBoard(EditText editText) {
        getBasePresenter().showSoftKeyBoard(editText);
    }

    @Override
    public void addPresenter(Class<? extends Presenter>... presenterType) {
        PresenterBind.bind(getPresenterHolder(), presenterType);
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
        if (getDefualtToolbar() != null) {
            getDefualtToolbar().onRefreshSkin(skin);
        }
    }

    @Override
    public void onDestroyView() {
        Skin.unregisterSkinRefresh(this);
        getPresenterHolder().onDettach();
        super.onDestroyView();
    }
}
