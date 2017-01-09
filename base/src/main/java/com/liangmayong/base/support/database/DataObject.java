package com.liangmayong.base.support.database;

import android.content.ContentValues;

/**
 * Created by LiangMaYong on 2016/11/30.
 */
public interface DataObject {

    void writeObject(ContentValues values);

    ContentValues writeValues();

}
