package com.liangmayong.base.support.airing;

import java.lang.reflect.Method;

/**
 * AiringObserver
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class AiringObserver {

    // methods
    private static Method registerAction, registerActions, unregisterAction, unregisterActions, unregisterObject;
    // object
    private Object object;
    // airing
    private Airing airing;

    public AiringObserver(Airing airing, Object object) {
        this.airing = airing;
        this.object = object;
    }

    /**
     * register
     *
     * @param action        action
     * @param eventListener eventListener
     * @return observer
     */
    public AiringObserver register(final String action, final OnAiringListener eventListener) {
        if (registerAction == null) {
            try {
                registerAction = Airing.class.getDeclaredMethod("register", Object.class, String.class,
                        OnAiringListener.class);
                registerAction.setAccessible(true);
            } catch (Exception e) {
            }
        }
        if (registerAction != null) {
            try {
                registerAction.invoke(airing, object, action, eventListener);
            } catch (Exception e) {
            }
        }
        return this;
    }

    /**
     * register
     *
     * @param actions       actions
     * @param eventListener eventListener
     * @return observer
     */
    public AiringObserver register(final String[] actions, final OnAiringListener eventListener) {
        if (registerActions == null) {
            try {
                registerActions = Airing.class.getDeclaredMethod("register", Object.class, String[].class,
                        OnAiringListener.class);
                registerActions.setAccessible(true);
            } catch (Exception e) {
            }
        }
        if (registerActions != null) {
            try {
                registerActions.invoke(airing, object, actions, eventListener);
            } catch (Exception e) {
            }
        }
        return this;
    }

    /**
     * unregister
     *
     * @param action action
     * @return observer
     */
    public AiringObserver unregister(String action) {
        if (unregisterAction == null) {
            try {
                unregisterAction = Airing.class.getDeclaredMethod("unregister", Object.class, String.class);
                unregisterAction.setAccessible(true);
            } catch (Exception e) {
            }
        }
        if (unregisterAction != null) {
            try {
                unregisterAction.invoke(airing, object, action);
            } catch (Exception e) {
            }
        }
        return this;
    }

    /**
     * unregister
     *
     * @param actions actions
     * @return observer
     */
    public AiringObserver unregister(String[] actions) {
        if (unregisterActions == null) {
            try {
                unregisterActions = Airing.class.getDeclaredMethod("unregister", Object.class, String[].class);
                unregisterActions.setAccessible(true);
            } catch (Exception e) {
            }
        }
        if (unregisterActions != null) {
            try {
                unregisterActions.invoke(airing, object, actions);
            } catch (Exception e) {
            }
        }
        return this;
    }

    /**
     * unregister
     *
     * @return observer
     */
    public AiringObserver unregister() {
        if (unregisterObject == null) {
            try {
                unregisterObject = Airing.class.getDeclaredMethod("unregister", Object.class);
                unregisterObject.setAccessible(true);
            } catch (Exception e) {
            }
        }
        if (unregisterObject != null) {
            try {
                unregisterObject.invoke(airing, object);
            } catch (Exception e) {
            }
        }
        return this;
    }
}
