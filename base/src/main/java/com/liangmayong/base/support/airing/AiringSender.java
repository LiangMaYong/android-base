package com.liangmayong.base.support.airing;

import android.os.Bundle;

import java.lang.reflect.Method;

/**
 * AiringSender
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class AiringSender {

    // sendAction
    private static Method sendAction;
    // sendActionName
    private static String sendActionName = "sendAction";
    // action
    private final String action;
    // airing
    private final Airing airing;
    // _extras
    private final Bundle extras;
    // _event
    private Object event = null;

    /**
     * AiringSender
     *
     * @param airing airing
     * @param action action
     */
    public AiringSender(Airing airing, String action) {
        this.action = action;
        this.airing = airing;
        this.extras = new Bundle();
        _reset();
    }

    /**
     * postToTarget
     *
     * @param what   what
     * @param extras bundle
     * @param event  event
     */
    public void postToTarget(int what, Bundle extras, Object event) {
        _reset();
        _extras(extras)._what(what)._event(event)._sendToTarget();
    }

    /**
     * postToTarget
     *
     * @param what   what
     * @param extras bundle
     */
    public void postToTarget(int what, Bundle extras) {
        _reset();
        _extras(extras)._what(what)._sendToTarget();
    }

    /**
     * postToTarget
     *
     * @param event event
     */
    public void postToTarget(Object event) {
        _reset();
        _event(event)._sendToTarget();
    }

    /**
     * postEmtpyToTarget
     */
    public void postEmtpyToTarget() {
        _reset();
        _sendToTarget();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////  Private  /////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * _what
     *
     * @param what what
     * @return sender
     */
    private AiringSender _what(int what) {
        extras.putInt(AiringContent.AIRING_WHAT_EXTRA, what);
        return this;
    }

    /**
     * _extras
     *
     * @param extras extras
     * @return sender
     */
    private AiringSender _extras(Bundle extras) {
        if (extras != null) {
            this.extras.putAll(extras);
        }
        return this;
    }

    /**
     * _event
     *
     * @param event event
     * @return sender
     */
    private AiringSender _event(Object event) {
        this.event = event;
        return this;
    }

    /**
     * sendEvent
     *
     * @return sender
     */
    private void _sendToTarget() {
        _send(extras, event);
        _reset();
    }


    /**
     * _reset
     */
    private void _reset() {
        this.extras.clear();
        this.extras.putInt(AiringContent.AIRING_WHAT_EXTRA, -1);
        this.event = null;
    }

    /**
     * _send
     *
     * @param bundle bundle
     * @param event  _event
     * @return sender
     */
    private void _send(Bundle bundle, Object event) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (sendAction == null) {
            try {
                sendAction = Airing.class.getDeclaredMethod(sendActionName, String.class, Bundle.class, Object.class);
                sendAction.setAccessible(true);
            } catch (Exception e) {
            }
        }
        if (sendAction != null) {
            try {
                sendAction.invoke(airing, action, bundle, event);
            } catch (Exception e) {
            }
        }
    }
}
