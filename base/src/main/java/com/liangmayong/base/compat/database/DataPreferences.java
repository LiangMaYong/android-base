package com.liangmayong.base.compat.database;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
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
    public static final String PREFERENCES_DATABASE_NAME = DataConstant.DEFUALT_PREFERENCES_DATABASE_NAME;
    //DEFUALT_PREFERENCES_TABLE_NAME
    private static final String DEFUALT_PREFERENCES_TABLE_NAME = DataConstant.DEFUALT_PREFERENCES_TABLE_NAME;
    //mPreferencesTable
    private PreferencesTable mPreferencesTable;

    //DataPreferences
    private DataPreferences(String tablename) {
        mPreferencesTable = new PreferencesTable(getApplication(), tablename);
    }

    /**
     * getPreferences
     *
     * @param tablename tablename
     * @return preferences
     */
    public static DataPreferences getPreferences(String tablename) {
        if (tablename == null || "".equals(tablename)) {
            tablename = DEFUALT_PREFERENCES_TABLE_NAME;
        }
        tablename = "pref_" + tablename;
        if (DATA_PREFERENCES_HASH_MAP.containsKey(tablename)) {
            return DATA_PREFERENCES_HASH_MAP.get(tablename);
        } else {
            DataPreferences preferences = new DataPreferences(tablename);
            DATA_PREFERENCES_HASH_MAP.put(tablename, preferences);
            return preferences;
        }
    }

    /**
     * getDefault
     *
     * @return preferences
     */
    public static DataPreferences getDefaultPreferences() {
        return getPreferences(DEFUALT_PREFERENCES_TABLE_NAME);
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
        DataModel model = mPreferencesTable.getModel("skey = '" + skey + "'");
        String svalue = defsvalue;
        if (model != null) {
            svalue = model.getString("svalue");
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


    // application
    private static WeakReference<Application> application;

    /**
     * getApplication
     *
     * @return application
     */
    private static Application getApplication() {
        if (application == null || application.get() == null) {
            synchronized (DataPreferences.class) {
                try {
                    Class<?> clazz = Class.forName("android.app.ActivityThread");
                    Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
                    if (currentActivityThread != null) {
                        Object object = currentActivityThread.invoke(null);
                        if (object != null) {
                            Method getApplication = object.getClass().getDeclaredMethod("getApplication");
                            if (getApplication != null) {
                                application = new WeakReference<Application>((Application) getApplication.invoke(object));
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
        return application.get();
    }
}
