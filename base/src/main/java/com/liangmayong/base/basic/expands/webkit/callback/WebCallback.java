package com.liangmayong.base.basic.expands.webkit.callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangMaYong on 2017/1/3.
 */
public class WebCallback {

    public interface Callback {
        void onCall(String url, String functionName, String jsonString);
    }

    private static final List<Callback> CALLBACKS = new ArrayList<Callback>();

    /**
     * registerCallbackListener
     *
     * @param callbackListener callbackListener
     */
    public static void registerCallbackListener(Callback callbackListener) {
        if (callbackListener != null && !CALLBACKS.contains(callbackListener)) {
            CALLBACKS.add(callbackListener);
        }
    }

    /**
     * unregisterCallbackListener
     *
     * @param callbackListener callbackListener
     */
    public static void unregisterCallbackListener(Callback callbackListener) {
        if (callbackListener != null && CALLBACKS.contains(callbackListener)) {
            CALLBACKS.remove(callbackListener);
        }
    }

    /**
     * call
     */
    public static void call(String url, String functionName, String jsonString) {
        for (int i = 0; i < CALLBACKS.size(); i++) {
            CALLBACKS.get(i).onCall(url, functionName, jsonString);
        }
    }

    private WebCallback() {
    }
}
