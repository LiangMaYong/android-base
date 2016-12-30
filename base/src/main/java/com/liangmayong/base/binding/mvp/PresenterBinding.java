package com.liangmayong.base.binding.mvp;

import com.liangmayong.base.binding.mvp.annotations.BindPresenter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by LiangMaYong on 2016/9/17.
 */
public class PresenterBinding {

    private PresenterBinding() {
    }

    /**
     * bindPresenter
     *
     * @param object object
     * @return presenter holder
     */
    @SuppressWarnings("rawtypes")
    public static PresenterHolder bind(Object object) {
        PresenterHolder handler = new PresenterHolder();
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
                            handler.addPresenter(pre);
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
     * bind
     *
     * @param handler    holder
     * @param presenters presenters
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void bind(PresenterHolder handler, Class<? extends Presenter>... presenters) {
        if (handler == null || handler.getTarget() == null || presenters == null)
            return;
        for (Class<? extends Presenter> presenterType : presenters) {
            if (verifyPresenter(presenterType, handler.getTarget().getClass())) {
                if (!handler.hasPresenter(presenterType)) {
                    try {
                        Presenter pre = presenterType.getConstructor().newInstance();
                        handler.addPresenter(pre);
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
        if (isClassGeneric(viewClass, viewPresenterClass)) {
            return true;
        }
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
     * @param clazz          clazz
     * @param clazzInterface clazzInterface
     * @return true or false
     */
    private static boolean isClassGeneric(Class<?> clazz, Class<?> clazzInterface) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            if (isInterfaceGeneric(clazz, clazzInterface)) {
                return true;
            }
        }
        return false;
    }

    /**
     * isInterfaceGeneric
     *
     * @param clazz          clazz
     * @param clazzInterface clazzInterface
     * @return flag
     */
    private static boolean isInterfaceGeneric(Class<?> clazz, Class<?> clazzInterface) {
        if (clazzInterface.equals(clazz)) {
            return true;
        }
        Class<?>[] classes = clazz.getInterfaces();
        for (int i = 0; i < classes.length; i++) {
            if (clazzInterface.equals(classes[i])) {
                return true;
            }
            Class<?> clz = classes[i];
            if (isInterfaceGeneric(clz, clazzInterface)) {
                return true;
            }
        }
        return false;
    }
}
