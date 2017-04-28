package com.liangmayong.base.airbus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;

import com.liangmayong.base.airbus.converter.AirBusConverter;
import com.liangmayong.base.airbus.dispatch.AirBusDispatchListener;
import com.liangmayong.base.airbus.listener.AirBusListener;
import com.liangmayong.base.airbus.receiver.AirBusReceiver;
import com.liangmayong.base.support.utils.ContextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/4/28.
 */
public class AirBus {

    private static final String TAG = AirBus.class.getSimpleName();

    /**
     * getContext
     *
     * @return context
     */
    private static Context getContext() {
        return ContextUtils.getApplication();
    }


    /**
     * isDebug
     *
     * @return true or false
     */
    private static boolean isDebug() {
        try {
            ApplicationInfo info = getContext().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    // airbus
    private final static Map<String, AirBus> AIRBUS = new HashMap<String, AirBus>();

    /**
     * getDefault
     *
     * @return airing
     */
    public static AirBus getDefault() {
        return get(getContext().getPackageName());
    }

    /**
     * get
     *
     * @param airName airName
     * @return airing
     */
    public static AirBus get(String airName) {
        if (AIRBUS.containsKey(airName)) {
            return AIRBUS.get(airName);
        } else {
            AirBus airbus = new AirBus(airName);
            AIRBUS.put(airName, airbus);
            return airbus;
        }
    }

    private final String airName;
    private final Map<Object, BroadcastReceiver> airReceivers;
    private AirBusListener commonListener = null;

    /**
     * AirBus
     *
     * @param airName airName
     */
    private AirBus(String airName) {
        if (AIRBUS.containsKey(airName)) {
            throw new IllegalArgumentException("AirBus already exists：" + airName);
        }
        this.airName = airName;
        this.airReceivers = new HashMap<>();
        registerCommonListener();
    }

    /**
     * setCommonListener
     *
     * @param commonListener commonListener
     */
    public void setCommonListener(AirBusListener commonListener) {
        this.commonListener = commonListener;
    }

    /**
     * registerCommonListener
     */
    private void registerCommonListener() {
        try {
            BroadcastReceiver broadcastReceiver = new AirBusReceiver(airName, new AirBusListener() {
                @Override
                public void onAirBus(Object event) {
                    if (commonListener != null) {
                        commonListener.onAirBus(event);
                    }
                }
            });
            IntentFilter filter = new IntentFilter();
            filter.addAction(airName);
            getContext().registerReceiver(broadcastReceiver, filter);
        } catch (Exception e) {
        }
    }

    /**
     * post
     *
     * @param event event
     */
    public void post(Object event) {
        try {
            if (isDebug()) {
                Log.d(TAG, "AirBus post:" + airName + " event:" + event);
            }
            Intent intent = new Intent();
            intent.setAction(airName);
            Bundle extras = AirBusConverter.parserExtras(event);
            if (extras != null) {
                intent.putExtras(extras);
                getContext().sendBroadcast(intent, getContext().getPackageName() + ".permission.AIRBUS_RECEIVER");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * register
     *
     * @param object object
     */
    public void register(Object object) {
        try {
            if (object == null) {
                return;
            }
            if (isDebug()) {
                Log.d(TAG, "AirBus register:" + object);
            }
            if (airReceivers.containsKey(object)) {
                return;
            }
            BroadcastReceiver broadcastReceiver = new AirBusReceiver(airName, new AirBusDispatchListener(object));
            IntentFilter filter = new IntentFilter();
            filter.addAction(airName);
            getContext().registerReceiver(broadcastReceiver, filter);
            airReceivers.put(object, broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * unregister
     *
     * @param object object
     */
    public void unregister(Object object) {
        try {
            if (object == null) {
                return;
            }
            if (isDebug()) {
                Log.d(TAG, "AirBus unregister:" + object);
            }
            if (airReceivers.containsKey(object)) {
                BroadcastReceiver broadcastReceiver = airReceivers.get(object);
                try {
                    getContext().unregisterReceiver(broadcastReceiver);
                } catch (Exception e) {
                }
                airReceivers.remove(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
