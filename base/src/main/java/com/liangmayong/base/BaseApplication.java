package com.liangmayong.base;

import android.app.Application;

import com.liangmayong.base.widget.themeskin.OnSkinRefreshListener;
import com.liangmayong.base.widget.themeskin.Skin;
import com.liangmayong.loading.Loading;
import com.liangmayong.takephoto.TakePhoto;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
public class BaseApplication extends Application implements OnSkinRefreshListener {

    @Override
    public void onCreate() {
        super.onCreate();
        onRefreshSkin(Skin.get());
        Skin.registerSkinRefresh(this);
        TakePhoto.init(getTakePhotoPath());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public String getTakePhotoPath() {
        return "/android/takephoto/";
    }

    @Override
    public void onRefreshSkin(Skin skin) {
        Loading.setBackgroundColor(skin.getThemeColor() - 0x55111111);
    }
}
