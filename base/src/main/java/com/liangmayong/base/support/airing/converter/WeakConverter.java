package com.liangmayong.base.support.airing.converter;

import android.os.Bundle;

import com.liangmayong.base.support.airing.AiringConverter;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by LiangMaYong on 2017/1/16.
 */

public class WeakConverter implements AiringConverter {

    private static final HashMap<String, WeakReference> OBJECT_HASH_MAP = new HashMap<String, WeakReference>();

    @Override
    public Object toEvent(Bundle extras) {
        if (extras == null) {
            return null;
        }
        String type = extras.containsKey(AIRING_OBJ_TYPE_EXTRA) ? extras.getString(AIRING_OBJ_TYPE_EXTRA) : "";
        if (getType().equals(type)) {
            try {
                String key = extras.getString("airing_obj_weak_extra");
                if (OBJECT_HASH_MAP.containsKey(key)) {
                    return OBJECT_HASH_MAP.get(key).get();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public Bundle toExtras(Object obj) {
        Bundle bundle = new Bundle();
        if (obj != null) {
            try {
                String key = System.currentTimeMillis() + "@" + obj.hashCode();
                OBJECT_HASH_MAP.put(key, new WeakReference(obj));
                bundle.putString("airing_obj_weak_extra", key);
                bundle.putString(AIRING_OBJ_TYPE_EXTRA, getType());
            } catch (Exception e) {
            }
        }
        return bundle;
    }

    @Override
    public String getType() {
        return "weak";
    }
}
