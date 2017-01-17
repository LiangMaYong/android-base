package com.liangmayong.base.basic.expands.drawer;

import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.liangmayong.base.R;
import com.liangmayong.base.basic.flow.FlowBaseActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;

/**
 * Created by LiangMaYong on 2016/12/29.
 */

public abstract class DrawerBaseActivity extends FlowBaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView base_drawer_navigation_view;
    private DrawerLayout base_drawer_layout;

    /**
     * _initView
     */
    private void _initView() {
        base_drawer_layout = (DrawerLayout) findViewById(R.id.base_drawer_layout);
        base_drawer_layout.setScrimColor(0x20333333);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            base_drawer_layout.setFitsSystemWindows(false);
        }
        base_drawer_navigation_view = (NavigationView) findViewById(R.id.base_drawer_navigation_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            base_drawer_navigation_view.setFitsSystemWindows(false);
        }
        base_drawer_navigation_view.inflateHeaderView(getDrawerHeaderLayoutId());
        base_drawer_navigation_view.inflateMenu(getDrawerMenuId());
        base_drawer_navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                DrawerBaseActivity.this.onNavigationItemSelected(item);
                base_drawer_layout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    protected void onConfigFlowFragmentAnims() {
    }

    @Override
    protected void generateContainerView() {
        setContentView(R.layout.base_default_activity_drawer);
        _initView();
    }

    @Override
    protected int generateContainerFragmentId() {
        return R.id.base_drawer_fragment;
    }

    /**
     * getDrawerHeaderLayoutId
     *
     * @return id
     */
    protected abstract int getDrawerHeaderLayoutId();

    /**
     * getDrawerMenuId
     *
     * @return id
     */
    protected abstract int getDrawerMenuId();

    /**
     * getFristFragment
     *
     * @return frist fragment
     */
    @Override
    protected abstract FlowBaseFragment getFristFragment();

    /**
     * getDrawerLayout
     *
     * @return mDrawerLayout
     */
    public DrawerLayout getDrawerLayout() {
        return base_drawer_layout;
    }

    /**
     * getNavigationView
     *
     * @return mNavigationView
     */
    public NavigationView getNavigationView() {
        return base_drawer_navigation_view;
    }

    /**
     * openDrawer
     */
    public void openDrawer() {
        openDrawer(GravityCompat.START);
    }

    /**
     * closeDrawer
     */
    public void closeDrawer() {
        closeDrawer(GravityCompat.START);
    }

    /**
     * isOpenDrawer
     *
     * @return is open drawer
     */
    public boolean isOpenDrawer() {
        return isOpenDrawer(GravityCompat.START);
    }

    /**
     * openDrawer
     *
     * @param gravity gravity
     */
    public void openDrawer(int gravity) {
        getDrawerLayout().openDrawer(gravity);
    }

    /**
     * closeDrawer
     *
     * @param gravity gravity
     */
    public void closeDrawer(int gravity) {
        getDrawerLayout().closeDrawer(gravity);
    }

    /**
     * isOpenDrawer
     *
     * @param gravity gravity
     * @return is open drawer
     */
    public boolean isOpenDrawer(int gravity) {
        return getDrawerLayout().isDrawerOpen(gravity);
    }
}
