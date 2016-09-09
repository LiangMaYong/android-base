package com.liangmayong.base.bind;

import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Presenter
 *
 * @param <V> viewType
 * @author LiangMaYong
 * @version 1.0
 */
public abstract class Presenter<V> {

    //view
    private V view;
    //isAttached
    private boolean isAttached = false;
    //handler
    private Handler handler = new Handler();

    /**
     * getViewInstance
     *
     * @return view
     */
    protected final V getViewInstance() {
        return view;
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
    protected void onAttach(V view) {
        this.view = view;
        isAttached = true;
    }

    /**
     * dettach
     */
    protected void onDettach() {
        isAttached = false;
    }

    /**
     * postDelayed
     *
     * @param runnable    runnable
     * @param delayMillis delayMillis
     */
    public void postDelayed(Runnable runnable, long delayMillis) {
        handler.postDelayed(runnable, delayMillis);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////// Holder //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * PresenterHolder
     *
     * @author LiangMaYong
     * @version 1.0
     */
    public final static class PresenterHolder {
        // presenterMap
        @SuppressWarnings("rawtypes")
        private Map<String, Presenter> persenterMap = new HashMap<String, Presenter>();
        // is attached
        private boolean isAttached = false;
        //target
        private Object target = null;

        /**
         * getTarget
         *
         * @return target
         */
        public Object getTarget() {
            return target;
        }

        /**
         * putPresenter
         *
         * @param presenter presenter
         */
        @SuppressWarnings("rawtypes")
        protected void putPresenter(Presenter presenter) {
            if (presenter == null) {
                return;
            }
            if (hasPresenter(presenter.getClass())) {
                return;
            }
            persenterMap.put(presenter.getClass().getName(), presenter);
        }

        /**
         * onAttach
         *
         * @param object object
         */
        @SuppressWarnings({"rawtypes", "unchecked"})
        public void onAttach(Object object) {
            if (isAttached) {
                return;
            }
            if (!isEmpty()) {
                for (Map.Entry<String, Presenter> entry : persenterMap.entrySet()) {
                    entry.getValue().onAttach(object);
                }
            }
            target = object;
            isAttached = true;
        }

        /**
         * isAttached
         *
         * @return is attached
         */
        public boolean isAttached() {
            return isAttached;
        }

        /**
         * onDettach
         */
        @SuppressWarnings("rawtypes")
        public void onDettach() {
            if (isAttached) {
                for (Map.Entry<String, Presenter> entry : persenterMap.entrySet()) {
                    entry.getValue().onDettach();
                }
                persenterMap.clear();
                isAttached = false;
            }
        }

        /**
         * isEmpty
         *
         * @return is empty
         */
        public boolean isEmpty() {
            return persenterMap.isEmpty();
        }

        /**
         * getPersenters
         *
         * @return persenterMap
         */
        @SuppressWarnings("rawtypes")
        public Map<String, Presenter> getPersenters() {
            return persenterMap;
        }

        @SuppressWarnings("rawtypes")
        public boolean hasPresenter(Class<? extends Presenter> cls) {
            if (persenterMap.containsKey(cls.getName())) {
                return true;
            }
            return false;
        }

        /**
         * getPresenter
         *
         * @param clazz clazz
         * @param <T>   t
         * @return presenter
         */
        @SuppressWarnings({"rawtypes", "unchecked"})
        public <T extends Presenter> T getPresenter(Class<T> clazz) {
            if (hasPresenter(clazz)) {
                return (T) persenterMap.get(clazz.getName());
            }
            return null;
        }
    }
}
