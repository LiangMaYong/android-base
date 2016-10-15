package com.liangmayong.base;

import android.app.Application;

import com.liangmayong.base.utils.FrescoUtils;
import com.liangmayong.skin.OnSkinRefreshListener;
import com.liangmayong.skin.Skin;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
public class BaseApplication extends Application implements OnSkinRefreshListener {

    @Override
    public void onCreate() {
        super.onCreate();
        FrescoUtils.initialize(this);
        Skin.registerSkinRefresh(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Skin.unregisterSkinRefresh(this);
    }

    @Override
    public void onRefreshSkin(Skin skin) {
    }
}
