package com.liangmayong.base.binding.mvp;

import com.liangmayong.base.binding.mvp.annotations.BindPresenter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/17.
 */
public class PresenterBinding {

    private static final Map<Object, PresenterBinding> BINDS = new HashMap<>();

    public static PresenterBinding binding(Object object) {
        if (BINDS.containsKey(object)) {
            return BINDS.get(object);
        }
        PresenterBinding binding = new PresenterBinding(object);
        BINDS.put(object, binding);
        return binding;
    }

    private Map<String, Presenter> persenters = new HashMap<String, Presenter>();
    private Object target = null;

    private PresenterBinding(Object object) {
        this.target = object;
        this.bindAll();
    }

    private void bindAll() {
        Class<?> clazz = target.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            BindPresenter presenter = clazz.getAnnotation(BindPresenter.class);
            if (presenter != null) {
                Class<? extends Presenter>[] presenterTypes = presenter.value();
                for (Class<? extends Presenter> presenterType : presenterTypes) {
                    getPresenter(presenterType);
                }
            }
        }
    }

    public void unbinding() {
        if (!persenters.isEmpty()) {
            for (Map.Entry<String, Presenter> entry : persenters.entrySet()) {
                entry.getValue().onDettach();
            }
            persenters.clear();
        }
    }

    public <T extends Presenter> T getPresenter(Class<T> tClass) {
        if (persenters.containsKey(tClass.getName())) {
            return (T) persenters.get(tClass.getName());
        } else {
            T presenter = null;
            try {
                if (verifyPresenter(tClass, target.getClass())) {
                    presenter = tClass.getConstructor().newInstance();
                    persenters.put(tClass.getName(), presenter);
                    presenter.onAttach(target);
                }
            } catch (Exception e) {
            }
            return presenter;
        }
    }


    /////////////////////////////////////////////////////////

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
        if (viewPresenterClass.isAssignableFrom(viewClass)) {
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
}
