package com.liangmayong.base.basic.abstracts;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.liangmayong.base.R;
import com.liangmayong.base.airbus.AirBus;
import com.liangmayong.base.basic.interfaces.IBase;
import com.liangmayong.base.binding.mvp.Presenter;
import com.liangmayong.base.binding.mvp.PresenterBinding;
import com.liangmayong.base.binding.view.ViewBinding;
import com.liangmayong.base.support.fixbug.AndroidBug5497Workaround;
import com.liangmayong.base.support.logger.Logger;
import com.liangmayong.base.support.permission.FragmentPermission;
import com.liangmayong.base.support.theme.Theme;
import com.liangmayong.base.support.theme.ThemeManager;
import com.liangmayong.base.support.toolbar.DefaultToolbar;
import com.liangmayong.base.support.utils.GoToUtils;
import com.liangmayong.base.support.utils.ToastUtils;

/**
 * Created by LiangMaYong on 2016/12/29.
 */
public abstract class AbstractBaseFragment extends Fragment implements IBase {

    private DefaultToolbar defaultToolbar = null;
    private PresenterBinding presenterBinding = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenterBinding == null) {
            long start_time = System.currentTimeMillis();
            presenterBinding = PresenterBinding.binding(this);
            long end_time = System.currentTimeMillis();
            Logger.d("BindPresenter " + getClass().getName() + ":+" + (end_time - start_time) + "ms " + start_time + " to " + end_time);
        }
        ThemeManager.registerThemeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenterBinding != null) {
            presenterBinding.unbinding();
            presenterBinding = null;
        }
        ThemeManager.unregisterThemeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AirBus.getDefault().unregister(this);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AirBus.getDefault().register(this);
        long start_time = System.currentTimeMillis();
        LinearLayout rootView = new LinearLayout(inflater.getContext());
        rootView.setPadding(0, 0, 0, 0);
        rootView.setId(R.id.base_default_fragment_content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rootView.setBackgroundColor(inflater.getContext().getColor(R.color.base_default_layout_bg_color));
        } else {
            rootView.setBackgroundColor(inflater.getContext().getResources().getColor(R.color.base_default_layout_bg_color));
        }
        rootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootView.setOrientation(LinearLayout.VERTICAL);
        View view = getContaierView(inflater, container, savedInstanceState);
        if (view == null) {
            if (getContaierLayoutId() > 0) {
                view = inflater.inflate(getContaierLayoutId(), container, false);
            } else {
                view = ViewBinding.parserLayoutByObject(this, container);
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
        ThemeManager.notifyTheme(this);
        if (shouldFixbug5497Workaround()) {
            AndroidBug5497Workaround.assistView(view, this);
        }
        ViewBinding.Data data = ViewBinding.parserViewByObject(AbstractBaseFragment.this, view, AbstractBaseFragment.class.getSuperclass());
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
    /////////////////////////////////////////////////////  Temp   ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * getTemp
     *
     * @param key key
     * @return temp
     */
    public <T> T getTemp(String key) {
        if (getActivity() instanceof IBase) {
            return ((IBase) getActivity()).getTemp(key);
        }
        return null;
    }

    /**
     * setTemp
     *
     * @param key       key
     * @param temporary temporary
     */
    public void setTemp(String key, Object temporary) {
        if (getActivity() instanceof IBase) {
            ((IBase) getActivity()).setTemp(key, temporary);
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
    public void onInitDefaultToolbar(DefaultToolbar defaultToolbar) {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////  Theme     ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onThemeEdited(Theme theme) {
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
        if (getActivity() instanceof IBase) {
            ((IBase) getActivity()).hideSoftKeyBoard();
        }
    }

    @Override
    public void showSoftKeyBoard(final EditText editText, long delay) {
        if (getActivity() instanceof IBase) {
            ((IBase) getActivity()).showSoftKeyBoard(editText, delay);
        }
    }

    @Override
    public void ignoreTouchHideSoftKeyboard(View view) {
        if (getActivity() instanceof IBase) {
            ((IBase) getActivity()).ignoreTouchHideSoftKeyboard(view);
        }
    }

    @Override
    public void touchHideSoftKeyboard(View view) {
        if (getActivity() instanceof IBase) {
            ((IBase) getActivity()).touchHideSoftKeyboard(view);
        }
    }

    @Override
    public void onSoftKeyboardStateChange(boolean visible) {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////// Presenter ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public <T extends Presenter> T getPresenter(Class<T> cls) {
        if (presenterBinding == null) {
            return null;
        }
        return presenterBinding.getPresenter(cls);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FragmentPermission.handleResult(requestCode, permissions, grantResults);
    }
}
