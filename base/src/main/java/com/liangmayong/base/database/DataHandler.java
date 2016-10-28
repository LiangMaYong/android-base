package com.liangmayong.base.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangMaYong on 2016/10/28.
 */
public class DataHandler {

    // _tableName
    private String _tableName;
    // _databaseName
    private String _databaseName;
    // _table
    private DataTable _table;

    /**
     * @param table table
     */
    public DataHandler(final DataTable table) {
        this._table = table;
        this._tableName = table.getTableName();
        this._databaseName = table.getDatabaseName();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////    COUNT   /////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * getCount
     *
     * @return count
     */
    public final int getCount() {
        int count = 0;
        SQLiteDatabase db = _table.getReadableDatabase();
        String sql = "select count(*) from " + _tableName + ";";
        Cursor cursor = db.rawQuery(sql, null);
        try {
            if (cursor.moveToNext()) {
                count = cursor.getInt(0);
            }
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            db.close();
            db = null;
        }
        return count;
    }

    /**
     * getCount
     *
     * @param where    where
     * @param distinct distinct
     * @return count
     */
    public final int getCount(String where, boolean distinct) {
        return getCount(distinct, null, where, null, null, null, null, null);
    }

    /**
     * getCount
     *
     * @param where where
     * @return count
     */
    public final int getCount(String where) {
        return getCount(false, null, where, null, null, null, null, null);
    }

    /**
     * getCount
     *
     * @param distinct      distinct
     * @param columns       columns
     * @param whereString   whereString
     * @param selectionArgs selectionArgs
     * @param groupBy       groupBy
     * @param having        having
     * @param orderBy       orderBy
     * @param limit         limit
     * @return count
     */
    public final int getCount(boolean distinct, String[] columns, String whereString, String[] selectionArgs,
                              String groupBy, String having, String orderBy, String limit) {
        int count = 0;
        SQLiteDatabase db = _table.getReadableDatabase();
        Cursor cursor = db.query(distinct, _tableName, columns, whereString, selectionArgs, groupBy, having,
                orderBy, limit);
        try {
            count = cursor.getCount();
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            db.close();
            db = null;
        }
        return count;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////    LIST   /////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * getList
     *
     * @param distinct      distinct
     * @param columns       columns
     * @param whereString   whereString
     * @param selectionArgs selectionArgs
     * @param groupBy       groupBy
     * @param having        having
     * @param orderBy       orderBy
     * @param limit         limit
     * @return list
     */
    public final List<DataModel> getList(boolean distinct, String[] columns, String whereString,
                                         String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        List<DataModel> models = new ArrayList<DataModel>();
        SQLiteDatabase db = _table.getReadableDatabase();
        Cursor cursor = db.query(distinct, _tableName, columns, whereString, selectionArgs, groupBy, having,
                orderBy, limit);
        try {
            while (cursor.moveToNext()) {
                models.add(toModel(cursor));
            }
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            db.close();
            db = null;
        }
        return models;
    }

    /**
     * getList
     *
     * @param where   where
     * @param orderBy orderBy
     * @param limit   limit
     * @return list
     */
    public List<DataModel> getList(String where, String orderBy, String limit) {
        return getList(false, null, where, null, null, null, orderBy, limit);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////    QUERY   ////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * query
     *
     * @param distinct      distinct
     * @param columns       columns
     * @param whereString   whereString
     * @param selectionArgs selectionArgs
     * @param groupBy       groupBy
     * @param having        having
     * @param orderBy       orderBy
     * @param limit         limit
     * @return cursor
     */
    public final Cursor query(boolean distinct, String[] columns, String whereString, String[] selectionArgs,
                              String groupBy, String having, String orderBy, String limit) {
        SQLiteDatabase db = _table.getReadableDatabase();
        Cursor cursor = db.query(distinct, _tableName, columns, whereString, selectionArgs, groupBy, having,
                orderBy, limit);
        db.close();
        return cursor;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////    GET   //////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * getModel
     *
     * @param id id
     * @return model
     */
    public final DataModel getModel(long id) {
        return getModel("_id = '" + id + "'");
    }

    /**
     * getModel
     *
     * @param where where
     * @return model
     */
    public final DataModel getModel(String where) {
        return getModel(false, null, where, null, null, null, null, null);
    }

    /**
     * getModel
     *
     * @param where   where
     * @param orderBy orderBy
     * @return model
     */
    public final DataModel getModel(String where, String orderBy) {
        return getModel(false, null, where, null, null, null, orderBy, null);
    }

    /**
     * getModel
     *
     * @param distinct      distinct
     * @param columns       columns
     * @param whereString   whereString
     * @param selectionArgs selectionArgs
     * @param groupBy       groupBy
     * @param having        having
     * @param orderBy       orderBy
     * @param limit         limit
     * @return model
     */
    public final DataModel getModel(boolean distinct, String[] columns, String whereString,
                                    String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        SQLiteDatabase db = _table.getReadableDatabase();
        Cursor cursor = db.query(distinct, _tableName, columns, whereString, selectionArgs, groupBy, having,
                orderBy, limit);
        DataModel model = null;
        try {
            if (cursor.moveToNext()) {
                model = toModel(cursor);
            }
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            db.close();
            db = null;
        }
        return model;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////    ADD   //////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * insert
     *
     * @param contentValues contentValues
     * @return id
     */
    public final long insert(ContentValues contentValues) {
        long rowid = 0;
        if (contentValues != null) {
            contentValues.remove("_id");
            SQLiteDatabase db = _table.getWritableDatabase();
            try {
                rowid = db.insert(_tableName, null, contentValues);
            } finally {
                db.close();
                db = null;
            }
        }
        return rowid;
    }

    /**
     * insertListByContentValues
     *
     * @param contentValues contentValues
     * @return count
     */
    public final long insertListByContentValues(List<ContentValues> contentValues) {
        long count = 0;
        if (contentValues != null) {
            SQLiteDatabase db = _table.getWritableDatabase();
            try {
                db.beginTransaction();
                for (int i = 0; i < contentValues.size(); i++) {
                    contentValues.get(i).remove("_id");
                    long insert = db.insert(_tableName, null, contentValues.get(i));
                    if (insert > 0) {
                        count++;
                    }
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
                db.close();
                db = null;
            }
        }
        return count;
    }

    /**
     * insert
     *
     * @param item item
     * @return id
     */
    public final long insert(DataModel item) {
        return insert(item.getValues());
    }

    /**
     * insertList
     *
     * @param items items
     * @return count
     */
    public final long insertList(List<DataModel> items) {
        long count = 0;
        if (items != null) {
            SQLiteDatabase db = _table.getWritableDatabase();
            try {
                db.beginTransaction();
                for (int i = 0; i < items.size(); i++) {
                    long insert = db.insert(_tableName, null, items.get(i).getValues());
                    if (insert > 0) {
                        count++;
                    }
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
                db.close();
                db = null;
            }
        }
        return count;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////    UPDATE   //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * update
     *
     * @param contentValues contentValues
     * @param whereClause   whereClause
     * @param whereArgs     whereArgs
     * @return count
     */
    public final int update(ContentValues contentValues, String whereClause, String[] whereArgs) {
        int count = 0;
        SQLiteDatabase db = _table.getWritableDatabase();
        try {
            count = db.update(_tableName, contentValues, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            db = null;
        }
        return count;
    }

    /**
     * update
     *
     * @param contentValues contentValues
     * @param where         where
     * @return count
     */
    public final int update(ContentValues contentValues, String where) {
        return update(contentValues, where, null);
    }

    /**
     * update
     *
     * @param item item
     * @return count
     */
    public final int update(DataModel item) {
        return update(item.getValues(), "_id = '" + item.getId() + "'");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////    DELETE   //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * delete
     *
     * @param where where
     * @return count
     */
    public final int delete(String where) {
        int count = 0;
        SQLiteDatabase db = _table.getWritableDatabase();
        try {
            count = db.delete(_tableName, where, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            db = null;
        }
        return count;
    }

    /**
     * delete
     *
     * @param whereClause whereClause
     * @param whereArgs   whereArgs
     * @return count
     */
    public final int delete(String whereClause, String[] whereArgs) {
        int count = 0;
        SQLiteDatabase db = _table.getWritableDatabase();
        try {
            count = db.delete(_tableName, whereClause, whereArgs);
        } finally {
            db.close();
            db = null;
        }
        return count;
    }

    /**
     * deleteAll
     *
     * @return count
     */
    public final int deleteAll() {
        int count = 0;
        try {
            count = delete("", new String[]{});
        } catch (Exception e) {
        }
        return count;
    }

    /**
     * delete
     *
     * @param id id
     * @return count
     */
    public final int delete(long id) {
        return delete("_id = '" + id + "'");
    }

    /**
     * delete
     *
     * @param item item
     * @return count
     */
    public final int delete(DataModel item) {
        int del = delete("_id = '" + item.getId() + "'");
        if (del > 0) {
            item.getValues().remove("_id");
        }
        return del;
    }


    /**
     * toModel
     *
     * @param cursor cursor
     * @return model
     */
    private DataModel toModel(Cursor cursor) {
        String[] name = cursor.getColumnNames();
        DataModel model = new DataModel(_table);
        for (int i = 0; i < name.length; i++) {
            int columnIndex = cursor.getColumnIndex(name[i]);
            try {
                int columnType = DataCompat.getType(cursor, columnIndex);
                if (columnType == Cursor.FIELD_TYPE_BLOB) {
                    model.put(name[i], cursor.getBlob(columnIndex));
                } else if (columnType == Cursor.FIELD_TYPE_FLOAT) {
                    model.put(name[i], cursor.getDouble(columnIndex));
                } else if (columnType == Cursor.FIELD_TYPE_INTEGER) {
                    model.put(name[i], cursor.getLong(columnIndex));
                } else if (columnType == Cursor.FIELD_TYPE_STRING) {
                    model.put(name[i], cursor.getString(columnIndex));
                } else if (columnType == Cursor.FIELD_TYPE_NULL) {
                }
            } catch (Exception e) {
            }
        }
        return model;
    }

    /**
     * generateFields
     *
     * @return fields
     */
    public List<String> getFields() {
        List<String> strings = new ArrayList<String>();
        SQLiteDatabase db = _table.getReadableDatabase();
        Cursor cursor = db.query(_tableName, null, null, null, null, null, null);
        try {
            String[] strs = cursor.getColumnNames();
            for (int i = 0; i < strs.length; i++) {
                strings.add(strs[i]);
            }
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            db.close();
            db = null;
        }
        return strings;
    }


    @Override
    public String toString() {
        return "DataHandler[tableName=" + _tableName + ",databaseName=" + _databaseName + "]";
    }
}
