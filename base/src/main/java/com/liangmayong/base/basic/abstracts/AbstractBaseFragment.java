package com.liangmayong.base.basic.abstracts;

import android.app.Activity;
import android.os.Bundle;
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
import com.liangmayong.base.support.fixbug.AndroidBug5497Workaround;
import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.support.utils.GoToUtils;
import com.liangmayong.base.support.utils.ToastUtils;

/**
 * Created by LiangMaYong on 2016/12/29.
 */
public abstract class AbstractBaseFragment extends Fragment implements IBasic {

    private DefaultToolbar defaultToolbar = null;
    private PresenterHolder presenterHolder = null;
    private String bindTitle = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenterHolder = PresenterBinding.bind(this);
        SkinManager.registerSkinRefresh(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenterHolder.onDettach();
        SkinManager.unregisterSkinRefresh(this);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = new LinearLayout(inflater.getContext());
        layout.setPadding(0, 0, 0, 0);
        layout.setId(R.id.base_default_fragment_content);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        View view = getContaierView(inflater, container, savedInstanceState);
        if (view != null) {
            ViewBinding.parserClassByView(this, view);
        } else {
            if (getContaierLayoutId() > 0) {
                view = inflater.inflate(getContaierLayoutId(), container, false);
                if (view != null) {
                    ViewBinding.parserClassByView(this, view);
                }
            } else {
                view = ViewBinding.parserFragment(this, container);
            }
        }
        try {
            defaultToolbar = new DefaultToolbar(view);
            defaultToolbar.setTitle(getBindTitle() != null ? getBindTitle() : "");
            initDefaultToolbar(defaultToolbar);
        } catch (Exception e) {
            defaultToolbar = null;
        }
        onSkinRefresh(SkinManager.get());
        onAbstractCreateView(view);
        view.setVisibility(View.VISIBLE);
        layout.addView(view);
        if (shouldFixbug5497Workaround()) {
            AndroidBug5497Workaround.assistView(layout);
        }
        return layout;
    }

    protected boolean shouldFixbug5497Workaround() {
        return true;
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
     * onAbstractCreateView
     *
     * @param containerView containerView
     */
    protected void onAbstractCreateView(View containerView) {
        initViews(containerView);
    }

    /**
     * initViews
     */
    protected abstract void initViews(View containerView);


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
    /////////////////////////////////////////////////////   Title   ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBindTitle(String title) {
        this.bindTitle = title;
        if (title != null && getDefaultToolbar() != null) {
            getDefaultToolbar().setTitle(title);
        }
    }

    @Override
    public String getBindTitle() {
        return bindTitle;
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
