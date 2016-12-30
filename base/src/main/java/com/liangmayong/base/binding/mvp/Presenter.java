package com.liangmayong.base.binding.mvp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by LiangMaYong on 2016/9/17.
 */
public class Presenter<V> {

    //viewStub
    private V viewStub = null;
    //isAttached
    private boolean isAttached = false;

    /**
     * getViewStub
     *
     * @return view
     */
    protected final V getViewStub() {
        return viewStub;
    }

    /**
     * isAttached
     *
     * @return isAttached
     */
    public final boolean isAttached() {
        return isAttached;
    }

    /**
     * attach
     *
     * @param view view
     */
    public void onAttach(final V view) {
        try {
            viewStub = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    try {
                        if (isAttached()) {
                            Object result = method.invoke(view, args);
                            return result;
                        }
                    } catch (Exception e) {
                        handleThrowable(e);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            viewStub = view;
        }
        isAttached = true;
    }

    /**
     * handleThrowable
     *
     * @param throwable throwable
     */
    protected void handleThrowable(Throwable throwable) {
    }

    /**
     * dettach
     */
    public void onDettach() {
        isAttached = false;
    }

    /**
     * callback
     *
     * @param run run
     */
    protected void callback(Runnable run) {
        if (isAttached() && run != null) {
            run.run();
        }
    }
}

