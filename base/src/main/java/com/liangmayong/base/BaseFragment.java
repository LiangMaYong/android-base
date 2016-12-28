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

import com.liangmayong.base.support.base.IBase;
import com.liangmayong.base.support.binding.Presenter;
import com.liangmayong.base.support.binding.PresenterBind;
import com.liangmayong.base.support.binding.PresenterHolder;
import com.liangmayong.base.support.binding.ViewBinding;
import com.liangmayong.base.support.binding.interfaces.TitleBindInterface;
import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.utils.BaseUtils;
import com.liangmayong.base.utils.ToastUtils;
import com.liangmayong.base.widget.toolbar.DefaultToolbar;

/**
 * Created by LiangMaYong on 2016/8/22.
 */
public abstract class BaseFragment extends Fragment implements IBase, TitleBindInterface {
    //holder
    private PresenterHolder holder = null;
    //defaultToolbar
    private DefaultToolbar defaultToolbar = null;
    //handler
    private Handler handler = new Handler();
    //rootView
    private View rootView = null;
    //title
    private String title = "";
    //inputManager
    private InputMethodManager inputManager = null;
    // skin
    private ISkin skin;

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
     * getSkin
     *
     * @return skin
     */
    public ISkin getSkin() {
        if (skin == null) {
            skin = SkinManager.get();
        }
        return skin;
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
     * getDefaultToolbar
     *
     * @return defaultToolbar
     */
    @Override
    public DefaultToolbar getDefaultToolbar() {
        return defaultToolbar;
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
        SkinManager.registerSkinRefresh(this);
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onCreateView();
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            ViewBinding.parserClassByView(this, rootView);
        } else {
            if (generateContainerViewId() > 0) {
                rootView = inflater.inflate(generateContainerViewId(), null);
                ViewBinding.parserClassByView(this, rootView);
            } else {
                rootView = ViewBinding.parserFragment(this, container);
            }
        }
        try {
            defaultToolbar = new DefaultToolbar(rootView);
            defaultToolbar.setTitle(getAnotationTitle());
        } catch (Exception e) {
            defaultToolbar = null;
        }
        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        rootView.setVisibility(View.VISIBLE);
        initToolbar();
        initView(rootView);
        return rootView;
    }

    /**
     * initDefaultToolbar
     */
    protected void initToolbar() {
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
     * onCreateView
     */
    protected void onCreateView() {
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
        this.skin = skin;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SkinManager.unregisterSkinRefresh(this);
        getPresenterHolder().onDettach();
    }

    public void goTo(Class<? extends Activity> cls) {
        BaseUtils.goTo(getActivity(), cls);
    }

    public void goTo(Class<? extends Activity> cls, Bundle extras) {
        BaseUtils.goTo(getActivity(), cls, extras);
    }

    public void goHome() {
        BaseUtils.goHome(getActivity());
    }

    public void goTo(String title, String url) {
        BaseUtils.goTo(getActivity(), title, url);
    }

    public void goToWeb(String title, String url) {
        BaseUtils.goToWeb(getActivity(), title, url);
    }

    public void goToForResult(Class<? extends Activity> cls, int requestCode) {
        BaseUtils.goToForResult(getActivity(), cls, null, requestCode);
    }

    public void goToForResult(Class<? extends Activity> cls, Bundle extras, int requestCode) {
        BaseUtils.goToForResult(getActivity(), cls, extras, requestCode);
    }

    public void hideSoftKeyBoard() {
        try {
            if (inputManager.isActive() && getActivity() != null && getActivity().getCurrentFocus() != null) {
                if (getActivity().getCurrentFocus().getWindowToken() != null) {
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
}
