package com.liangmayong.base.utils;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by LiangMaYong on 2016/11/1.
 */

public class BundleBuilder {

    public BundleBuilder() {
    }

    public BundleBuilder(Bundle extras) {
        if (extras != null) {
            if (this.extras == null) {
                this.extras = new Bundle();
            }
            this.extras.putAll(extras);
        }
    }

    // extras
    private Bundle extras = null;

    public BundleBuilder put(String key, CharSequence value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putCharSequence(key, value);
        return this;
    }

    public BundleBuilder put(String key, String value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putString(key, value);
        return this;
    }

    public BundleBuilder put(String key, String[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putStringArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, int value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putInt(key, value);
        return this;
    }


    public BundleBuilder put(String key, boolean value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putBoolean(key, value);
        return this;
    }


    public BundleBuilder put(String key, boolean[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putBooleanArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, IBinder value) {
        if (extras == null) {
            extras = new Bundle();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            extras.putBinder(key, value);
        }
        return this;
    }


    public BundleBuilder put(String key, Bundle value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putBundle(key, value);
        return this;
    }


    public BundleBuilder put(String key, byte value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putByte(key, value);
        return this;
    }

    public BundleBuilder put(String key, byte[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putByteArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, char value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putChar(key, value);
        return this;
    }

    public BundleBuilder put(String key, char[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putCharArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, CharSequence[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putCharSequenceArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, float value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putFloat(key, value);
        return this;
    }

    public BundleBuilder put(String key, float[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putFloatArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, Parcelable value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putParcelable(key, value);
        return this;
    }

    public BundleBuilder put(String key, Parcelable[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putParcelableArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, Serializable value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putSerializable(key, value);
        return this;
    }

    public BundleBuilder put(String key, int[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putIntArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, double value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putDouble(key, value);
        return this;
    }

    public BundleBuilder put(String key, double[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putDoubleArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, long value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putLong(key, value);
        return this;
    }

    public BundleBuilder put(String key, long[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putLongArray(key, value);
        return this;
    }

    public BundleBuilder putParcelableArrayList(String key, ArrayList<Parcelable> value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putParcelableArrayList(key, value);
        return this;
    }

    public BundleBuilder putIntegerArrayList(String key, ArrayList<Integer> value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putIntegerArrayList(key, value);
        return this;
    }

    public BundleBuilder putCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putCharSequenceArrayList(key, value);
        return this;
    }

    public BundleBuilder put(String key, ArrayList<String> value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putStringArrayList(key, value);
        return this;
    }

    public BundleBuilder putAll(Bundle value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putAll(value);
        return this;
    }

    /**
     * builder
     *
     * @return extras
     */
    public Bundle builder() {
        if (extras == null) {
            return new Bundle();
        }
        return new Bundle(extras);
    }

}
