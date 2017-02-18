package com.liangmayong.base.support.router;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/2/18.
 */
public class RouterProvider {

    private final String providerName;

    public RouterProvider(@NonNull String providerName) {
        this.providerName = providerName;
    }

    // activityRouters
    private final Map<String, Class<? extends Activity>> activityRouters = new HashMap<String, Class<? extends Activity>>();
    // serviceRouters
    private final Map<String, Class<? extends Service>> serviceRouters = new HashMap<String, Class<? extends Service>>();
    // receiverRouters
    private final Map<String, Class<? extends BroadcastReceiver>> receiverRouters = new HashMap<String, Class<? extends BroadcastReceiver>>();

    protected void routerActivity(String name, Class<? extends Activity> klass) {
        if (klass != null) {
            activityRouters.put(providerName + ":" + name, klass);
        } else {
            if (activityRouters.containsKey(providerName + ":" + name)) {
                activityRouters.remove(providerName + ":" + name);
            }
        }
    }

    protected void routerService(String name, Class<? extends Service> klass) {
        if (klass != null) {
            serviceRouters.put(providerName + ":" + name, klass);
        } else {
            if (serviceRouters.containsKey(providerName + ":" + name)) {
                serviceRouters.remove(providerName + ":" + name);
            }
        }
    }

    protected void routerReceiver(String name, Class<? extends BroadcastReceiver> klass) {
        if (klass != null) {
            receiverRouters.put(providerName + ":" + name, klass);
        } else {
            if (receiverRouters.containsKey(providerName + ":" + name)) {
                receiverRouters.remove(providerName + ":" + name);
            }
        }
    }

    public Map<String, Class<? extends Activity>> getActivityRouters() {
        return activityRouters;
    }

    public Map<String, Class<? extends Service>> getServiceRouters() {
        return serviceRouters;
    }

    public Map<String, Class<? extends BroadcastReceiver>> getReceiverRouters() {
        return receiverRouters;
    }
}
