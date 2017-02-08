package com.liangmayong.android_base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.liangmayong.android_base.demo.StackF;
import com.liangmayong.base.basic.expands.drawer.DrawerBaseActivity;
import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.support.photo.Photo;
import com.liangmayong.base.support.photo.PhotoResult;
import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.support.utils.PermissionUtils;

import java.util.List;

/**
 * Created by LiangMaYong on 2016/11/10.
 */
public class DrawerActivity extends DrawerBaseActivity {


    @Override
    public void onFlowActivityCreate(Bundle savedInstanceState) {
        super.onFlowActivityCreate(savedInstanceState);
        SkinManager.editor().setThemeColor(0xFFDA4D3E, 0xFFFFFFFF).commit();
    }

    @Override
    protected int getDrawerHeaderLayoutId() {
        return R.layout.navi_head;
    }

    @Override
    protected FlowBaseFragment getFristFragment() {
        return new StackF();
    }

    protected ViewHolder viewHolder;

    @Override
    protected int getDrawerMenuId() {
        return R.menu.menu_drawer;
    }

    @Override
    protected boolean isStatusBarDark() {
        return false;
    }

    @Override
    protected void generateContainerView() {
        super.generateContainerView();
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
                        Photo.getInstance().startSelect(DrawerActivity.this, 1001, true);
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
        return false;
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        Photo.getInstance().handleResult(1001, 160, 160, this, requestCode, resultCode, data, new PhotoResult.Listener() {
            @Override
            public void onResult(PhotoResult result) {
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
