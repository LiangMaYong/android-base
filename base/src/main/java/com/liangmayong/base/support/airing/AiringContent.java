package com.liangmayong.base.support.airing;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * AiringContent
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class AiringContent {
    public static final String SEPARATOR = "$";
    // AIRING_EVENT_EXTRA
    public static final String AIRING_EVENT_EXTRA = "airing_event_extra";
    // AIRING_WHAT_EXTRA
    public static final String AIRING_WHAT_EXTRA = "airing_what_extra";
    // airingName
    private String airingName = "";
    // action
    private String action = "";
    // extras
    private Bundle extras = null;
    // event
    private Object event = null;
    // what
    private int what = -1;

    public AiringContent(String airingName, String action, int what, Bundle extras, Object event) {
        this.event = event;
        this.airingName = airingName;
        this.action = action;
        this.what = what;
        this.extras = extras;
    }

    /**
     * getAiringName
     *
     * @return airingName
     */
    public String getAiringName() {
        return airingName;
    }

    /**
     * getAction
     *
     * @return action
     */
    public String getAction() {
        return action;
    }

    /**
     * getWhat
     *
     * @return what
     */
    public int getWhat() {
        return what;
    }

    /**
     * getExtras
     *
     * @return extras
     */
    public Bundle getExtras() {
        if (extras == null) {
            return new Bundle();
        }
        return new Bundle(extras);
    }

    /**
     * getEvent
     *
     * @param <T> type
     * @return type instance
     */
    public <T> T getEvent() {
        T t = null;
        try {
            t = (T) event;
        } catch (Exception e) {
        }
        return t;
    }

    @Override
    public String toString() {
        return "AiringContent{" +
                "airingName='" + airingName + '\'' +
                ", action='" + action + '\'' +
                ", extras=" + extras +
                ", event=" + event +
                ", what=" + what +
                '}';
    }

    /**
     * getStringExtra
     *
     * @param key          key
     * @param defualtValue defualtValue
     * @return extra
     */
    public String getStringExtra(String key, String defualtValue) {
        if (extras == null) {
            return defualtValue;
        }
        if (!extras.containsKey(key)) {
            return defualtValue;
        }
        return extras.getString(key);
    }

    /**
     * getStringExtra
     *
     * @param key key
     * @return extra
     */
    public String getStringExtra(String key) {
        return getStringExtra(key, "");
    }

    /**
     * getBooleanArrayExtra
     *
     * @param name name
     * @return extra
     */
    public boolean[] getBooleanArrayExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getBooleanArray(name);
    }

    /**
     * getBooleanExtra
     *
     * @param name         name
     * @param defaultValue defaultValue
     * @return extra
     */
    public boolean getBooleanExtra(String name, boolean defaultValue) {
        if (extras == null) {
            return defaultValue;
        }
        return extras.getBoolean(name, defaultValue);
    }

    /**
     * getBundleExtra
     *
     * @param name name
     * @return extra
     */
    public Bundle getBundleExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getBundle(name);
    }

    /**
     * getByteArrayExtra
     *
     * @param name name
     * @return extra
     */
    public byte[] getByteArrayExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getByteArray(name);
    }

    /**
     * getByteExtra
     *
     * @param name         name
     * @param defualtValue defualtValue
     * @return extra
     */
    public byte getByteExtra(String name, byte defualtValue) {
        if (extras == null) {
            return defualtValue;
        }
        return extras.getByte(name, defualtValue);
    }

    /**
     * getCharArrayExtra
     *
     * @param name name
     * @return extra
     */
    public char[] getCharArrayExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getCharArray(name);
    }

    /**
     * getCharExtra
     *
     * @param name         name
     * @param defaultValue defaultValue
     * @return extra
     */
    public char getCharExtra(String name, char defaultValue) {
        if (extras == null) {
            return defaultValue;
        }
        return extras.getChar(name, defaultValue);
    }

    /**
     * getCharSequenceArrayExtra
     *
     * @param name name
     * @return extra
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public CharSequence[] getCharSequenceArrayExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getCharSequenceArray(name);
    }

    /**
     * getCharSequenceArrayListExtra
     *
     * @param name name
     * @return extra
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public ArrayList<CharSequence> getCharSequenceArrayListExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getCharSequenceArrayList(name);
    }

    /**
     * getCharSequenceExtra
     *
     * @param name name
     * @return extra
     */
    public CharSequence getCharSequenceExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getCharSequence(name);
    }

    /**
     * getFloatExtra
     *
     * @param name name
     * @return extra
     */
    public double[] getDoubleArrayExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getDoubleArray(name);
    }

    /**
     * getDoubleExtra
     *
     * @param name         name
     * @param defaultValue defaultValue
     * @return extra
     */
    public double getDoubleExtra(String name, double defaultValue) {
        if (extras == null) {
            return defaultValue;
        }
        return extras.getDouble(name, defaultValue);
    }

    /**
     * getFloatArrayExtra
     *
     * @param name name
     * @return extra
     */
    public float[] getFloatArrayExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getFloatArray(name);
    }

    /**
     * getFloatExtra
     *
     * @param name         name
     * @param defaultValue defaultValue
     * @return extra
     */
    public float getFloatExtra(String name, float defaultValue) {
        if (extras == null) {
            return defaultValue;
        }
        return extras.getFloat(name, defaultValue);
    }

    /**
     * getIntArrayExtra
     *
     * @param name name
     * @return extra
     */
    public int[] getIntArrayExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getIntArray(name);
    }

    /**
     * getIntExtra
     *
     * @param name         name
     * @param defaultValue defaultValue
     * @return extra
     */
    public int getIntExtra(String name, int defaultValue) {
        if (extras == null) {
            return defaultValue;
        }
        return extras.getInt(name, defaultValue);
    }

    /**
     * getIntegerArrayListExtra
     *
     * @param name name
     * @return extra
     */
    public ArrayList<Integer> getIntegerArrayListExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getIntegerArrayList(name);
    }

    /**
     * getLongArrayExtra
     *
     * @param name name
     * @return extra
     */
    public long[] getLongArrayExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getLongArray(name);
    }

    /**
     * getLongExtra
     *
     * @param name         name
     * @param defaultValue defaultValue
     * @return extra
     */
    public long getLongExtra(String name, long defaultValue) {
        if (extras == null) {
            return defaultValue;
        }
        return extras.getLong(name, defaultValue);
    }

    /**
     * getParcelableArrayExtra
     *
     * @param name name
     * @return extra
     */
    public Parcelable[] getParcelableArrayExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getParcelableArray(name);
    }

    /**
     * getParcelableArrayListExtra
     *
     * @param name name
     * @param <T>  type
     * @return extra
     */
    public <T extends Parcelable> ArrayList<T> getParcelableArrayListExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getParcelableArrayList(name);
    }

    /**
     * getParcelableExtra
     *
     * @param name name
     * @param <T>  type
     * @return extra
     */
    public <T extends Parcelable> T getParcelableExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getParcelable(name);
    }

    /**
     * getSerializableExtra
     *
     * @param name name
     * @return extra
     */
    public Serializable getSerializableExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getSerializable(name);
    }

    /**
     * getShortArrayExtra
     *
     * @param name name
     * @return extra
     */
    public short[] getShortArrayExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getShortArray(name);
    }

    /**
     * getShortExtra
     *
     * @param name         name
     * @param defaultValue defaultValue
     * @return extra
     */
    public short getShortExtra(String name, short defaultValue) {
        if (extras == null) {
            return defaultValue;
        }
        return extras.getShort(name, defaultValue);
    }

    /**
     * getStringArrayExtra
     *
     * @param name name
     * @return extra
     */
    public String[] getStringArrayExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getStringArray(name);
    }

    /**
     * getStringArrayListExtra
     *
     * @param name name
     * @return extra
     */
    public ArrayList<String> getStringArrayListExtra(String name) {
        if (extras == null) {
            return null;
        }
        return extras.getStringArrayList(name);
    }

}
