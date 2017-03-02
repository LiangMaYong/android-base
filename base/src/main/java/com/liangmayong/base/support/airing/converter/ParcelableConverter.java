package com.liangmayong.base.support.airing.converter;

import android.os.Bundle;
import android.os.Parcelable;

import com.liangmayong.base.support.airing.AiringConverter;

/**
 * Created by LiangMaYong on 2017/1/16.
 */
public class ParcelableConverter implements AiringConverter {

    private final AiringConverter converter;

    public ParcelableConverter() {
        this.converter = new SerializableConverter(new WeakConverter());
    }

    @Override
    public Object toEvent(Bundle extras) {
        if (extras == null) {
            return null;
        }
        String type = extras.containsKey(AIRING_OBJ_TYPE_EXTRA) ? extras.getString(AIRING_OBJ_TYPE_EXTRA) : "";
        if (getType().equals(type)) {
            return extras.getParcelable("airing_obj_parcel_extra");
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
            if (obj instanceof Parcelable) {
                bundle.putParcelable("airing_obj_parcel_extra", (Parcelable) obj);
                bundle.putString(AIRING_OBJ_TYPE_EXTRA, getType());
                flag = true;
            }
            if (!flag && converter != null) {
                return converter.toExtras(obj);
            }
        }
        return bundle;
    }

    @Override
    public String getType() {
        return "parcelable";
    }
}
