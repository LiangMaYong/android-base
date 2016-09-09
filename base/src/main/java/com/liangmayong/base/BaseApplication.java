package com.liangmayong.base;

import android.app.Application;

import com.liangmayong.takephoto.TakePhoto;


/**
 * Created by LiangMaYong on 2016/8/22.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TakePhoto.init(getTakePhotoPath());
    }

    public String getTakePhotoPath() {
        return "/android/takephoto/";
    }

}
