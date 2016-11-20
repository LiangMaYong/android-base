package com.liangmayong.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.liangmayong.base.utils.BaseUtils;
import com.liangmayong.base.utils.ToastUtils;
import com.liangmayong.base.widget.binding.Presenter;
import com.liangmayong.base.widget.binding.PresenterBind;
import com.liangmayong.base.widget.binding.PresenterHolder;
import com.liangmayong.base.widget.binding.ViewBinding;
import com.liangmayong.base.widget.binding.annotations.BindP;
import com.liangmayong.base.widget.binding.interfaces.TitleBindInterface;
import com.liangmayong.base.widget.interfaces.BaseInterface;
import com.liangmayong.base.widget.skin.Skin;
import com.liangmayong.base.widget.toolbar.DefualtToolbar;

/**
 * Created by LiangMaYong on 2016/8/22.
 */
@BindP({BasePresenter.class})
public abstract class BaseFragment extends Fragment implements BaseInterface, TitleBindInterface {
    //holder
    private PresenterHolder holder = null;
    //defualtToolbar
    private DefualtToolbar defualtToolbar = null;
    //handler
    private Handler handler = new Handler();
    //rootView
    private View rootView = null;
    //title
    private String title = "";
    //inputManager
    private InputMethodManager inputManager = null;

    /**
     * getFragmentId
     *
     * @return fragmentId
     */
    public int getFragmentId() {
        try {
            return ((View) getView().getParent()).getId();
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * extras
     *
     * @param extras extras
     * @return
     */
    public BaseFragment initArguments(Bundle extras) {
        setArguments(extras);
        return this;
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
    public PresenterHolder getPresenterHolder() {
        return holder;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        holder = PresenterBind.bind(this);
        Skin.registerSkinRefresh(this);
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createView();
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
        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        initView(rootView);
        onSkinRefresh(Skin.get());
        return rootView;
    }

    /**
     * getmRootView
     *
     * @return rootView
     */
    public final View getRootView() {
        return rootView;
    }

    /**
     * generateFragmentContainerId
     *
     * @return containerViewId
     */
    protected int generateContainerViewId() {
        return -1;
    }

    /**
     * initView
     *
     * @param rootView rootView
     */
    protected abstract void initView(View rootView);

    /**
     * createView
     */
    protected void createView() {
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
    public void addPresenter(Class<? extends Presenter>... presenterType) {
        PresenterBind.bind(getPresenterHolder(), presenterType);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Skin.unregisterSkinRefresh(this);
        getPresenterHolder().onDettach();
    }

    public void goTo(Class<? extends Activity> cls) {
        BaseUtils.goTo(getContext(), cls);
    }

    public void goTo(Class<? extends Activity> cls, Bundle extras) {
        BaseUtils.goTo(getContext(), cls, extras);
    }

    public void goHome() {
        BaseUtils.goHome(getContext());
    }

    public void goTo(String title, String url) {
        BaseUtils.goTo(getContext(), title, url);
    }

    public void goToWeb(String title, String url) {
        BaseUtils.goToWeb(getContext(), title, url);
    }

    public void goToForResult(Class<? extends Activity> cls, int requestCode) {
        BaseUtils.goToForResult(getActivity(), cls, null, requestCode);
    }

    public void goToForResult(Class<? extends Activity> cls, Bundle extras, int requestCode) {
        BaseUtils.goToForResult(getActivity(), cls, extras, requestCode);
    }

    public void hideSoftKeyBoard() {
        if (inputManager.isActive() && getActivity().getCurrentFocus() != null) {
            if (getActivity().getCurrentFocus().getWindowToken() != null) {
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
}
