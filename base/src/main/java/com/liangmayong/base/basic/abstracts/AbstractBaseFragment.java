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
import com.liangmayong.base.binding.view.data.ViewBindingData;
import com.liangmayong.base.support.fixbug.AndroidBug5497Workaround;
import com.liangmayong.base.support.logger.Logger;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenterHolder == null) {
            long start_time = System.currentTimeMillis();
            presenterHolder = PresenterBinding.bind(this);
            long end_time = System.currentTimeMillis();
            Logger.d("BindPresenter " + getClass().getName() + ":+" + (end_time - start_time) + "ms " + start_time + " to " + end_time);
        }
        SkinManager.registerSkinRefresh(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenterHolder != null) {
            presenterHolder.onDettach();
            presenterHolder = null;
        }
        SkinManager.unregisterSkinRefresh(this);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        long start_time = System.currentTimeMillis();
        LinearLayout rootView = new LinearLayout(inflater.getContext());
        rootView.setPadding(0, 0, 0, 0);
        rootView.setId(R.id.base_default_fragment_content);
        rootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootView.setOrientation(LinearLayout.VERTICAL);
        View view = getContaierView(inflater, container, savedInstanceState);
        if (view == null) {
            if (getContaierLayoutId() > 0) {
                view = inflater.inflate(getContaierLayoutId(), container, false);
            } else {
                view = ViewBinding.parserClassByLayout(this, container);
            }
        }
        long end_time = System.currentTimeMillis();
        Logger.d("Displayed " + getClass().getName() + ":+" + (end_time - start_time) + "ms " + start_time + " to " + end_time);
        rootView.addView(view);
        rootView.setVisibility(View.VISIBLE);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SkinManager.refresh(this);
        if (shouldFixbug5497Workaround()) {
            AndroidBug5497Workaround.assistView(view, this);
        }
        ViewBindingData data = ViewBinding.parserClassByView(AbstractBaseFragment.this, view);
        try {
            defaultToolbar = new DefaultToolbar(view);
            if (data != null) {
                defaultToolbar.setTitle(data.getTitle());
            }
            onInitDefaultToolbar(defaultToolbar);
        } catch (Exception e) {
            defaultToolbar = null;
        }
        initViews(view);
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
     * onInitView
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
    /////////////////////////////////////////////////////  Toolbar   ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public DefaultToolbar getDefaultToolbar() {
        return defaultToolbar;
    }

    @Override
    public void onInitDefaultToolbar(DefaultToolbar defaultToolbar) {
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
