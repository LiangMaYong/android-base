package com.liangmayong.base.bind;

import com.liangmayong.base.bind.annotations.BindPresenter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * BindMVP
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class BindMVP {

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
