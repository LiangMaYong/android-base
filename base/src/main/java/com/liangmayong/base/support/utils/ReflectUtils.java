package com.liangmayong.base.support.utils;

import android.annotation.SuppressLint;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ReflectUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
@SuppressLint("DefaultLocale")
public final class ReflectUtils {

    /**
     * @param name name
     * @return Reflect
     * @throws ReflectException e
     * @see #on(Class)
     */
    public static ReflectUtils on(String name) throws ReflectException {
        return on(forName(name));
    }

    /**
     * on
     *
     * @param name        name
     * @param classLoader classLoader
     * @return ReflectUtils
     * @throws ReflectException e
     * @see #on(Class)
     */
    public static ReflectUtils on(String name, ClassLoader classLoader) throws ReflectException {
        return on(forName(name, classLoader));
    }

    public static ReflectUtils on(Class<?> clazz) {
        return new ReflectUtils(clazz);
    }

    /**
     * Wrap an object.
     * Use this when you want to access instance fields and methods on any
     * {@link Object}
     *
     * @param object The object to be wrapped
     * @return A wrapped object, to be used for further reflection.
     */
    public static ReflectUtils on(Object object) {
        return new ReflectUtils(object);
    }

    /**
     * @param accessible accessible
     * @param <T>        t
     * @return t
     */
    public static <T extends AccessibleObject> T accessible(T accessible) {
        if (accessible == null) {
            return null;
        }

        if (accessible instanceof Member) {
            Member member = (Member) accessible;

            if (Modifier.isPublic(member.getModifiers())
                    && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {

                return accessible;
            }
        }
        if (!accessible.isAccessible()) {
            accessible.setAccessible(true);
        }

        return accessible;
    }

    private final Object object;

    private final boolean isClass;

    private ReflectUtils(Class<?> type) {
        this.object = type;
        this.isClass = true;
    }

    private ReflectUtils(Object object) {
        this.object = object;
        this.isClass = false;
    }

    // ---------------------------------------------------------------------
    // Fluent Reflection API
    // ---------------------------------------------------------------------

    /**
     * @param <T> t
     * @return t
     */
    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) object;
    }

    /**
     * @param name  name
     * @param value value
     * @return ReflectUtils
     * @throws ReflectException e
     */
    public ReflectUtils set(String name, Object value) throws ReflectException {
        try {
            Field field = field0(name);
            field.setAccessible(true);
            field.set(object, unwrap(value));
            return this;
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * @param name name
     * @param <T>  t
     * @return t
     * @throws ReflectException e
     */
    public <T> T get(String name) throws ReflectException {
        return field(name).<T>get();
    }

    /**
     * @param name name
     * @return t
     * @throws ReflectException e
     */
    public ReflectUtils field(String name) throws ReflectException {
        try {
            Field field = field0(name);
            return on(field.get(object));
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private Field field0(String name) throws ReflectException {
        Class<?> type = type();

        try {
            return type.getField(name);
        } catch (NoSuchFieldException e) {
            do {
                try {
                    return accessible(type.getDeclaredField(name));
                } catch (NoSuchFieldException ignore) {
                }

                type = type.getSuperclass();
            } while (type != null);

            throw new ReflectException(e);
        }
    }

    /**
     * @return fields
     */
    public Map<String, ReflectUtils> fields() {
        Map<String, ReflectUtils> result = new LinkedHashMap<String, ReflectUtils>();
        Class<?> type = type();

        do {
            for (Field field : type.getDeclaredFields()) {
                if (!isClass ^ Modifier.isStatic(field.getModifiers())) {
                    String name = field.getName();

                    if (!result.containsKey(name))
                        result.put(name, field(name));
                }
            }

            type = type.getSuperclass();
        } while (type != null);

        return result;
    }

    /**
     * @param name name
     * @return ReflectUtils
     * @throws ReflectException e
     */
    public ReflectUtils call(String name) throws ReflectException {
        return call(name, new Object[0]);
    }

    /**
     * @param name name
     * @param args args
     * @return ReflectUtils
     * @throws ReflectException e
     */
    public ReflectUtils call(String name, Object... args) throws ReflectException {
        Class<?>[] types = types(args);

        try {
            Method method = exactMethod(name, types);
            return on(method, object, args);
        } catch (NoSuchMethodException e) {
            try {
                Method method = similarMethod(name, types);
                return on(method, object, args);
            } catch (NoSuchMethodException e1) {
                throw new ReflectException(e1);
            }
        }
    }

    private Method exactMethod(String name, Class<?>[] types) throws NoSuchMethodException {
        Class<?> type = type();

        try {
            return type.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            do {
                try {
                    return type.getDeclaredMethod(name, types);
                } catch (NoSuchMethodException ignore) {
                }

                type = type.getSuperclass();
            } while (type != null);

            throw new NoSuchMethodException();
        }
    }

    /**
     */
    private Method similarMethod(String name, Class<?>[] types) throws NoSuchMethodException {
        Class<?> type = type();

        for (Method method : type.getMethods()) {
            if (isSimilarSignature(method, name, types)) {
                return method;
            }
        }

        do {
            for (Method method : type.getDeclaredMethods()) {
                if (isSimilarSignature(method, name, types)) {
                    return method;
                }
            }

            type = type.getSuperclass();
        } while (type != null);

        throw new NoSuchMethodException("No similar method " + name + " with params " + Arrays.toString(types)
                + " could be found on type " + type() + ".");
    }

    private boolean isSimilarSignature(Method possiblyMatchingMethod, String desiredMethodName,
                                       Class<?>[] desiredParamTypes) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName)
                && match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
    }

    /**
     * @return ReflectUtils
     * @throws ReflectException e
     */
    public ReflectUtils create() throws ReflectException {
        return create(new Object[0]);
    }

    /**
     * @param args args
     * @return ReflectUtils
     * @throws ReflectException e
     */
    public ReflectUtils create(Object... args) throws ReflectException {
        Class<?>[] types = types(args);

        try {
            Constructor<?> constructor = type().getDeclaredConstructor(types);
            return on(constructor, args);
        } catch (NoSuchMethodException e) {
            for (Constructor<?> constructor : type().getDeclaredConstructors()) {
                if (match(constructor.getParameterTypes(), types)) {
                    return on(constructor, args);
                }
            }

            throw new ReflectException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <P> P as(Class<P> proxyType) {
        final boolean isMap = (object instanceof Map);
        final InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String name = method.getName();
                try {
                    return on(object).call(name, args).get();
                } catch (ReflectException e) {
                    if (isMap) {
                        Map<String, Object> map = (Map<String, Object>) object;
                        int length = (args == null ? 0 : args.length);

                        if (length == 0 && name.startsWith("get")) {
                            return map.get(property(name.substring(3)));
                        } else if (length == 0 && name.startsWith("is")) {
                            return map.get(property(name.substring(2)));
                        } else if (length == 1 && name.startsWith("set")) {
                            map.put(property(name.substring(3)), args[0]);
                            return null;
                        }
                    }

                    throw e;
                }
            }
        };

        return (P) Proxy.newProxyInstance(proxyType.getClassLoader(), new Class[]{proxyType}, handler);
    }

    @SuppressLint("DefaultLocale")
    private static String property(String string) {
        int length = string.length();

        if (length == 0) {
            return "";
        } else if (length == 1) {
            return string.toLowerCase();
        } else {
            return string.substring(0, 1).toLowerCase() + string.substring(1);
        }
    }

    private boolean match(Class<?>[] declaredTypes, Class<?>[] actualTypes) {
        if (declaredTypes.length == actualTypes.length) {
            for (int i = 0; i < actualTypes.length; i++) {
                if (actualTypes[i] == NULL.class)
                    continue;

                if (wrapper(declaredTypes[i]).isAssignableFrom(wrapper(actualTypes[i])))
                    continue;

                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return object.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReflectUtils) {
            return object.equals(((ReflectUtils) obj).get());
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return object.toString();
    }

    private static ReflectUtils on(Constructor<?> constructor, Object... args) throws ReflectException {
        try {
            return on(accessible(constructor).newInstance(args));
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private static ReflectUtils on(Method method, Object object, Object... args) throws ReflectException {
        try {
            accessible(method);

            if (method.getReturnType() == void.class) {
                method.invoke(object, args);
                return on(object);
            } else {
                return on(method.invoke(object, args));
            }
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private static Object unwrap(Object object) {
        if (object instanceof ReflectUtils) {
            return ((ReflectUtils) object).get();
        }

        return object;
    }

    private static Class<?>[] types(Object... values) {
        if (values == null) {
            return new Class[0];
        }

        Class<?>[] result = new Class[values.length];

        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            result[i] = value == null ? NULL.class : value.getClass();
        }

        return result;
    }

    private static Class<?> forName(String name) throws ReflectException {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private static Class<?> forName(String name, ClassLoader classLoader) throws ReflectException {
        try {
            return Class.forName(name, true, classLoader);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    public Class<?> type() {
        if (isClass) {
            return (Class<?>) object;
        } else {
            return object.getClass();
        }
    }

    public static Class<?> wrapper(Class<?> type) {
        if (type == null) {
            return null;
        } else if (type.isPrimitive()) {
            if (boolean.class == type) {
                return Boolean.class;
            } else if (int.class == type) {
                return Integer.class;
            } else if (long.class == type) {
                return Long.class;
            } else if (short.class == type) {
                return Short.class;
            } else if (byte.class == type) {
                return Byte.class;
            } else if (double.class == type) {
                return Double.class;
            } else if (float.class == type) {
                return Float.class;
            } else if (char.class == type) {
                return Character.class;
            } else if (void.class == type) {
                return Void.class;
            }
        }

        return type;
    }

    public static class ReflectException extends RuntimeException {

        private static final long serialVersionUID = 663038727503637969L;

        public ReflectException(String message) {
            super(message);
        }

        public ReflectException(String message, Throwable cause) {
            super(message, cause);
        }

        public ReflectException() {
            super();
        }

        public ReflectException(Throwable cause) {
            super(cause);
        }
    }

    public class NULL {
    }
}
