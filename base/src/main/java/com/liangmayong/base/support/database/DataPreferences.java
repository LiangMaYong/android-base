package com.liangmayong.base.support.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.liangmayong.base.utils.DESUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DataPreferences
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class DataPreferences {

    // DATA_PREFERENCES_HASH_MAP
    private static final Map<String, DataPreferences> DATA_PREFERENCES_HASH_MAP = new HashMap<String, DataPreferences>();
    //PREFERENCES_DATABASE_NAME
    public static final String PREFERENCES_DATABASE_NAME = DataConstant.DEFAULT_PREFERENCES_DATABASE_NAME;
    //DEFAULT_PREFERENCES_TABLE_NAME
    private static final String DEFAULT_PREFERENCES_TABLE_NAME = DataConstant.DEFAULT_PREFERENCES_TABLE_NAME;
    //mPreferencesTable
    private final PreferencesTable mPreferencesTable;
    //encryptListener
    private OnDataEncryptListener encryptListener = null;
    //isReload
    private boolean isReload = false;
    //values
    private final Map<String, String> values = new HashMap<String, String>();
    //default_encryptListener
    private final OnDataEncryptListener default_encryptListener = new OnDataEncryptListener() {
        @Override
        public String encrypt(String key, String value) {
            try {
                return DESUtils.encrypt(value.getBytes("utf-8"), key, true);
            } catch (UnsupportedEncodingException e) {
                return value;
            }
        }

        @Override
        public String decrypt(String key, String value) {
            try {
                byte[] bytes = DESUtils.decrypt(value, key, true);
                return new String(bytes, "utf-8");
            } catch (UnsupportedEncodingException e) {
                return value;
            }
        }
    };

    /**
     * useDefaultEncrypt
     *
     * @return preferences
     */
    public DataPreferences useDefaultEncrypt() {
        setOnDataEncryptListener(default_encryptListener);
        return this;
    }

    /**
     * setOnDataEncryptListener
     *
     * @param encryptListener encryptListener
     * @return preferences
     */
    public DataPreferences setOnDataEncryptListener(OnDataEncryptListener encryptListener) {
        this.encryptListener = encryptListener;
        return this;
    }

    /**
     * setReload
     *
     * @param reload reload
     */
    public DataPreferences setReload(boolean reload) {
        isReload = reload;
        return this;
    }

    /**
     * OnDataEncryptListener
     */
    public interface OnDataEncryptListener {
        /**
         * encrypt
         *
         * @param key   key
         * @param value value
         * @return en
         */
        String encrypt(String key, String value);

        /**
         * decrypt
         *
         * @param key   key
         * @param value value
         * @return de
         */
        String decrypt(String key, String value);
    }

    //DataPreferences
    private DataPreferences(String tablename) {
        mPreferencesTable = new PreferencesTable(DataUtils.getApplication(), tablename);
        useDefaultEncrypt();
    }

    /**
     * getPreferences
     *
     * @param tablename tablename
     * @return preferences
     */
    public static DataPreferences getPreferences(String tablename) {
        if (tablename == null || "".equals(tablename)) {
            tablename = DEFAULT_PREFERENCES_TABLE_NAME;
        }
        tablename = "pref_" + tablename;
        if (DATA_PREFERENCES_HASH_MAP.containsKey(tablename)) {
            return DATA_PREFERENCES_HASH_MAP.get(tablename).setReload(false);
        } else {
            DataPreferences preferences = new DataPreferences(tablename);
            DATA_PREFERENCES_HASH_MAP.put(tablename, preferences);
            return preferences.setReload(false);
        }
    }

    /**
     * getDefaultPreferences
     *
     * @return preferences
     */
    public static DataPreferences getDefaultPreferences() {
        return getPreferences(DEFAULT_PREFERENCES_TABLE_NAME);
    }

    /**
     * getPreferences
     *
     * @return preferences
     */
    public Map<String, String> getPreferences() {
        Map<String, String> map = new HashMap<String, String>();
        List<DataModel> dataModels = mPreferencesTable.getList();
        for (int i = 0; i < dataModels.size(); i++) {
            map.put(dataModels.get(i).getString("skey"), dataModels.get(i).getString("svalue"));
        }
        return map;
    }

    /**
     * clear
     *
     * @return count
     */
    public int clear() {
        return mPreferencesTable.deleteAll();
    }

    /**
     * set
     *
     * @param skey   skey
     * @param svalue svalue
     * @return IBDataConfig
     */
    public DataPreferences setString(String skey, String svalue) {
        if (svalue != null && !"".equals(svalue)) {
            if (encryptListener != null) {
                svalue = encryptListener.encrypt(skey, svalue);
            }
        }
        if (svalue != null) {
            values.put(skey, svalue);
        } else {
            values.remove(skey);
        }
        DataModel model = mPreferencesTable.getModel("skey = '" + skey + "'");
        if (model != null) {
            if (svalue == null) {
                del(skey);
                return this;
            }
            model.put("svalue", svalue);
            model.update();
        } else {
            if (svalue == null) {
                return this;
            }
            mPreferencesTable.inser().put("skey", skey).put("svalue", svalue).commit();
        }
        return this;
    }

    /**
     * getString
     *
     * @param skey skey
     * @return svalue
     */
    public String getString(String skey, String defsvalue) {
        String svalue = null;
        if (isReload || !values.containsKey(skey)) {
            DataModel model = mPreferencesTable.getModel("skey = '" + skey + "'");
            if (model != null) {
                svalue = model.getString("svalue");
            }
        } else {
            svalue = values.get(skey);
        }
        if (svalue != null && !"".equals(svalue)) {
            if (encryptListener != null) {
                svalue = encryptListener.decrypt(skey, svalue);
            }
        }
        if (svalue == null || "".equals(svalue)) {
            return defsvalue;
        }
        return svalue;
    }

    /**
     * contains
     *
     * @param key key
     * @return contains
     */
    public boolean contains(String key) {
        DataModel model = mPreferencesTable.getModel("skey = '" + key + "'");
        if (model != null) {
            return true;
        }
        return false;
    }

    /**
     * getString
     *
     * @param key key
     * @return string
     */
    public String getString(String key) {
        return getString(key, "");
    }

    /**
     * get int
     *
     * @param key      key
     * @param defValue defValue
     * @return int
     */
    public int getInt(String key, int defValue) {
        int mInt = defValue;
        try {
            String string = getString(key, defValue + "");
            mInt = Integer.parseInt(string);
        } catch (Exception e) {
        }
        return mInt;
    }

    /**
     * get boolean
     *
     * @param key      key
     * @param defValue defValue
     * @return boolean
     */
    @SuppressLint("DefaultLocale")
    public boolean getBoolean(String key, boolean defValue) {
        boolean retu = defValue;
        try {
            retu = "Yes".equals(getString(key, defValue ? "Yes" : "No"))
                    || "TRUE".toUpperCase().equals(getString(key, defValue ? "Yes" : "No"));
        } catch (Exception e) {
        }
        return retu;
    }

    /**
     * get float
     *
     * @param key      key
     * @param defValue defValue
     * @return float
     */
    public float getFloat(String key, float defValue) {
        float retu = defValue;
        try {
            String string = getString(key, defValue + "");
            retu = Float.parseFloat(string);
        } catch (Exception e) {
        }
        return retu;
    }

    /**
     * get long
     *
     * @param key      key
     * @param defValue defValue
     * @return long
     */
    public long getLong(String key, long defValue) {
        long retu = defValue;
        try {
            String string = getString(key, defValue + "");
            retu = Long.parseLong(string);
        } catch (Exception e) {
        }
        return retu;
    }

    /**
     * set long
     *
     * @param key   key
     * @param value value
     * @return preference
     */
    public DataPreferences setLong(String key, long value) {
        setString(key, value + "");
        return this;
    }

    /**
     * set float
     *
     * @param key   key
     * @param value value
     * @return preference
     */
    public DataPreferences setFloat(String key, float value) {
        setString(key, value + "");
        return this;
    }

    /**
     * set boolean
     *
     * @param key   key
     * @param value value
     * @return preference
     */
    public DataPreferences setBoolean(String key, boolean value) {
        setString(key, value ? "Yes" : "No");
        return this;
    }

    /**
     * set int
     *
     * @param key   key
     * @param value value
     * @return preference
     */
    public DataPreferences setInt(String key, int value) {
        setString(key, value + "");
        return this;
    }

    /**
     * remove
     *
     * @param key key
     * @return preference
     */
    public DataPreferences remove(String key) {
        del(key);
        return this;
    }

    /**
     * del
     *
     * @param skey skey
     */
    private void del(String skey) {
        mPreferencesTable.delete("skey = '" + skey + "'");
    }

    /**
     * PreferencesTable
     */
    private class PreferencesTable extends DataTable {

        public PreferencesTable(Context context, String tablename) {
            super(context, tablename, PREFERENCES_DATABASE_NAME, 1);
        }

        @Override
        public Map<String, DataType> generateFields() {
            Map<String, DataType> map = new HashMap<String, DataType>();
            map.put("skey", DataType.TEXT().notNull());
            map.put("svalue", DataType.TEXT().notNull());
            return map;
        }

        @Override
        protected void generateOnUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
