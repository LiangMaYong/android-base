package com.liangmayong.base.compat.database;

import android.content.ContentValues;

/**
 * Created by LiangMaYong on 2016/11/30.
 */
public interface DataBean {

    void writeModel(ContentValues values);

    ContentValues writeValues();

}
