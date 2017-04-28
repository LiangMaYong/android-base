package com.liangmayong.base.airbus.dispatch;

import com.liangmayong.base.airbus.annotations.OnAir;
import com.liangmayong.base.airbus.annotations.UnAir;
import com.liangmayong.base.airbus.listener.AirBusListener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/4/28.
 */
public final class AirBusDispatchListener implements AirBusListener {

    public static final String[] AIR_PROXY_PREFIX = {"onAir", "onEvent"};

    private static final Map<Class<?>, Map<String, Method>> methods = new HashMap<>();

    private final Object object;

    public AirBusDispatchListener(Object object) {
        this.object = object;
        if (methods.containsKey(object.getClass())) {
            return;
        } else {
            Map<String, Method> methodMap = this.parserOnAir(object);
            methods.put(object.getClass(), methodMap);
        }
    }

    private Map<String, Method> parserOnAir(Object object) {
        Map<String, Method> methodMap = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Method[] methods = clazz.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                UnAir unAir = method.getAnnotation(UnAir.class);
                if (unAir == null) {
                    OnAir onAir = method.getAnnotation(OnAir.class);
                    boolean proxy = onAir != null;
                    if (!proxy) {
                        for (int j = 0; j < AIR_PROXY_PREFIX.length; j++) {
                            if (method.getName().startsWith(AIR_PROXY_PREFIX[j])) {
                                proxy = true;
                                break;
                            }
                        }
                    }
                    if (proxy) {
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        if (parameterTypes.length == 1) {
                            methodMap.put(parameterTypes[0].getName(), method);
                        }
                    }
                }
            }
        }
        return methodMap;
    }

    private Method getProxyMethod(Class<?> clazz) {
        if (methods.containsKey(object.getClass())) {
            return methods.get(object.getClass()).get(clazz.getName());
        }
        return null;
    }

    @Override
    public void onAirBus(Object event) {
        if (event != null) {
            Class<?> clazz = event.getClass();
            Method method = getProxyMethod(clazz);
            if (method != null) {
                method.setAccessible(true);
                try {
                    method.invoke(object, event);
                } catch (Exception e) {
                }
            }
        }
    }
}
