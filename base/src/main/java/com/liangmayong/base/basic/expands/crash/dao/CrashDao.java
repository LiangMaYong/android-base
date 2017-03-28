package com.liangmayong.base.basic.expands.crash.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liangmayong.base.basic.expands.crash.model.CrashModel;
import com.liangmayong.base.support.sqlite.SQLiteDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/3/28.
 */

public class CrashDao extends SQLiteDAO {

    public CrashDao(Context context) {
        super(context, "carsh_log", "android_base_log.db", 1);
    }

    @Override
    protected void generateUpgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    protected Map<String, DBType> generateFields() {
        Map<String, DBType> typeMap = new HashMap<>();
        typeMap.put("title", DB_TYPE_TEXT());
        typeMap.put("log", DB_TYPE_TEXT());
        typeMap.put("time", DB_TYPE_INTEGER());
        return typeMap;
    }

    private Converter<ContentValues, CrashModel> crashModelConverter = new Converter<ContentValues, CrashModel>() {
        @Override
        public ContentValues convert(CrashModel crashModel) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", crashModel.getTitle());
            contentValues.put("log", crashModel.getLog());
            contentValues.put("time", crashModel.getTime());
            return contentValues;
        }
    };

    private Converter<CrashModel, Cursor> cursorConverter = new Converter<CrashModel, Cursor>() {
        @Override
        public CrashModel convert(Cursor cursor) {
            CrashModel crashModel = new CrashModel();
            crashModel.setId(readLong("_id", cursor));
            crashModel.setTitle(readString("title", cursor));
            crashModel.setLog(readString("log", cursor));
            crashModel.setTime(readLong("time", cursor));
            return crashModel;
        }
    };

    public long addCrash(CrashModel crashModel) {
        return insertData(crashModel, crashModelConverter);
    }

    public int delCrash(CrashModel crashModel) {
        return deleteData("_id = " + crashModel.getId());
    }

    public int delAll() {
        return deleteData(null);
    }

    public List<CrashModel> getCrashList() {
        return getDataList(null, "time DESC", null, cursorConverter);
    }

}
