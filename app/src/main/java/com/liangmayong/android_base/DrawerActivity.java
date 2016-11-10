package com.liangmayong.android_base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.liangmayong.android_base.demo.DemoContentFragment;
import com.liangmayong.base.BaseDrawerActivity;
import com.liangmayong.base.sub.BaseSubFragment;

/**
 * Created by LiangMaYong on 2016/11/10.
 */
public class DrawerActivity extends BaseDrawerActivity {

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        getDefualtToolbar().
    }

    @Override
    protected int getDrawerHeadLayoutId() {
        return R.layout.navi_head;
    }

    @Override
    protected int getDrawerMenuId() {
        return R.menu.menu_drawer;
    }

    @Override
    protected BaseSubFragment getContentFragment() {
        return new DemoContentFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        showToast(item.getTitle() + "");
        return false;
    }
}
