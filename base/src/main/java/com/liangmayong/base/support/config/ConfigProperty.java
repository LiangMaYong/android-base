package com.liangmayong.base.support.config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangMaYong on 2017/3/13.
 */
public class ConfigProperty {

    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "property";
    private static final String F_KEY = "key";
    private static final String F_PROPERTY = "property";
    private static final String F_TIMESTAMP = "timestamp";

    public static ConfigProperty getInstance(String name) {
        return new ConfigProperty(name);
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////// Private
    ///////////////////////////////////////////////////////////////////////////

    private String name = "";

    public ConfigProperty(String name) {
        this.name = name;
    }

    /**
     * DatabaseHelper
     */
    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String dbname) {
            super(context, dbname + "_property.db", null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + F_KEY + " TEXT NOT NULL," + F_PROPERTY + " BLOD, " + F_TIMESTAMP + " INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

    }

    ///////////////////////////////////////////////////////////////////////////
    ///////// Public
    ///////////////////////////////////////////////////////////////////////////


    public String getName() {
        return name;
    }

    /**
     * setProperty
     *
     * @param context  context
     * @param key      key
     * @param property property
     */
    public <T> void setProperty(Context context, String key, T property) {
        if (hasProperty(context, key)) {
            DatabaseHelper databaseHelper = new DatabaseHelper(context, getName());
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(F_PROPERTY, toByteArray(property));
                contentValues.put(F_TIMESTAMP, System.currentTimeMillis());
                db.update(TABLE_NAME, contentValues, F_KEY + " = '" + key + "'", null);
            } finally {
                db.close();
                db = null;
            }
            return;
        }
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context, getName());
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            try {
                ContentValues values = new ContentValues();
                values.put(F_KEY, key);
                values.put(F_PROPERTY, toByteArray(property));
                values.put(F_TIMESTAMP, System.currentTimeMillis());
                db.insert(TABLE_NAME, null, values);
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
        }
    }

    public <T extends Serializable> T getProperty(Context context, String key) {
        return getProperty(context, key, null);
    }

    /**
     * getProperty
     *
     * @param context     context
     * @param key         key
     * @param defProperty defProperty
     * @return property
     */
    public <T extends Serializable> T getProperty(Context context, String key, T defProperty) {
        T property = defProperty;
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context, getName());
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            Cursor cursor = db.query(false, TABLE_NAME, null, F_KEY + " = '" + key + "'", null, null, null,
                    null, null);
            try {
                if (cursor.moveToNext()) {
                    try {
                        int columnIndex = cursor.getColumnIndex(F_PROPERTY);
                        property = toObject(cursor.getBlob(columnIndex));
                    } catch (Exception e) {
                    }
                }
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                db = null;
            }
        } catch (Exception e) {
        }
        return property;
    }

    /**
     * deleteProperty
     *
     * @param context context
     * @param key     key
     */
    public void deleteProperty(Context context, String key) {
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context, getName());
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            try {
                db.delete(TABLE_NAME, F_KEY + " = '" + key + "'", null);
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
        }
    }

    /**
     * deleteAll
     *
     * @param context context
     */
    public void deleteAll(Context context) {
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context, getName());
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            try {
                db.delete(TABLE_NAME, null, null);
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
        }
    }

    /**
     * hasProperty
     *
     * @param context context
     * @param key     key
     * @return
     */
    public boolean hasProperty(Context context, String key) {
        int count = 0;
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context, getName());
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            Cursor cursor = db.query(false, TABLE_NAME, null, F_KEY + " = '" + key + "'", null, null, null, null, null);
            try {
                count = cursor.getCount();
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                db = null;
            }
        } catch (Exception e) {
        }
        return count > 0;
    }

    /**
     * getPropertys
     *
     * @param context context
     * @return caches
     */
    public List<String> getPropertys(Context context) {
        List<String> list = new ArrayList<>();
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context, getName());
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query(false, TABLE_NAME, null, null, null, null, null, null, null);
            try {
                while (cursor.moveToNext()) {
                    String key = "";
                    try {
                        int columnIndex = cursor.getColumnIndex(F_KEY);
                        key = cursor.getString(columnIndex);
                    } catch (Exception e) {
                    }
                    list.add(key);
                }
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                db = null;
            }
        } catch (Exception e) {
        }
        return list;
    }

    /**
     * getCount
     *
     * @param context context
     * @return count
     */
    public int getCount(Context context) {
        int count = 0;
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context, getName());
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query(false, TABLE_NAME, null, null, null, null, null, null, null);
            try {
                count = cursor.getCount();
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                db = null;
            }
        } catch (Exception e) {
        }
        return count;
    }


    /**
     * toByteArray
     *
     * @param obj obj
     * @return bytes
     */
    private byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (Exception ex) {
        }
        return bytes;
    }

    /**
     * toObject
     *
     * @param bytes bytes
     * @param <O>   obj
     * @return obj
     */
    private <O> O toObject(byte[] bytes) {
        O data = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            data = (O) ois.readObject();
            ois.close();
            bis.close();
        } catch (Exception ex) {
        }
        return data;
    }
}
