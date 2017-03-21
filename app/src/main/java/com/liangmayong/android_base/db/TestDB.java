package com.liangmayong.android_base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.liangmayong.base.support.sqlite.SQLiteDAO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/3/21.
 */

public class TestDB extends SQLiteDAO {

    public TestDB(Context context) {
        super(context, "ibeam", "me.db", 1);
    }

    @Override
    protected void generateUpgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    protected Map<String, DBType> generateFields() {
        Map<String, DBType> typeMap = new HashMap<>();
        typeMap.put("name", DB_TYPE_TEXT());
        typeMap.put("age", DB_TYPE_INTEGER());
        typeMap.put("content", DB_TYPE_TEXT().NOTNULL());
        return typeMap;
    }
}
