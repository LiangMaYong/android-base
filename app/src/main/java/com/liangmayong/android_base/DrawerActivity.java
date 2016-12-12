package com.liangmayong.android_base;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.liangmayong.android_base.demo.DemoContentFragment;
import com.liangmayong.base.BaseDrawerActivity;
import com.liangmayong.base.sub.BaseSubFragment;
import com.liangmayong.base.utils.PermissionUtils;
import com.liangmayong.base.utils.PhotoUtils;

import java.util.List;

/**
 * Created by LiangMaYong on 2016/11/10.
 */
public class DrawerActivity extends BaseDrawerActivity {

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
        PermissionUtils.filePermissions(this, 1002, new PermissionUtils.OnPermissionListener() {

            @Override
            public boolean showDialog(Activity activity, PermissionUtils.Request request) {
                return false;
            }

            @Override
            public void gotPermissions() {
                PhotoUtils.getInstance().startSelect(DrawerActivity.this, 1001, true);
            }

            @Override
            public void rejectPermissions(List<String> rejects) {
            }
        });
        return false;
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        showToast("onActivityResult");
        PhotoUtils.getInstance().handleResult(1001, 0, 0, this, requestCode, resultCode, data, new PhotoUtils.OnPhotoResultListener() {
            @Override
            public void onResult(PhotoUtils.Result result) {
                showToast(result.getPath());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.handleResult(requestCode, permissions, grantResults);
    }

}
