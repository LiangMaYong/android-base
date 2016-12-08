package com.liangmayong.base.support.database;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangMaYong on 2016/10/28.
 */
public class DataBatchInser {

    //mDataModel
    private List<ContentValues> contentValues = new ArrayList<ContentValues>();
    //mDataTable
    private DataTable mDataTable;

    public DataBatchInser(DataTable table) {
        this.mDataTable = table;
    }

    /**
     * put
     *
     * @param dataModel dataModel
     * @return inser
     */
    public DataBatchInser put(DataModel dataModel) {
        if (dataModel == null) {
            return this;
        }
        this.contentValues.add(dataModel.getValues());
        return this;
    }

    /**
     * put
     *
     * @param contentValues contentValues
     * @return inser
     */
    public DataBatchInser put(ContentValues contentValues) {
        if (contentValues == null) {
            return this;
        }
        this.contentValues.add(contentValues);
        return this;
    }

    /**
     * putAll
     *
     * @param contentValues contentValues
     * @return inser
     */
    public DataBatchInser putAll(List<ContentValues> contentValues) {
        if (contentValues == null) {
            return this;
        }
        this.contentValues.addAll(contentValues);
        return this;
    }

    /**
     * commit
     *
     * @return count
     */
    public long commit() {
        long count = mDataTable.getDataHandler().insertListByContentValues(contentValues);
        this.contentValues.removeAll(contentValues);
        return count;
    }

}
