package com.liangmayong.base;

import android.app.Application;

import com.liangmayong.base.widget.themeskin.OnSkinRefreshListener;
import com.liangmayong.base.widget.themeskin.Skin;
import com.liangmayong.loading.Loading;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
public class BaseApplication extends Application implements OnSkinRefreshListener {

    @Override
    public void onCreate() {
        super.onCreate();
        Skin.registerSkinRefresh(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Skin.unregisterSkinRefresh(this);
    }

    @Override
    public void onRefreshSkin(Skin skin) {
        Loading.setBackgroundColor(skin.getThemeColor() - 0x55111111);
    }
}
