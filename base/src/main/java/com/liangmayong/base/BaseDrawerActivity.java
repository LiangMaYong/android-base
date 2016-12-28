package com.liangmayong.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.liangmayong.base.sub.BaseSubFragment;
import com.liangmayong.base.sub.BaseSubFragmentManager;
import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.web.fragments.DefaultWebFragment;

/**
 * Created by LiangMaYong on 2016/11/10.
 */
public abstract class BaseDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    // mDrawerLayout
    private DrawerLayout mDrawerLayout;
    // mNavigationView
    private NavigationView mNavigationView;
    // mSubManager
    private BaseSubFragmentManager mSubManager;

    /**
     * getSubFragmentManager
     *
     * @return sub fragment manager
     */
    public BaseSubFragmentManager getSubFragmentManager() {
        return mSubManager;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_default_activity_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.base_drawer_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mDrawerLayout.setFitsSystemWindows(false);
        }
        mDrawerLayout.setScrimColor(0x20333333);
        mNavigationView = (NavigationView) findViewById(R.id.base_drawer_navigation_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mNavigationView.setFitsSystemWindows(false);
        }
        mNavigationView.inflateHeaderView(getDrawerHeadLayoutId());
        mNavigationView.inflateMenu(getDrawerMenuId());
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                BaseDrawerActivity.this.onNavigationItemSelected(item);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        BaseSubFragment fragment = getContentFragment();
        if (fragment != null) {
            mSubManager = new BaseSubFragmentManager(this, R.id.base_drawer_fragment, fragment);
            mSubManager.setCountListener(new BaseSubFragmentManager.OnFragmentCountListener() {
                @Override
                public void onFragmentCount(int count) {
                    if (count <= 1) {
                        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    } else {
                        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    }
                }
            });
        }
    }

    @Override
    public void goTo(String title, String url) {
        hideSoftKeyBoard();
        openFragment(new DefaultWebFragment(title, url));
    }

    @Override
    public void onSkinRefresh(ISkin skin) {
        super.onSkinRefresh(skin);
        if (mDrawerLayout != null) {
            mDrawerLayout.setStatusBarBackgroundColor(skin.getThemeColor());
        }
    }

    /**
     * openFragment
     *
     * @param fragment fragment
     */
    public void openFragment(BaseSubFragment fragment) {
        hideSoftKeyBoard();
        getSubFragmentManager().addFragment(fragment, 0, 0);
    }

    /**
     * openFragment
     *
     * @param fragment  fragment
     * @param enterAnim enterAnim
     * @param exitAnim  exitAnim
     */
    public void openFragment(BaseSubFragment fragment, int enterAnim, int exitAnim) {
        hideSoftKeyBoard();
        getSubFragmentManager().addFragment(fragment, 0, 0);
    }


    /**
     * replaceFragment
     *
     * @param fragment  fragment
     * @param enterAnim enterAnim
     * @param exitAnim  exitAnim
     */
    public void replaceFragment(BaseSubFragment fragment, int enterAnim, int exitAnim) {
        hideSoftKeyBoard();
        getSubFragmentManager().replaceFragment(fragment, enterAnim, exitAnim);
    }


    /**
     * openFragment
     *
     * @param fragment fragment
     */
    public void replaceFragment(BaseSubFragment fragment) {
        hideSoftKeyBoard();
        getSubFragmentManager().replaceFragment(fragment, 0, 0);
    }

    /**
     * openDrawer
     */
    public void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    /**
     * closeDrawer
     */
    public void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * isOpenDrawer
     *
     * @return is open drawer
     */
    public boolean isOpenDrawer() {
        return getDrawerLayout().isDrawerOpen(GravityCompat.START);
    }

    /**
     * openDrawer
     *
     * @param gravity gravity
     */
    public void openDrawer(int gravity) {
        mDrawerLayout.openDrawer(gravity);
    }

    /**
     * getDrawerHeadLayoutId
     *
     * @return id
     */
    protected abstract int getDrawerHeadLayoutId();

    /**
     * getDrawerMenuId
     *
     * @return id
     */
    protected abstract int getDrawerMenuId();

    /**
     * getContentFragment
     *
     * @return fragment
     */
    protected abstract BaseSubFragment getContentFragment();

    /**
     * getDrawerLayout
     *
     * @return mDrawerLayout
     */
    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    /**
     * getNavigationView
     *
     * @return mNavigationView
     */
    public NavigationView getNavigationView() {
        return mNavigationView;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getVisibleFragment() != null) {
            boolean flag = getVisibleFragment().onTouchEvent(event);
            if (flag) {
                return true;
            } else {
                return super.onTouchEvent(event);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (getVisibleFragment() != null) {
            return getVisibleFragment().onKeyUp(keyCode, event);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isOpenDrawer()) {
            closeDrawer();
            return true;
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (getVisibleFragment() != null) {
                    boolean flag = getVisibleFragment().onKeyDown(keyCode, event);
                    if (!flag) {
                        if (mSubManager != null) {
                            mSubManager.onBackPressed();
                        } else {
                            return super.onKeyDown(keyCode, event);
                        }
                    }
                } else {
                    if (mSubManager != null) {
                        mSubManager.onBackPressed();
                    } else {
                        return super.onKeyDown(keyCode, event);
                    }
                }
                return true;
            default:
                if (getVisibleFragment() != null) {
                    return getVisibleFragment().onKeyDown(keyCode, event);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * doGet visible fragment
     *
     * @return visible fragment
     */
    public final BaseSubFragment getVisibleFragment() {
        if (mSubManager != null) {
            return mSubManager.getVisibleFragment();
        }
        return null;
    }
}
