package com.liangmayong.base.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by LiangMaYong on 2016/11/1.
 */

public class IntentBuilder {
    // extras
    private Bundle extras = null;


    public IntentBuilder() {
    }

    public IntentBuilder(Bundle extras) {
        if (extras != null) {
            if (this.extras == null) {
                this.extras = new Bundle();
            }
            this.extras.putAll(extras);
        }
    }

    public IntentBuilder put(String key, CharSequence value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putCharSequence(key, value);
        return this;
    }

    public IntentBuilder put(String key, String value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putString(key, value);
        return this;
    }

    public IntentBuilder put(String key, String[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putStringArray(key, value);
        return this;
    }

    public IntentBuilder put(String key, int value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putInt(key, value);
        return this;
    }


    public IntentBuilder put(String key, boolean value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putBoolean(key, value);
        return this;
    }


    public IntentBuilder put(String key, boolean[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putBooleanArray(key, value);
        return this;
    }

    public IntentBuilder put(String key, IBinder value) {
        if (extras == null) {
            extras = new Bundle();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            extras.putBinder(key, value);
        }
        return this;
    }


    public IntentBuilder put(String key, Bundle value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putBundle(key, value);
        return this;
    }


    public IntentBuilder put(String key, byte value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putByte(key, value);
        return this;
    }

    public IntentBuilder put(String key, byte[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putByteArray(key, value);
        return this;
    }

    public IntentBuilder put(String key, char value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putChar(key, value);
        return this;
    }

    public IntentBuilder put(String key, char[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putCharArray(key, value);
        return this;
    }

    public IntentBuilder put(String key, CharSequence[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putCharSequenceArray(key, value);
        return this;
    }

    public IntentBuilder put(String key, float value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putFloat(key, value);
        return this;
    }

    public IntentBuilder put(String key, float[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putFloatArray(key, value);
        return this;
    }

    public IntentBuilder put(String key, Parcelable value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putParcelable(key, value);
        return this;
    }

    public IntentBuilder put(String key, Parcelable[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putParcelableArray(key, value);
        return this;
    }

    public IntentBuilder put(String key, Serializable value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putSerializable(key, value);
        return this;
    }

    public IntentBuilder put(String key, int[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putIntArray(key, value);
        return this;
    }

    public IntentBuilder put(String key, double value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putDouble(key, value);
        return this;
    }

    public IntentBuilder put(String key, double[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putDoubleArray(key, value);
        return this;
    }

    public IntentBuilder put(String key, long value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putLong(key, value);
        return this;
    }

    public IntentBuilder put(String key, long[] value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putLongArray(key, value);
        return this;
    }

    public IntentBuilder putParcelableArrayList(String key, ArrayList<Parcelable> value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putParcelableArrayList(key, value);
        return this;
    }

    public IntentBuilder putIntegerArrayList(String key, ArrayList<Integer> value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putIntegerArrayList(key, value);
        return this;
    }

    public IntentBuilder putCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putCharSequenceArrayList(key, value);
        return this;
    }

    public IntentBuilder put(String key, ArrayList<String> value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putStringArrayList(key, value);
        return this;
    }

    public IntentBuilder putAll(Bundle value) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putAll(value);
        return this;
    }

    /**
     * builder
     *
     * @return intent
     */
    public Intent builder() {
        Intent intent = new Intent();
        if (extras == null) {
            return intent;
        }
        intent.putExtras(new Bundle(extras));
        return intent;
    }

    /**
     * builder
     *
     * @return intent
     */
    public Intent builder(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        if (extras == null) {
            return intent;
        }
        intent.putExtras(new Bundle(extras));
        return intent;
    }
}
