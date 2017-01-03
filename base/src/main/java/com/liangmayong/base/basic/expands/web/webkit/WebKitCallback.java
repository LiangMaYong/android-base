package com.liangmayong.base.basic.expands.web.webkit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangMaYong on 2017/1/3.
 */
public class WebKitCallback {

    public interface OnWebKitCallbackListener {
        void onCall(String url, String functionName, String jsonString);
    }

    private static final List<OnWebKitCallbackListener> CALLBACK_LISTENERS = new ArrayList<OnWebKitCallbackListener>();

    /**
     * registerCallbackListener
     *
     * @param callbackListener callbackListener
     */
    public static void registerCallbackListener(OnWebKitCallbackListener callbackListener) {
        if (callbackListener != null && !CALLBACK_LISTENERS.contains(callbackListener)) {
            CALLBACK_LISTENERS.add(callbackListener);
        }
    }

    /**
     * unregisterCallbackListener
     *
     * @param callbackListener callbackListener
     */
    public static void unregisterCallbackListener(OnWebKitCallbackListener callbackListener) {
        if (callbackListener != null && CALLBACK_LISTENERS.contains(callbackListener)) {
            CALLBACK_LISTENERS.remove(callbackListener);
        }
    }

    /**
     * call
     */
    public static void call(String url, String functionName, String jsonString) {
        for (int i = 0; i < CALLBACK_LISTENERS.size(); i++) {
            CALLBACK_LISTENERS.get(i).onCall(url, functionName, jsonString);
        }
    }

    private WebKitCallback() {
    }
}
