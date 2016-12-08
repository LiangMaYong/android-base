package com.liangmayong.base.compat.database;

import java.util.List;

/**
 * DataPager
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class DataPager {

    //table
    private DataTable table = null;
    //page_size
    private int page_size = 10;
    //page_index
    private int page_index = 0;
    //limit_start
    private int limit_start = 0;
    //total_count
    private int total_count = 0;
    //where
    private String where = null;
    //orderBy
    private String orderBy = null;

    /**
     * @param table       table
     * @param limit_start limit_start
     * @param page_index  page_index
     * @param page_size   page_size
     * @param orderBy     orderBy
     */
    public DataPager(DataTable table, String where, String orderBy, int limit_start, int page_index, int page_size) {
        this.table = table;
        this.limit_start = limit_start;
        this.page_index = page_index;
        this.page_size = page_size;
        this.orderBy = orderBy;
        this.where = where;
    }

    /**
     * get total_count pager
     *
     * @return total_count pager
     */
    public int getTotalPager() {
        return (total_count - limit_start) / page_size > 0 ? (total_count - limit_start) / page_size : 1;
    }

    /**
     * get total_count rows
     *
     * @return total_count rows
     */
    public int getTotalCount() {
        return total_count;
    }

    /**
     * get now pager
     *
     * @return now pager
     */
    public int getPageIndex() {
        return page_index;
    }

    /**
     * get sync rows
     *
     * @return sync rows
     */
    public int getPageSize() {
        return page_size;
    }

    /**
     * setTotal_count
     *
     * @param total_count total_count
     */
    public void setTotalCount(int total_count) {
        this.total_count = total_count;
    }

    /**
     * getLimit
     *
     * @return limit sql
     */
    public String getLimit() {
        if (page_index < 1) {
            page_index = 1;
        }
        return ((page_index - 1) * page_size + limit_start) + "," + (page_index * page_size + limit_start);
    }

    /**
     * next
     */
    public void next() {
        page_index++;
    }

    /**
     * previous
     */
    public void previous() {
        page_index--;
    }

    /**
     * frist
     */
    public void frist() {
        page_index = 0;
    }

    /**
     * load
     */
    public List<DataModel> load() {
        return table.getList(where, orderBy, getLimit());
    }
}
