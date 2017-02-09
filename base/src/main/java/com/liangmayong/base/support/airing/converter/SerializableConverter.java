package com.liangmayong.base.support.airing.converter;

import android.os.Bundle;

import com.liangmayong.base.support.airing.AiringConverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by LiangMaYong on 2017/1/16.
 */
public class SerializableConverter implements AiringConverter {

    private final AiringConverter converter;

    public SerializableConverter(AiringConverter converter) {
        this.converter = converter;
    }

    @Override
    public Object toEvent(Bundle extras) {
        String type = extras.containsKey(AIRING_OBJ_TYPE_EXTRA) ? extras.getString(AIRING_OBJ_TYPE_EXTRA) : "";
        if (getType().equals(type)) {
            try {
                byte[] raw = extras.getByteArray("airing_obj_serializ_extra");
                return bytes2Object(raw);
            } catch (Exception e) {
            }
        }
        if (converter == null) {
            return null;
        }
        return converter.toEvent(extras);
    }

    @Override
    public Bundle toExtras(Object obj) {
        Bundle bundle = new Bundle();
        if (obj != null) {
            boolean flag = false;
            if (obj instanceof Serializable) {
                try {
                    bundle.putByteArray("airing_obj_serializ_extra", object2Bytes(obj));
                    bundle.putString(AIRING_OBJ_TYPE_EXTRA, getType());
                    flag = true;
                } catch (Exception e) {
                }
            }
            if (!flag && converter != null) {
                return converter.toExtras(obj);
            }
        }
        return bundle;
    }

    @Override
    public String getType() {
        return "serializable";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Converting objects to byte arrays
     */
    private static byte[] object2Bytes(Object o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        return baos.toByteArray();
    }

    /**
     * Converting byte arrays to objects
     */
    private static Object bytes2Object(byte raw[])
            throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(raw);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object o = ois.readObject();
        return o;
    }
}
