package com.liangmayong.android_base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.liangmayong.android_base.demo.DemoContentFragment;
import com.liangmayong.base.BaseDrawerActivity;
import com.liangmayong.base.sub.BaseSubFragment;
import com.liangmayong.base.support.skin.SkinManager;
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

    protected ViewHolder viewHolder;

    @Override
    protected int getDrawerMenuId() {
        return R.menu.menu_drawer;
    }

    @Override
    protected BaseSubFragment getContentFragment() {
        return new DemoContentFragment();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHolder = new ViewHolder(getNavigationView().getHeaderView(0));
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils.cameraPermissions(DrawerActivity.this, 1002, new PermissionUtils.OnPermissionListener() {

                    @Override
                    public boolean showDialog(Activity activity, PermissionUtils.Request request) {
                        return false;
                    }

                    @Override
                    public void gotPermissions() {
                        PhotoUtils.getInstance().startTake(DrawerActivity.this, 1001, true);
                    }

                    @Override
                    public void rejectPermissions(List<String> rejects) {
                    }
                });
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.menu_setting) {
//            SkinManager.switchTheme("night", NightDefault.class);
//        } else {
//            SkinManager.switchTheme("day", SkinDefault.class);
//        }
        return false;
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        PhotoUtils.getInstance().handleResult(1001, 160, 160, this, requestCode, resultCode, data, new PhotoUtils.OnPhotoResultListener() {
            @Override
            public void onResult(PhotoUtils.Result result) {
                if (viewHolder != null) {
                    viewHolder.img.setImageBitmap(result.getThumbnail());
                }
                Log.e("TAG", result.getPath());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.handleResult(requestCode, permissions, grantResults);
    }

    public class ViewHolder {
        public View rootView;
        public ImageView img;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.img = (ImageView) rootView.findViewById(R.id.img);
        }

    }
}
