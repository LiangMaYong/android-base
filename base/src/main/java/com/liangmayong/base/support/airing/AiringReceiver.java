package com.liangmayong.base.support.airing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * AiringReceiver
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class AiringReceiver extends BroadcastReceiver {

    // eventListener
    private OnAiringListener eventListener;
    // action
    private String action;
    // airingName
    private String airingName;
    // converter
    private AiringConverter converter;

    public AiringReceiver(AiringConverter converter, String airingName, String action, OnAiringListener eventListener) {
        this.converter = converter;
        this.airingName = airingName;
        this.action = action;
        this.eventListener = eventListener;
    }

    /**
     * getAction
     *
     * @return action
     */
    public String getAction() {
        return action;
    }

    /**
     * getAiringName
     *
     * @return airingName
     */
    public String getAiringName() {
        return airingName;
    }

    /**
     * getEventListener
     *
     * @return eventListener
     */
    public OnAiringListener getAiringListener() {
        return eventListener;
    }

    @Override
    public final void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(getAiringName() + AiringContent.SEPARATOR + getAction())) {
            if (getAiringListener() != null) {
                Bundle bundle = intent.getExtras();
                Bundle eventBundle = null;
                if (bundle.containsKey(AiringContent.AIRING_EVENT_EXTRA)) {
                    eventBundle = bundle.getBundle(AiringContent.AIRING_EVENT_EXTRA);
                }
                int what = -1;
                if (bundle != null) {
                    try {
                        what = bundle.getInt(AiringContent.AIRING_WHAT_EXTRA, -1);
                        bundle.remove(AiringContent.AIRING_WHAT_EXTRA);
                    } catch (Exception e) {
                    }
                }
                getAiringListener().onAiring(new AiringContent(getAiringName(), getAction(), what, bundle, converter.toEvent(eventBundle)));
            }
        }
    }
}
