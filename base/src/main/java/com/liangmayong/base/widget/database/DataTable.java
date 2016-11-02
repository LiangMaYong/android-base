package com.liangmayong.base.widget.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * DataTable
 *
 * @author LiangMaYong
 * @version 1.0
 */
public abstract class DataTable {
    //mTableName
    private String mTableName;
    //mDatabaseName
    private String mDatabaseName;
    //mSQLiteHelper
    private SQLiteHelper mSQLiteHelper;
    //mDataHandler
    private DataHandler mDataHandler;
    //mDataHandler
    private DataBatchInser mDataBatchInser = null;
    //mDataInser
    private DataInser mDataInser = null;

    public DataTable(final Context context, String tableName, String databaseName, int version) {
        this.mSQLiteHelper = new SQLiteHelper(context, databaseName, version) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String sql = generateCreateSQL();
                if (sql != null && !"".equals(sql)) {
                    db.execSQL(sql);
                }
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                DataTable.this.generateOnUpgrade(db, oldVersion, newVersion);
            }
        };
        this.mTableName = tableName;
        this.mDatabaseName = databaseName;
        this.mDataHandler = new DataHandler(this);
        this.mSQLiteHelper.creatTable();
    }


    /**
     * getReadableDatabase
     *
     * @return data readable
     */
    public SQLiteDatabase getReadableDatabase() {
        return mSQLiteHelper.getReadableDatabase();
    }

    /**
     * getWritableDatabase
     *
     * @return data writable
     */
    public SQLiteDatabase getWritableDatabase() {
        return mSQLiteHelper.getWritableDatabase();
    }

    /**
     * get DatabaseName
     *
     * @return mDatabaseName
     */
    public String getDatabaseName() {
        return mDatabaseName;
    }

    /**
     * get TableName
     *
     * @return mTableName
     */
    public String getTableName() {
        return mTableName;
    }

    /**
     * getDataHandler
     *
     * @return DataHandler
     */
    public DataHandler getDataHandler() {
        return mDataHandler;
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
    public DataModel getModel(long id) {
        return getDataHandler().getModel(id);
    }

    /**
     * getModel
     *
     * @param where where
     * @return model
     */
    public DataModel getModel(String where) {
        return getDataHandler().getModel(where);
    }

    /**
     * getModel
     *
     * @param where   where
     * @param orderBy orderBy
     * @return DataModel
     */
    public DataModel getModel(String where, String orderBy) {
        return getDataHandler().getModel(where, orderBy);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////    LIST   /////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * getList
     *
     * @return list
     */
    public List<DataModel> getList() {
        return getDataHandler().getList(null, null, null);
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
        return getDataHandler().getList(where, orderBy, limit);
    }

    /**
     * getPager
     *
     * @return DataPager
     */
    public DataPager getPager(int limit_start, int page_index, int page_size) {
        return new DataPager(this, null, null, limit_start, page_index, page_size);
    }

    /**
     * getPager
     *
     * @return DataPager
     */
    public DataPager getPager(String where, String orderBy, int limit_start, int page_index, int page_size) {
        return new DataPager(this, where, orderBy, limit_start, page_index, page_size);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////    INSER   ////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * batchInser
     *
     * @return id
     */
    public DataBatchInser batchInser() {
        if (mDataBatchInser == null) {
            mDataBatchInser = new DataBatchInser(this);
        }
        return mDataBatchInser;
    }

    /**
     * inser
     *
     * @return id
     */
    public DataInser inser() {
        if (mDataInser == null) {
            mDataInser = new DataInser(this);
        }
        return mDataInser;
    }

    /**
     * insert
     *
     * @param dataModel dataModel
     * @return id
     */
    public long insert(DataModel dataModel) {
        return mDataHandler.insert(dataModel);
    }

    /**
     * insert
     *
     * @param contentValues contentValues
     * @return id
     */
    public long insert(ContentValues contentValues) {
        return mDataHandler.insert(contentValues);
    }

    /**
     * insertList
     *
     * @param models models
     * @return count
     */
    public long insertList(List<DataModel> models) {
        return mDataHandler.insertList(models);
    }

    /**
     * insertListByContentValues
     *
     * @param contentValues contentValues
     * @return count
     */
    public long insertListByContentValues(List<ContentValues> contentValues) {
        return mDataHandler.insertListByContentValues(contentValues);
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
    public Cursor query(boolean distinct, String[] columns, String whereString, String[] selectionArgs, String groupBy,
                        String having, String orderBy, String limit) {
        return mDataHandler.query(distinct, columns, whereString, selectionArgs, groupBy, having, orderBy, limit);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////    DELETE   //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * delete
     *
     * @param where where
     * @return lines
     */
    public int delete(String where) {
        return mDataHandler.delete(where);
    }

    /**
     * delete all
     *
     * @return lines
     */
    public int deleteAll() {
        return mDataHandler.deleteAll();
    }

    /**
     * delete
     *
     * @param id id
     * @return lines
     */
    public int delete(long id) {
        return mDataHandler.delete(id);
    }

    /**
     * delete
     *
     * @param whereClause whereClause
     * @param whereArgs   whereArgs
     * @return lines
     */
    public int delete(String whereClause, String[] whereArgs) {
        return mDataHandler.delete(whereClause, whereArgs);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////    UPDATE   //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * update
     *
     * @param contentValues contentValues
     * @param where         where
     * @param whereArgs     whereArgs
     * @return lines
     */
    public int updata(ContentValues contentValues, String where, String[] whereArgs) {
        return mDataHandler.update(contentValues, where, whereArgs);
    }

    /**
     * update
     *
     * @param contentValues contentValues
     * @param where         where
     * @return lines
     */
    public int updata(ContentValues contentValues, String where) {
        return mDataHandler.update(contentValues, where);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////    COUNT   /////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * get count
     *
     * @return count
     */
    public int getCount() {
        return mDataHandler.getCount();
    }

    /**
     * get count
     *
     * @param where where
     * @return count
     */
    public int getCount(String where) {
        return mDataHandler.getCount(where);
    }

    /**
     * get count
     *
     * @param where    where
     * @param distinct distinct
     * @return count
     */
    public int getCount(String where, boolean distinct) {
        return mDataHandler.getCount(where, distinct);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////    GENERAEN   /////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * get createSQL
     *
     * @return createSQL
     */
    private final String generateCreateSQL() {
        if (generateFields() == null || generateFields().size() <= 0) {
            return "";
        }
        String start = "create table IF NOT EXISTS " + mTableName + " (_id" + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        String end = ");";
        String content = "";
        for (Entry<String, DataType> entry : generateFields().entrySet()) {
            content += entry.getKey();
            content += " " + entry.getValue().name() + " ";
            if (entry.getValue().isNotNull()) {
                content += " NOT NULL";
            }
            content += ",";
        }
        content = content.substring(0, content.length() - 1);
        return start + content + end;
    }

    /**
     * generateFields
     *
     * @return
     */
    public abstract Map<String, DataType> generateFields();

    /**
     * generateOnUpgrade
     *
     * @param db         db
     * @param oldVersion oldVersion
     * @param newVersion newVersion
     */
    protected abstract void generateOnUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);


    /**
     * SQLiteHelper
     */
    private abstract class SQLiteHelper extends SQLiteOpenHelper {

        public SQLiteHelper(Context context, String name, int version) {
            super(context, name, null, version);
        }

        @Override
        public abstract void onCreate(SQLiteDatabase db);

        @Override
        public abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

        public void creatTable() {
            SQLiteDatabase db = getWritableDatabase();
            if (db != null) {
                onCreate(db);
            }
        }
    }


    @Override
    public String toString() {
        return "DataTable[tableName=" + mTableName + ", databaseName=" + mDatabaseName + "]";
    }
}
