package com.liangmayong.base.support.skin.handlers;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Process;

import com.liangmayong.base.support.skin.SkinManager;
import com.liangmayong.base.support.skin.interfaces.ISkin;
import com.liangmayong.base.support.skin.listeners.OnSkinRefreshListener;
import com.liangmayong.base.support.utils.ContextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public class SkinEvent {

    private static SkinReceiver receiver = null;
    private static final String SKIN_RECEIVER_ACTION = ".android_base_skin_refresh_action";
    private static final List<OnSkinRefreshListener> SKIN_REFRESH_LISTENERS = new ArrayList<OnSkinRefreshListener>();
    private static boolean isInited = false;

    /**
     * init
     */
    private static void init() {
        if (isInited) {
            return;
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ContextUtils.getApplication().getPackageName() + SKIN_RECEIVER_ACTION);
        receiver = new SkinReceiver(new OnSkinReceiverListener() {
            @Override
            public void onSkinReceiver() {
                refreshSkin();
            }
        });
        ContextUtils.getApplication().registerReceiver(receiver, filter);
        isInited = true;
    }

    /**
     * registerSkinRefresh
     *
     * @param refreshListener refreshListener
     */
    public static void registerSkinRefresh(OnSkinRefreshListener refreshListener) {
        init();
        ISkin skin = SkinManager.get();
        if (refreshListener != null && !SKIN_REFRESH_LISTENERS.contains(refreshListener)) {
            SKIN_REFRESH_LISTENERS.add(refreshListener);
            refreshListener.onSkinRefresh(skin);
        }
    }

    /**
     * unregisterSkinRefresh
     *
     * @param refreshListener refreshListener
     */
    public static void unregisterSkinRefresh(OnSkinRefreshListener refreshListener) {
        init();
        if (refreshListener != null && SKIN_REFRESH_LISTENERS.contains(refreshListener)) {
            SKIN_REFRESH_LISTENERS.remove(refreshListener);
        }
    }

    /**
     * refreshSkin
     */
    public static void refreshSkin() {
        init();
        for (int i = 0; i < SKIN_REFRESH_LISTENERS.size(); i++) {
            SKIN_REFRESH_LISTENERS.get(i).onSkinRefresh(SkinManager.get());
        }
    }

    /**
     * refreshReceiver
     */
    public static void refreshReceiver() {
        init();
        Intent intent = new Intent(ContextUtils.getApplication().getPackageName() + SKIN_RECEIVER_ACTION);
        intent.putExtra("process", getCurrentProcessName(ContextUtils.getApplication()) + "@" + SkinManager.get().hashCode());
        ContextUtils.getApplication().sendBroadcast(intent);
    }

    /**
     * getCurrentProcessName
     *
     * @param context
     * @return process name
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private static String getCurrentProcessName(Context context) {
        int pid = Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        @SuppressWarnings("rawtypes")
        Iterator i$ = mActivityManager.getRunningAppProcesses().iterator();
        ActivityManager.RunningAppProcessInfo appProcess;
        do {
            if (!i$.hasNext()) {
                return null;
            }
            appProcess = (ActivityManager.RunningAppProcessInfo) i$.next();
        } while (appProcess.pid != pid);
        return appProcess.processName;
    }

    public interface OnSkinReceiverListener {
        void onSkinReceiver();
    }

    /**
     * SkinReceiver
     */
    private static class SkinReceiver extends BroadcastReceiver {

        private OnSkinReceiverListener listener;

        public SkinReceiver(OnSkinReceiverListener listener) {
            this.listener = listener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String process = intent.getStringExtra("process");
            String current = getCurrentProcessName(context) + "@" + SkinManager.get().hashCode();
            if (process != null && !process.equals(current)) {
                if (listener != null) {
                    listener.onSkinReceiver();
                }
            }
        }
    }
}
