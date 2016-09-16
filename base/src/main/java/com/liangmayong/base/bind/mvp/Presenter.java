package com.liangmayong.base.bind.mvp;

import android.os.Handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * BindPresenter
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
        view = null;
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
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Bind
     */
    public static final class Bind {

        /**
         * bindPresenter
         *
         * @param object object
         * @return presenter holder
         */
        @SuppressWarnings("rawtypes")
        public static Presenter.PresenterHolder bindPresenter(Object object) {
            Presenter.PresenterHolder handler = new Presenter.PresenterHolder();
            if (object == null)
                return handler;
            Class<?> clazz = object.getClass();
            for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                BindPresenter presenter = clazz.getAnnotation(BindPresenter.class);
                if (presenter != null) {
                    Class<? extends Presenter>[] presenterTypes = presenter.value();
                    for (Class<? extends Presenter> presenterType : presenterTypes) {
                        if (verifyPresenter(presenterType, object.getClass())) {
                            try {
                                Presenter pre = presenterType.getConstructor().newInstance();
                                handler.putPresenter(pre);
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
            handler.onAttach(object);
            return handler;
        }

        /**
         * addPresenter
         *
         * @param handler    holder
         * @param presenters presenters
         */
        @SuppressWarnings("rawtypes")
        public static void addPresenter(Presenter.PresenterHolder handler, Class<? extends Presenter>... presenters) {
            if (handler == null || handler.getTarget() == null || presenters == null)
                return;
            for (Class<? extends Presenter> presenterType : presenters) {
                if (verifyPresenter(presenterType, handler.getTarget().getClass())) {
                    if (!handler.hasPresenter(presenterType)) {
                        try {
                            Presenter pre = presenterType.getConstructor().newInstance();
                            handler.putPresenter(pre);
                            pre.onAttach(handler.getPersenters());
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }

        /**
         * verifyPresenter
         *
         * @param presenter presenter
         * @param viewClass viewClass
         * @return false or true
         */
        @SuppressWarnings("rawtypes")
        private static boolean verifyPresenter(Class<? extends Presenter> presenter, Class<?> viewClass) {
            Class<?> viewPresenterClass = getViewClass(presenter);
            if (isClassGeneric(viewClass, viewPresenterClass.getName()))
                return true;
            return false;
        }

        /**
         * getViewClass
         *
         * @param presenter presenter
         * @return view class
         */
        @SuppressWarnings("rawtypes")
        private static Class<?> getViewClass(Class<? extends Presenter> presenter) {
            if (presenter != null) {
                Class<?> entityClass = null;
                Type t = presenter.getGenericSuperclass();
                if (t instanceof ParameterizedType) {
                    Type[] p = ((ParameterizedType) t).getActualTypeArguments();
                    entityClass = (Class<?>) p[0];
                }
                return entityClass;
            }
            return null;
        }

        /**
         * isGeneric
         *
         * @param clazz clazz
         * @param name  name
         * @return true or false
         */
        private static boolean isClassGeneric(Class<?> clazz, String name) {
            for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                if (name.equals(clazz.getName())) {
                    return true;
                }
                Class<?>[] classes = clazz.getInterfaces();
                for (int i = 0; i < classes.length; i++) {
                    if (name.equals(classes[i].getName())) {
                        return true;
                    }
                }
            }
            return false;
        }

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
