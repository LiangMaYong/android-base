package com.liangmayong.base.support.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangMaYong on 2016/10/28.
 */
public class DataModel {

    private ContentValues values = new ContentValues();
    private List<String> fields = new ArrayList<String>();
    private DataTable table = null;

    /**
     * get fields
     *
     * @return fields
     */
    public List<String> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "[" + values + "]";
    }

    /**
     * init
     *
     * @param table table
     */
    @SuppressLint("NewApi")
    public DataModel(DataTable table) {
        this.table = table;
        List<String> strings = table.getDataHandler().getFields();
        for (int i = 0; i < strings.size(); i++) {
            if (!fields.contains(strings.get(i))) {
                fields.add(strings.get(i));
            }
        }
    }

    /**
     * update
     *
     * @return lines
     */
    public int update() {
        try {
            return table.getDataHandler().update(this);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * delete
     *
     * @return lines
     */
    public int delete() {
        try {
            int del = table.getDataHandler().delete(this);
            if (del > 0) {
                removeAll();
            }
            return del;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * get values
     *
     * @return values
     */
    public ContentValues getValues() {
        ContentValues contentValues = new ContentValues(values);
        contentValues.remove("_id");
        if (!contentValues.containsKey("timestamp")) {
            contentValues.put("timestamp", System.currentTimeMillis());
        }
        return contentValues;
    }

    /**
     * setTimestamp
     *
     * @param timestamp timestamp
     */
    public void setTimestamp(long timestamp) {
        put("timestamp", timestamp);
    }

    /**
     * getTimestamp
     *
     * @return timestamp
     */
    public long getTimestamp() {
        return getLong("timestamp");
    }

    /**
     * put all
     *
     * @param contentValues values
     */
    public void putAll(ContentValues contentValues) {
        this.values.putAll(contentValues);
    }

    /**
     * remove all
     */
    public void removeAll() {
        this.values.clear();
    }

    /**
     * put
     *
     * @param key   key
     * @param value value
     * @return DataModel
     */
    public DataModel put(String key, Object value) {
        if (!fields.contains(key)) {
            return this;
        }
        if (value instanceof Integer) {
            values.put(key, ((Integer) value).intValue());
        } else if (value instanceof String) {
            values.put(key, (String) value);
        } else if (value instanceof Double) {
            double d = ((Double) value).doubleValue();
            values.put(key, d);
        } else if (value instanceof Float) {
            float f = ((Float) value).floatValue();
            values.put(key, f);
        } else if (value instanceof Long) {
            long l = ((Long) value).longValue();
            values.put(key, l);
        } else if (value instanceof Boolean) {
            boolean b = ((Boolean) value).booleanValue();
            values.put(key, b);
        } else if (value instanceof byte[]) {
            byte[] b = (byte[]) value;
            values.put(key, b);
        } else if (value instanceof Byte) {
            Byte b = (Byte) value;
            values.put(key, b);
        } else if (value instanceof Short) {
            Short b = (Short) value;
            values.put(key, b);
        } else if (value instanceof Long) {
            Long l = (Long) value;
            values.put(key, l);
        } else {
            values.putNull(key);
        }
        return this;
    }

    /**
     * get boolean
     *
     * @param key key
     * @return true or false
     */
    public boolean getBoolean(String key) {
        boolean value = false;
        if (values.containsKey(key)) {
            try {
                value = Boolean.valueOf(values.get(key).toString());
            } catch (Exception e) {
            }
        }
        return value;
    }

    /**
     * get byte[]
     *
     * @param key key
     * @return byte[]
     */
    public byte[] getBlob(String key) {
        if (values.containsKey(key)) {
            try {
                return (byte[]) values.get(key);
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * get string
     *
     * @param key key
     * @return string
     */
    public String getString(String key) {
        if (values.containsKey(key)) {
            try {
                return values.get(key).toString();
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * get float
     *
     * @param key key
     * @return float
     */
    public Float getFloat(String key) {
        if (values.containsKey(key)) {
            try {
                return Float.parseFloat(values.get(key).toString());
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * get integer
     *
     * @param key key
     * @return integer
     */
    public int getInteger(String key) {
        if (values.containsKey(key)) {
            try {
                return Integer.parseInt(values.get(key).toString());
            } catch (Exception e) {
            }
        }
        return 0;
    }

    /**
     * getLong
     *
     * @param key key
     * @return long
     */
    public long getLong(String key) {
        if (values.containsKey(key)) {
            try {
                return Long.parseLong(values.get(key).toString());
            } catch (Exception e) {
            }
        }
        return 0;
    }

    /**
     * get
     *
     * @param key key
     * @return object
     */
    public Object get(String key) {
        if (values.containsKey(key)) {
            return values.get(key);
        } else {
            return null;
        }
    }

    /**
     * get id
     *
     * @return id
     */
    public long getId() {
        if (values.containsKey("_id")) {
            return values.getAsLong("_id");
        } else {
            return 0;
        }
    }

    /**
     * toBean
     *
     * @param t   t
     * @param <T> bean type
     * @return
     */
    public <T extends DataBean> T toBean(T t) {
        t.writeModel(values);
        return t;
    }

    /**
     * writeModel
     *
     * @param bean bean
     */
    public void writeModel(DataBean bean) {
        putAll(bean.writeValues());
    }
}
