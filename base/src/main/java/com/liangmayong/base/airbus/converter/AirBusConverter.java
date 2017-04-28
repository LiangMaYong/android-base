package com.liangmayong.base.airbus.converter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by LiangMaYong on 2017/4/28.
 */
public class AirBusConverter {

    private AirBusConverter() {
    }

    private static final String AIRBUS_EVENT_EXTRAS = "airbus_event_extras";
    private static final String AIRBUS_EVENT_TYPE = "airbus_event_type";

    private static final HashMap<String, Object> OBJECTS = new HashMap<>();
    private static final int AIRBUS_TYPE_PARCELABLE = 1;
    private static final int AIRBUS_TYPE_SERIALIZABLE = 2;
    private static final int AIRBUS_TYPE_OBJECT = 3;

    private static Handler mHandler = new Handler();

    private static class RemoveRunnable implements Runnable {

        private String eventId = "";

        public RemoveRunnable(String eventId) {
            this.eventId = eventId;
        }

        @Override
        public void run() {
            OBJECTS.remove(eventId);
        }
    }

    /**
     * parserEvent
     *
     * @param extras extras
     * @return event
     */
    public static Object parserEvent(Bundle extras) {
        int type = extras.getInt(AIRBUS_EVENT_TYPE);
        if (type == AIRBUS_TYPE_PARCELABLE) {
            return extras.getParcelable(AIRBUS_EVENT_EXTRAS);
        } else if (type == AIRBUS_TYPE_SERIALIZABLE) {
            return extras.getSerializable(AIRBUS_EVENT_EXTRAS);
        } else {
            String eventId = extras.getString(AIRBUS_EVENT_EXTRAS);
            if (OBJECTS.containsKey(eventId)) {
                Object object = OBJECTS.get(eventId);
                mHandler.postDelayed(new RemoveRunnable(eventId), 3000);
                return object;
            }
        }
        return null;
    }

    /**
     * parserExtras
     *
     * @param event event
     * @return extras
     */
    public static Bundle parserExtras(Object event) {
        Bundle extras = new Bundle();
        if (event instanceof Parcelable) {
            extras.putParcelable(AIRBUS_EVENT_EXTRAS, (Parcelable) event);
            extras.putInt(AIRBUS_EVENT_TYPE, AIRBUS_TYPE_PARCELABLE);
        } else if (event instanceof Serializable) {
            extras.putSerializable(AIRBUS_EVENT_EXTRAS, (Serializable) event);
            extras.putInt(AIRBUS_EVENT_TYPE, AIRBUS_TYPE_SERIALIZABLE);
        } else {
            String eventId = UUID.randomUUID().toString();
            OBJECTS.put(eventId, event);
            extras.putString(AIRBUS_EVENT_EXTRAS, eventId);
            extras.putInt(AIRBUS_EVENT_TYPE, AIRBUS_TYPE_OBJECT);
        }
        return extras;
    }
}
