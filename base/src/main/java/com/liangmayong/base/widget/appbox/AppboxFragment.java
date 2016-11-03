package com.liangmayong.base.widget.appbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.liangmayong.base.BasePresenter;
import com.liangmayong.base.ui.activitys.WebActivity;
import com.liangmayong.base.widget.interfaces.BaseInterface;
import com.liangmayong.base.widget.binding.annotations.BindP;
import com.liangmayong.base.widget.binding.Presenter;
import com.liangmayong.base.widget.binding.PresenterBind;
import com.liangmayong.base.widget.binding.PresenterHolder;
import com.liangmayong.base.utils.ToastUtils;
import com.liangmayong.base.widget.binding.ViewBinding;
import com.liangmayong.base.widget.binding.interfaces.TitleBindInterface;
import com.liangmayong.base.widget.toolbar.DefualtToolbar;
import com.liangmayong.base.widget.skin.Skin;

/**
 * Created by LiangMaYong on 2016/8/22.
 */
@BindP({BasePresenter.class})
public abstract class AppboxFragment extends ContextThemeWrapper implements BaseInterface, TitleBindInterface {
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

    public void onCreate(@Nullable Bundle savedInstanceState) {
        holder = PresenterBind.bind(this);
        Skin.registerSkinRefresh(this);
    }

    public final View onCreateView(Context context, LayoutInflater inflater) {
        rootView = null;
        if (generateContainerViewId() > 0) {
            rootView = inflater.inflate(generateContainerViewId(), null);
            ViewBinding.parserClassByView(this, rootView);
        } else {
            rootView = ViewBinding.parserClass(this, getContext());
        }
        try {
            defualtToolbar = new DefualtToolbar(rootView);
            defualtToolbar.setTitle(getAnotationTitle());
        } catch (Exception e) {
            defualtToolbar = null;
        }
        inputManager = (InputMethodManager) getHostActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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

    public void onDestroy() {
        Skin.unregisterSkinRefresh(this);
        getPresenterHolder().onDettach();
    }

    @Override
    public void goTo(Class<? extends Activity> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    public void goTo(Class<? extends Activity> cls, Bundle extras) {
        Intent intent = new Intent(this, cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    @Override
    public void goHome() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void goTo(String title, String url) {
        Bundle extras = new Bundle();
        extras.putString(BaseInterface.WEB_EXTRA_TITLE, title);
        extras.putString(BaseInterface.WEB_EXTRA_URL, url);
        Intent intent = new Intent(this, WebActivity.class);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    @Override
    @Deprecated
    public void goToForResult(Class<? extends Activity> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    @Deprecated
    public void goToForResult(Class<? extends Activity> cls, Bundle extras, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }


    public void hideSoftKeyBoard() {
        if (inputManager.isActive() && getHostActivity().getCurrentFocus() != null) {
            if (getHostActivity().getCurrentFocus().getWindowToken() != null) {
                inputManager.hideSoftInputFromWindow(getHostActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // mActivity
    private Activity mActivity;
    // mView
    private View mView;
    // mExtras
    private Bundle mExtras;

    @Override
    protected final void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    /**
     * attachActivity
     *
     * @param activity activity
     */
    protected void onAttach(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * setArguments
     *
     * @param extras extras
     */
    public final void setArguments(Bundle extras) {
        this.mExtras = extras;
    }

    /**
     * getArguments
     *
     * @return extras
     */
    protected Bundle getArguments() {
        if (mExtras == null) {
            return new Bundle();
        }
        return new Bundle(mExtras);
    }

    /**
     * getHostActivity
     *
     * @return activity
     */
    protected final Activity getHostActivity() {
        return mActivity;
    }

    /**
     * getContext
     *
     * @return context
     */
    public Context getContext() {
        return this;
    }

    /**
     * getView
     *
     * @return view
     */
    public final View getView() {
        if (mView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getHostActivity()).cloneInContext(this);
            mView = onCreateView(this, layoutInflater);
        }
        return mView;
    }

    /**
     * onStart
     */
    public void onStart() {
    }

    /**
     * onStart
     */
    public void onResume() {
    }

    /**
     * onStart
     */
    public void onPause() {
    }

    /**
     * onStop
     */
    public void onStop() {
    }

    /**
     * onDetach
     */
    public void onDetach() {
        mActivity = null;
        mExtras = null;
    }

    /**
     * onDestroyView
     */
    public void onDestroyView() {
        mView = null;
    }
}
