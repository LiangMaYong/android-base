package com.liangmayong.base.support.airing;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.liangmayong.base.support.airing.converter.ParcelableConverter;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Airing
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class Airing {

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String TAG = "Airing";


    /**
     * isDebugable
     *
     * @return true or false
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    private static boolean isDebugable() {
        try {
            ApplicationInfo info = getApplication().getApplicationInfo();
            boolean debugable = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            return debugable;
        } catch (Exception e) {
            return false;
        }
    }


    // application
    private static WeakReference<Application> application = null;

    /**
     * getApplication
     *
     * @return application
     */
    private static Application getApplication() {
        if (application == null || application.get() == null) {
            synchronized (Application.class) {
                if (application == null) {
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
        }
        return application.get();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Airing() {
    }

    // airingMap
    private static Map<String, Airing> airingMap = new HashMap<String, Airing>();

    /**
     * getDefault
     *
     * @return airing
     */
    public static Airing getDefault() {
        return get(getApplication().getPackageName());
    }

    /**
     * get
     *
     * @param airingName airingName
     * @return airing
     */
    public static Airing get(String airingName) {
        if (airingMap.containsKey(airingName)) {
            return airingMap.get(airingName);
        } else {
            Airing airing = new Airing();
            airing.setName(airingName);
            airing.setConverter(new ParcelableConverter());
            airingMap.put(airingName, airing);
            return airing;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private String airingName = "";
    private AiringConverter converter;

    /**
     * cache event receiver
     */
    private Map<Object, Map<String, BroadcastReceiver>> receiverMap = new HashMap<Object, Map<String, BroadcastReceiver>>();

    /**
     * setName
     *
     * @param airingName airingName
     */
    private void setName(String airingName) {
        this.airingName = airingName;
    }

    /**
     * setConverter
     *
     * @param converter converter
     */
    private void setConverter(AiringConverter converter) {
        this.converter = converter;
    }

    /**
     * getConverter
     *
     * @return converter
     */
    private AiringConverter getConverter() {
        return converter;
    }

    /**
     * getName
     *
     * @return action prefix
     */
    private String getName() {
        return airingName;
    }

    /**
     * sender
     *
     * @param action action
     * @return action
     */
    public AiringSender sender(String action) {
        return new AiringSender(this, action);
    }

    /**
     * observer
     *
     * @param object object
     * @return action
     */
    public AiringObserver observer(Object object) {
        return new AiringObserver(this, object);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * sendAction
     *
     * @param action action
     * @param bundle bundle
     */
    @SuppressWarnings("unused")
    private void sendAction(String action, Bundle bundle, Object event) {
        Intent intent = new Intent();
        String newAction = getName() + AiringContent.SEPARATOR + action;
        intent.setAction(newAction);
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (event != null) {
            Bundle eventBundle = getConverter().toExtras(event);
            if (eventBundle != null) {
                bundle.putBundle(AiringContent.AIRING_EVENT_EXTRA, eventBundle);
            }
        }
        if (bundle != null && !bundle.isEmpty()) {
            intent.putExtras(bundle);
        }
        if (isDebugable()) {
            Log.d(TAG, "send Airing:" + newAction + " extras:" + bundle);
        }
        getApplication().sendBroadcast(intent);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * register
     *
     * @param object        object
     * @param actions       actions
     * @param eventListener eventListener
     * @return airing
     */
    @SuppressWarnings("unused")
    private Airing register(Object object, final String[] actions, final OnAiringListener eventListener) {
        if (actions != null) {
            for (int i = 0; i < actions.length; i++) {
                register(object, actions[i], eventListener);
            }
        }
        return this;
    }

    /**
     * register
     *
     * @param object        context
     * @param action        action
     * @param eventListener eventListener
     * @return airing
     */
    private Airing register(Object object, final String action, final OnAiringListener eventListener) {
        Map<String, BroadcastReceiver> map = null;
        if (receiverMap.containsKey(object)) {
            map = receiverMap.get(object);
        } else {
            map = new HashMap<String, BroadcastReceiver>();
        }
        BroadcastReceiver broadcastReceiver = new AiringReceiver(getConverter(), getName(), action, eventListener);
        IntentFilter filter = new IntentFilter();
        filter.addAction(getName() + AiringContent.SEPARATOR + action);
        getApplication().registerReceiver(broadcastReceiver, filter);
        int i = 0;
        String key = action + "$" + i;
        while (map.containsKey(key)) {
            i++;
            key = action + "$" + i;
        }
        if (isDebugable()) {
            Log.d(TAG, "register:" + action + " index:" + i);
        }
        map.put(key, broadcastReceiver);
        receiverMap.put(object, map);
        return this;
    }

    /**
     * unregister
     *
     * @param object object
     * @param action action
     */
    private void unregister(Object object, final String action) {
        if (receiverMap.containsKey(object)) {
            Map<String, BroadcastReceiver> map = receiverMap.get(object);
            int i = 0;
            String key = action + "$" + i;
            while (map.containsKey(key)) {
                try {
                    BroadcastReceiver broadcastReceiver = map.get(key);
                    getApplication().unregisterReceiver(broadcastReceiver);
                } catch (Exception e) {
                }
                map.remove(key);
                if (isDebugable()) {
                    Log.d(TAG, "unregister:" + action + " index:" + i);
                }
                i++;
                key = action + "$" + i;
            }
            if (map.isEmpty()) {
                receiverMap.remove(object);
            } else {
                receiverMap.put(object, map);
            }
        }
    }

    /**
     * unregister
     *
     * @param object  object
     * @param actions actions
     */
    @SuppressWarnings("unused")
    private void unregister(Object object, final String[] actions) {
        if (actions != null) {
            for (int i = 0; i < actions.length; i++) {
                unregister(object, actions[i]);
            }
        }
    }

    /**
     * unregister
     *
     * @param object object
     */
    @SuppressWarnings("unused")
    private void unregister(Object object) {
        if (receiverMap.containsKey(object)) {
            Map<String, BroadcastReceiver> map = receiverMap.get(object);
            for (BroadcastReceiver receiver : map.values()) {
                try {
                    getApplication().unregisterReceiver(receiver);
                } catch (Exception e) {
                }
            }
            receiverMap.remove(object);
        }
    }

}
