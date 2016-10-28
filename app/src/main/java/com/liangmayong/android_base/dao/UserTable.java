package com.liangmayong.android_base.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.liangmayong.base.database.DataTable;
import com.liangmayong.base.database.DataType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/10/28.
 */

public class UserTable extends DataTable {
    /**
     * @param context context
     */
    public UserTable(Context context) {
        super(context, "User", "DB.db", 1);
    }

    @Override
    public Map<String, DataType> generateFields() {
        Map<String, DataType> map = new HashMap<String, DataType>();
        map.put("user", DataType.TEXT().notNull());
        map.put("age", DataType.TEXT());
        return map;
    }

    @Override
    protected void generateOnUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
