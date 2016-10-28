package com.liangmayong.base.database;

import android.content.ContentValues;

/**
 * Created by LiangMaYong on 2016/10/28.
 */
public class DataInser {
    //mDataModel
    private DataModel mDataModel;
    //mDataTable
    private DataTable mDataTable;

    public DataInser(DataTable table) {
        this.mDataTable = table;
        this.mDataModel = new DataModel(table);
    }

    /**
     * put
     *
     * @param key   key
     * @param value value
     * @return inser
     */
    public DataInser put(String key, Object value) {
        this.mDataModel.put(key, value);
        return this;
    }

    /**
     * putAll
     *
     * @param contentValues contentValues
     * @return inser
     */
    public DataInser putAll(ContentValues contentValues) {
        this.mDataModel.putAll(contentValues);
        return this;
    }

    /**
     * commit
     *
     * @return count
     */
    public long commit() {
        long count = mDataTable.getDataHandler().insert(mDataModel);
        this.mDataModel.removeAll();
        return count;
    }

}
