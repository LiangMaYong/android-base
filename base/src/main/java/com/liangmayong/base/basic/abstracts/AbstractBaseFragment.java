package com.liangmayong.base.basic.abstracts;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.liangmayong.base.R;
import com.liangmayong.base.basic.interfaces.IBasic;
import com.liangmayong.base.binding.mvp.Presenter;
import com.liangmayong.base.binding.mvp.PresenterBinding;
import com.liangmayong.base.binding.mvp.PresenterHolder;
import com.liangmayong.base.binding.view.ViewBinding;
import com.liangmayong.base.binding.view.data.ViewData;
import com.liangmayong.base.support.fixbug.AndroidBug5497Workaround;
import com.liangmayong.base.support.logger.Logger;
import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.support.utils.GoToUtils;
import com.liangmayong.base.support.utils.ThreadPoolUtils;
import com.liangmayong.base.support.utils.ToastUtils;

/**
 * Created by LiangMaYong on 2016/12/29.
 */
public abstract class AbstractBaseFragment extends Fragment implements IBasic {

    // threadPool
    private final ThreadPoolUtils threadPool = new ThreadPoolUtils(ThreadPoolUtils.Type.CachedThread, 5);
    private DefaultToolbar defaultToolbar = null;
    private PresenterHolder presenterHolder = null;
    private LinearLayout rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long start_time = System.currentTimeMillis();
        presenterHolder = PresenterBinding.bind(this);
        long end_time = System.currentTimeMillis();
        Logger.d("BindPresenter " + getClass().getName() + ":+" + (end_time - start_time) + "ms " + start_time + " to " + end_time);
        SkinManager.registerSkinRefresh(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenterHolder != null) {
            presenterHolder.onDettach();
        }
        SkinManager.unregisterSkinRefresh(this);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            long start_time = System.currentTimeMillis();
            rootView = new LinearLayout(inflater.getContext());
            rootView.setPadding(0, 0, 0, 0);
            rootView.setId(R.id.base_default_fragment_content);
            rootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            View view = getContaierView(inflater, container, savedInstanceState);
            if (view == null) {
                if (getContaierLayoutId() > 0) {
                    view = inflater.inflate(getContaierLayoutId(), container, false);
                } else {
                    view = ViewBinding.parserClassByLayout(this, inflater.getContext());
                }
            }
            onSkinRefresh(SkinManager.get());
            view.setVisibility(View.VISIBLE);
            rootView.addView(view);
            if (shouldFixbug5497Workaround()) {
                AndroidBug5497Workaround.assistView(rootView, this);
            }
            onCallInitViewAndData(rootView);
            long end_time = System.currentTimeMillis();
            Logger.d("Displayed " + getClass().getName() + ":+" + (end_time - start_time) + "ms " + start_time + " to " + end_time);
        }
        return rootView;
    }

    protected boolean shouldFixbug5497Workaround() {
        return true;
    }

    /**
     * setTitle
     *
     * @param title title
     */
    protected void setTitle(CharSequence title) {
        if (title != null && getDefaultToolbar() != null) {
            getDefaultToolbar().setTitle(title.toString());
        }
    }

    /**
     * setTitle
     *
     * @param titleId titleId
     */
    protected void setTitle(int titleId) {
        if (getDefaultToolbar() != null) {
            getDefaultToolbar().setTitle(titleId);
        }
    }

    /**
     * get contaier layout Id
     *
     * @return viewId
     */
    protected int getContaierLayoutId() {
        return 0;
    }

    /**
     * getContaierView
     *
     * @param inflater           inflater
     * @param container          container
     * @param savedInstanceState savedInstanceState
     * @return
     */
    protected View getContaierView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    /**
     * onCreateViewAbstract
     */
    protected void onCreateViewAbstract(final View rootView) {
        ViewBinding.parserClassByView(AbstractBaseFragment.this, rootView, new ViewBinding.OnViewBindingListener() {
            @Override
            public void onBind(ViewData data) {
                try {
                    defaultToolbar = new DefaultToolbar(rootView);
                    if (data != null) {
                        defaultToolbar.setTitle(data.getTitle());
                    }
                    initDefaultToolbar(defaultToolbar);
                } catch (Exception e) {
                    defaultToolbar = null;
                }
                mHandler.postDelayed(callInitViewAndData, getCallInitDelayTime());
            }
        });
    }

    /**
     * onInitView
     */
    protected abstract void initViews(View containerView);

    /**
     * initDataOnThread
     */
    protected void initDataOnThread() {
    }


    /**
     * initArguments
     *
     * @param extras extras
     * @return fragment
     */
    public AbstractBaseFragment initArguments(Bundle extras) {
        setArguments(extras);
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////    Delay   ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                initViews(rootView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable callInitViewAndData = new Runnable() {
        @Override
        public void run() {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    initDataOnThread();
                    mHandler.sendEmptyMessage(0);
                }
            });
        }
    };

    protected int getCallInitDelayTime() {
        return 0;
    }

    private void onCallInitViewAndData(View rootView) {
        onCreateViewAbstract(rootView);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            mHandler.removeCallbacks(callInitViewAndData);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////  Toolbar   ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public DefaultToolbar getDefaultToolbar() {
        return defaultToolbar;
    }

    @Override
    public void initDefaultToolbar(DefaultToolbar defaultToolbar) {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////  Skin     ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onSkinRefresh(ISkin skin) {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////  Toast   ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void showToast(CharSequence text) {
        ToastUtils.showToast(text);
    }

    @Override
    public void showToast(int stringId) {
        ToastUtils.showToast(stringId);
    }

    @Override
    public void showToast(CharSequence text, int duration) {
        ToastUtils.showToast(text, duration);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////   Go to   ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void goTo(Class<? extends Activity> cls) {
        GoToUtils.goTo(getActivity(), cls);
    }

    @Override
    public void goTo(Class<? extends Activity> cls, Bundle extras) {
        GoToUtils.goTo(getActivity(), cls, extras);
    }

    @Override
    public void goTo(String title, String url) {
        GoToUtils.goTo(getActivity(), title, url);
    }

    @Override
    public void goToForResult(Class<? extends Activity> cls, int requestCode) {
        GoToUtils.goToForResult(getActivity(), cls, null, requestCode);
    }

    @Override
    public void goToForResult(Class<? extends Activity> cls, Bundle extras, int requestCode) {
        GoToUtils.goToForResult(getActivity(), cls, extras, requestCode);
    }

    @Override
    public void goHome() {
        GoToUtils.goHome(getActivity());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////// KeyBoard  ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void hideSoftKeyBoard() {
        if (getActivity() instanceof IBasic) {
            ((IBasic) getActivity()).hideSoftKeyBoard();
        }
    }

    @Override
    public void showSoftKeyBoard(final EditText editText) {
        if (getActivity() instanceof IBasic) {
            ((IBasic) getActivity()).showSoftKeyBoard(editText);
        }
    }

    @Override
    public void ignoreTouchHideSoftKeyboard(View view) {
        if (getActivity() instanceof IBasic) {
            ((IBasic) getActivity()).ignoreTouchHideSoftKeyboard(view);
        }
    }

    @Override
    public void onSoftKeyboardStateChange(boolean visible) {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////// Presenter ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public PresenterHolder getPresenterHolder() {
        return presenterHolder;
    }

    @Override
    public <T extends Presenter> T getPresenter(Class<T> cls) {
        if (presenterHolder == null) {
            return null;
        }
        return presenterHolder.getPresenter(cls);
    }

    @Override
    public void addPresenter(Class<? extends Presenter>... presenterType) {
        if (presenterHolder != null) {
            PresenterBinding.bind(presenterHolder, presenterType);
        }
    }
}
