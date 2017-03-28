package com.liangmayong.base.support.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/3/15.
 */
public abstract class SQLiteDAO {

    ////////////////////////////////////////////////////////////////////////////
    /////////// DB Converter
    ////////////////////////////////////////////////////////////////////////////

    public interface Converter<D, T> {
        D convert(T t);
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////// DB TABLE
    ////////////////////////////////////////////////////////////////////////////

    private final String tableName;
    private final Helper helper;

    public SQLiteDAO(Context context, String tableName, String dbName, int version) {
        this.tableName = tableName;
        this.helper = new Helper(context, dbName, null, version);
    }

    protected abstract void generateUpgradeTable(SQLiteDatabase db, int oldVersion, int newVersion);

    protected abstract Map<String, DBType> generateFields();

    /**
     * generateCreateTable
     *
     * @param db db
     */
    protected void generateCreateTable(SQLiteDatabase db) {
        db.execSQL(generateCreateTableSQL(tableName, generateFields()));
    }

    /**
     * generateCreateSQL
     *
     * @return createSQL
     */
    protected final String generateCreateTableSQL(String tableName, Map<String, DBType> fields) {
        if (fields == null || fields.size() <= 0) {
            return "";
        }
        String start = "create table IF NOT EXISTS " + tableName + " (_id" + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        String content = "";
        for (Map.Entry<String, DBType> entry : fields.entrySet()) {
            content += entry.getKey();
            content += " " + entry.getValue().name() + " ";
            if (entry.getValue().NOTNULL) {
                content += " NOT NULL";
            }
            content += ",";
        }
        content = content.substring(0, content.length() - 1);
        String end = ");";
        return start + content + end;
    }

    /**
     * handleException
     *
     * @param e e
     */
    protected void handleException(Exception e) {
        e.printStackTrace();
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////// DB Helper
    ////////////////////////////////////////////////////////////////////////////

    private class Helper extends SQLiteOpenHelper {

        public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            createTable();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            generateCreateTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            generateUpgradeTable(db, oldVersion, newVersion);
        }

        public void createTable() {
            try {
                SQLiteDatabase db = getWritableDatabase();
                try {
                    onCreate(db);
                } finally {
                    db.close();
                    db = null;
                }
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////// DB TYPE
    ////////////////////////////////////////////////////////////////////////////

    protected static final DBType DB_TYPE_TEXT() {
        return new DBType(DBType.Type.TEXT);
    }

    protected static final DBType DB_TYPE_INTEGER() {
        return new DBType(DBType.Type.INTEGER);
    }

    protected static final DBType DB_TYPE_FLOAT() {
        return new DBType(DBType.Type.FLOAT);
    }

    protected static final DBType DB_TYPE_BLOB() {
        return new DBType(DBType.Type.BLOB);
    }

    protected static class DBType {

        // type
        private final Type type;
        // NOTNULL
        private boolean NOTNULL = false;

        private DBType(Type type) {
            this.type = type;
        }

        /**
         * NOTNULL
         *
         * @return type
         */
        public DBType NOTNULL() {
            this.NOTNULL = true;
            return this;
        }

        public String name() {
            return type.name();
        }

        @Override
        public String toString() {
            return name();
        }

        private enum Type {
            INTEGER, TEXT, FLOAT, BLOB;
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    /////////// ORM
    ////////////////////////////////////////////////////////////////////////////


    /**
     * getDataList
     *
     * @param where     where
     * @param orderBy   orderBy
     * @param limit     limit
     * @param converter converter
     * @param <D>       dtype
     * @return list d
     */
    public <D> List<D> getDataList(String where, String orderBy, String limit, Converter<D, Cursor> converter) {
        List<D> list = new ArrayList<D>();
        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query(false, tableName, null, where, null, null, null, orderBy, limit);
            try {
                while (cursor.moveToNext()) {
                    D data = converter.convert(cursor);
                    if (data != null) {
                        list.add(data);
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
            handleException(e);
        }
        return list;
    }

    /**
     * getData
     *
     * @param where     where
     * @param orderBy   orderBy
     * @param converter converter
     * @param <D>       dtype
     * @return d
     */
    public <D> D getData(String where, String orderBy, Converter<D, Cursor> converter) {
        D data = null;
        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query(false, tableName, null, where, null, null, null, orderBy, null);
            try {
                if (cursor.moveToNext()) {
                    data = converter.convert(cursor);
                }
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return data;
    }

    /**
     * updateData
     *
     * @param d         d
     * @param converter converter
     * @param where     where
     * @param <D>       dtype
     * @return update count
     */
    public <D> int updateData(D d, Converter<ContentValues, D> converter, String where) {
        int update = 0;
        try {
            SQLiteDatabase db = helper.getWritableDatabase();
            try {
                update = db.update(tableName, converter.convert(d), where, null);
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return update;
    }

    /**
     * updateData
     *
     * @param values values
     * @param where  where
     * @return update count
     */
    public int updateData(ContentValues values, String where) {
        int update = 0;
        try {
            SQLiteDatabase db = helper.getWritableDatabase();
            try {
                update = db.update(tableName, values, where, null);
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return update;
    }

    /**
     * insertData
     *
     * @param d         d
     * @param converter converter
     * @param <D>       dtype
     * @return id
     */
    public <D> long insertData(D d, Converter<ContentValues, D> converter) {
        long id = 0;
        try {
            SQLiteDatabase db = helper.getWritableDatabase();
            try {
                id = db.insert(tableName, null, converter.convert(d));
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return id;
    }

    /**
     * insertData
     *
     * @param ds        ds
     * @param converter converter
     * @param <D>       type
     * @return insert count
     */
    public <D> int insertDataList(List<D> ds, Converter<ContentValues, D> converter) {
        int count = 0;
        try {
            SQLiteDatabase db = helper.getWritableDatabase();
            try {
                db.beginTransaction();
                for (int i = 0; i < ds.size(); i++) {
                    long id = db.insert(tableName, null, converter.convert(ds.get(i)));
                    if (id > 0) {
                        count++;
                    }
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return count;
    }

    /**
     * insertData
     *
     * @param values values
     * @return id
     */
    public long insertData(ContentValues values) {
        long id = 0;
        try {
            SQLiteDatabase db = helper.getWritableDatabase();
            try {
                id = db.insert(tableName, null, values);
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return id;
    }

    /**
     * insertData
     *
     * @param values values
     * @return id
     */
    public int insertDataList(List<ContentValues> values) {
        int count = 0;
        try {
            SQLiteDatabase db = helper.getWritableDatabase();
            try {
                db.beginTransaction();
                for (int i = 0; i < values.size(); i++) {
                    long id = db.insert(tableName, null, values.get(i));
                    if (id > 0) {
                        count++;
                    }
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return count;
    }

    /**
     * deleteData
     *
     * @param where where
     */
    public int deleteData(String where) {
        int delete = 0;
        try {
            SQLiteDatabase db = helper.getWritableDatabase();
            try {
                delete = db.delete(tableName, where, null);
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return delete;
    }

    /**
     * getCount
     *
     * @param where where
     * @return count
     */
    public int getCount(String where) {
        int count = 0;
        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query(false, tableName, null, where, null, null, null, null, null);
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
            handleException(e);
        }
        return count;
    }

    /**
     * hasData
     *
     * @param where where
     * @return bool
     */
    public boolean hasData(String where) {
        int count = 0;
        try {
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor cursor = db.query(false, tableName, null, where, null, null, null, null, null);
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
            handleException(e);
        }
        return count > 0;
    }

    /////////////////////////////////////////////////////////////////////////
    ///////// Read Cursor
    /////////////////////////////////////////////////////////////////////////

    protected String readString(String key, Cursor cursor) {
        String value = "";
        try {
            int columnIndex = cursor.getColumnIndex(key);
            value = cursor.getString(columnIndex);
        } catch (Exception e) {
        }
        return value;
    }

    protected int readInt(String key, Cursor cursor) {
        int value = 0;
        try {
            int columnIndex = cursor.getColumnIndex(key);
            value = cursor.getInt(columnIndex);
        } catch (Exception e) {
        }
        return value;
    }

    protected long readLong(String key, Cursor cursor) {
        long value = 0;
        try {
            int columnIndex = cursor.getColumnIndex(key);
            value = cursor.getLong(columnIndex);
        } catch (Exception e) {
        }
        return value;
    }

    protected byte[] readBytes(String key, Cursor cursor) {
        byte[] value = new byte[0];
        try {
            int columnIndex = cursor.getColumnIndex(key);
            value = cursor.getBlob(columnIndex);
        } catch (Exception e) {
        }
        return value;
    }

    protected double readDouble(String key, Cursor cursor) {
        double value = 0;
        try {
            int columnIndex = cursor.getColumnIndex(key);
            value = cursor.getDouble(columnIndex);
        } catch (Exception e) {
        }
        return value;
    }

    protected float readFloat(String key, Cursor cursor) {
        float value = 0;
        try {
            int columnIndex = cursor.getColumnIndex(key);
            value = cursor.getFloat(columnIndex);
        } catch (Exception e) {
        }
        return value;
    }

    protected short readShort(String key, Cursor cursor) {
        short value = 0;
        try {
            int columnIndex = cursor.getColumnIndex(key);
            value = cursor.getShort(columnIndex);
        } catch (Exception e) {
        }
        return value;
    }
}
