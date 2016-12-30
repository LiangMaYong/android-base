package com.liangmayong.base.binding.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangmayong.base.binding.view.annotations.BindColor;
import com.liangmayong.base.binding.view.annotations.BindLayout;
import com.liangmayong.base.binding.view.annotations.BindOnClick;
import com.liangmayong.base.binding.view.annotations.BindOnLongClick;
import com.liangmayong.base.binding.view.annotations.BindString;
import com.liangmayong.base.binding.view.annotations.BindTitle;
import com.liangmayong.base.binding.view.annotations.BindView;
import com.liangmayong.base.binding.view.interfaces.ITitle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * ViewBinding
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class ViewBinding {

    /**
     * parserActivity
     *
     * @param act Activity
     * @return view
     */
    public static View parserActivity(Activity act) {
        if (null == act)
            return null;
        Class<?> cl = act.getClass();
        View root = null;
        if (isLayout(cl)) {
            BindLayout layout = cl.getAnnotation(BindLayout.class);
            root = LayoutInflater.from(act).inflate(layout.value(), null);
            act.setContentView(root);
        }
        if (isTitle(act, cl)) {
            BindTitle title = cl.getAnnotation(BindTitle.class);
            if (title.id() != 0) {
                ((ITitle) act).onBindTitle(act.getString(title.id()));
            } else {
                ((ITitle) act).onBindTitle(title.value());
            }
        }
        View decorView = act.getWindow().getDecorView();
        initFields(cl.getDeclaredFields(), decorView, act);
        initMethods(cl.getDeclaredMethods(), decorView, act);
        return root;
    }

    /**
     * parserClassByView
     *
     * @param obj  obj
     * @param root root View
     */
    public static void parserClassByView(Object obj, View root) {
        if (null == obj || null == root)
            return;
        Class<?> cl = obj.getClass();
        if (isTitle(obj, cl)) {
            BindTitle title = cl.getAnnotation(BindTitle.class);
            if (title.id() != 0) {
                ((ITitle) obj).onBindTitle(root.getContext().getString(title.id()));
            } else {
                ((ITitle) obj).onBindTitle(title.value());
            }
        }
        initFields(cl.getDeclaredFields(), root, obj);
        initMethods(cl.getDeclaredMethods(), root, obj);
    }

    /**
     * parserLayout
     *
     * @param obj     obj
     * @param context context
     * @return view
     */
    public static View parserLayout(Object obj, Context context) {
        if (null == obj || null == context)
            return null;
        Class<?> cl = obj.getClass();
        View root = null;
        if (isLayout(cl)) {
            BindLayout layout = cl.getAnnotation(BindLayout.class);
            root = LayoutInflater.from(context).inflate(layout.value(), null);
        }
        return root;
    }

    /**
     * parserClass
     *
     * @param obj     obj
     * @param context context
     * @return view
     */
    public static View parserClass(Object obj, Context context) {
        if (null == obj || null == context)
            return null;
        Class<?> cl = obj.getClass();
        View root = null;
        if (isLayout(cl)) {
            BindLayout layout = cl.getAnnotation(BindLayout.class);
            root = LayoutInflater.from(context).inflate(layout.value(), null);
        }
        if (isTitle(obj, cl)) {
            BindTitle title = cl.getAnnotation(BindTitle.class);
            if (title.id() != 0) {
                ((ITitle) obj).onBindTitle(root.getContext().getString(title.id()));
            } else {
                ((ITitle) obj).onBindTitle(title.value());
            }
        }
        initFields(cl.getDeclaredFields(), root, obj);
        initMethods(cl.getDeclaredMethods(), root, obj);
        return root;
    }

    /**
     * parserFragment
     *
     * @param fragment fragment
     * @param group    group
     * @return View
     */
    public static View parserFragment(Fragment fragment, ViewGroup group) {
        Class<?> cl = fragment.getClass();
        View view = null;
        if (isLayout(cl)) {
            BindLayout layout = cl.getAnnotation(BindLayout.class);
            view = fragment.getActivity().getLayoutInflater().inflate(layout.value(), group, false);
        }
        if (isTitle(fragment, cl)) {
            BindTitle title = cl.getAnnotation(BindTitle.class);
            if (title.id() != 0) {
                ((ITitle) fragment).onBindTitle(fragment.getActivity().getString(title.id()));
            } else {
                ((ITitle) fragment).onBindTitle(title.value());
            }
        }

        if (null != view) {
            initFields(cl.getDeclaredFields(), view, fragment);
            initMethods(cl.getDeclaredMethods(), view, fragment);
        }
        return view;
    }

    /**
     * parserView
     *
     * @param view view
     */
    public static void parserView(View view) {
        Class<?> cl = view.getClass();
        if (isTitle(view, cl)) {
            BindTitle title = cl.getAnnotation(BindTitle.class);
            if (title.id() != 0) {
                ((ITitle) view).onBindTitle(view.getContext().getString(title.id()));
            } else {
                ((ITitle) view).onBindTitle(title.value());
            }
        }
        initFields(cl.getDeclaredFields(), view, view);
        initMethods(cl.getDeclaredMethods(), view, view);
    }

    /**
     * initFields
     *
     * @param allField allField
     * @param root     root
     * @param object   object
     */
    private static void initFields(Field[] allField, View root, Object object) {
        for (Field field : allField) {
            // View
            if (isView(field)) {
                BindView xkView = field.getAnnotation(BindView.class);
                View v = root.findViewById(xkView.value());
                if (null != v) {
                    try {
                        field.setAccessible(true);
                        field.set(object, v);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            // String
            if (isString(field)) {
                BindString xkString = field.getAnnotation(BindString.class);
                String s = root.getContext().getString(xkString.value());
                if (null != s) {
                    try {
                        field.setAccessible(true);
                        field.set(object, s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // Color
            if (isColor(field)) {
                BindColor xkColor = field.getAnnotation(BindColor.class);
                try {
                    @SuppressWarnings("deprecation")
                    int color = root.getContext().getResources().getColor(xkColor.value());
                    field.setAccessible(true);
                    field.set(object, color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * initMethods
     *
     * @param allMethod allMethod
     * @param root      root
     * @param object    object
     */
    private static void initMethods(Method[] allMethod, View root, Object object) {
        for (Method method : allMethod) {
            if (isOnClick(method)) {
                BindOnClick onClick = method.getAnnotation(BindOnClick.class);
                ProxyClick click = new ProxyClick(method, object);
                int[] ids = onClick.value();
                for (int id : ids) {
                    View view = root.findViewById(id);
                    if (view != null) {
                        view.setOnClickListener(click);
                    }
                }
            }
            if (isOnLongClick(method)) {
                BindOnLongClick onLongClick = method.getAnnotation(BindOnLongClick.class);
                ProxyLongClick longClick = new ProxyLongClick(method, object);
                int[] ids = onLongClick.value();
                for (int id : ids) {
                    View view = root.findViewById(id);
                    if (view != null) {
                        view.setOnLongClickListener(longClick);
                    }
                }
            }
        }
    }

    /**
     * isLayout
     *
     * @param cls cls
     * @return true or false
     */
    private static boolean isLayout(Class<?> cls) {
        return cls.isAnnotationPresent(BindLayout.class);
    }

    /**
     * isTitle
     *
     * @param cls cls
     * @return true or false
     */
    private static boolean isTitle(Object object, Class<?> cls) {
        return cls.isAnnotationPresent(BindTitle.class) && object instanceof ITitle;
    }

    /**
     * isView
     *
     * @param field field
     * @return true or false
     */
    private static boolean isView(Field field) {
        return field.isAnnotationPresent(BindView.class);
    }

    /**
     * isString
     *
     * @param field field
     * @return true or false
     */
    private static boolean isString(Field field) {
        return field.isAnnotationPresent(BindString.class);
    }

    /**
     * isColor
     *
     * @param field field
     * @return true or false
     */
    private static boolean isColor(Field field) {
        return field.isAnnotationPresent(BindColor.class);
    }

    /**
     * isOnClick
     *
     * @param method method
     * @return true or false
     */
    private static boolean isOnClick(Method method) {
        return method.isAnnotationPresent(BindOnClick.class);
    }

    /**
     * isOnLongClick
     *
     * @param method method
     * @return true or false
     */
    private static boolean isOnLongClick(Method method) {
        return method.isAnnotationPresent(BindOnLongClick.class);
    }

    /**
     * ProxyClick
     */
    private static class ProxyClick implements View.OnClickListener {

        private Method mMethod;
        private Object mReceiver;

        public ProxyClick(Method method, Object receiver) {
            mMethod = method;
            mReceiver = receiver;
        }

        @Override
        public void onClick(View v) {
            try {
                mMethod.setAccessible(true);
                if (mMethod.getParameterTypes().length == 0) {
                    mMethod.invoke(mReceiver);
                } else if (mMethod.getParameterTypes().length == 1 && mMethod.getParameterTypes()[0] == View.class) {
                    mMethod.invoke(mReceiver, v);
                } else {
                    Object[] objects = new Object[mMethod.getParameterTypes().length];
                    boolean setView = false;
                    for (int i = 0; i < mMethod.getParameterTypes().length; i++) {
                        if (!setView && mMethod.getParameterTypes()[i] == View.class) {
                            setView = true;
                            objects[i] = v;
                        } else {
                            if (mMethod.getParameterTypes()[i] == int.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == Integer.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == float.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == Float.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == long.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == Long.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == double.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == Double.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == Boolean.class) {
                                objects[i] = false;
                            } else if (mMethod.getParameterTypes()[i] == boolean.class) {
                                objects[i] = false;
                            } else if (mMethod.getParameterTypes()[i] == char.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == char.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == Character.class) {
                                objects[i] = 0;
                            } else {
                                objects[i] = null;
                            }
                        }
                    }
                    mMethod.invoke(mReceiver, objects);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ProxyLongClick
     */
    private static class ProxyLongClick implements View.OnLongClickListener {

        private Method mMethod;
        private Object mReceiver;

        public ProxyLongClick(Method method, Object receiver) {
            mMethod = method;
            mReceiver = receiver;
        }

        @Override
        public boolean onLongClick(View v) {
            try {
                mMethod.setAccessible(true);
                Object object = null;
                if (mMethod.getParameterTypes().length == 0) {
                    object = mMethod.invoke(mReceiver);
                } else if (mMethod.getParameterTypes().length == 1 && mMethod.getParameterTypes()[0] == View.class) {
                    object = mMethod.invoke(mReceiver, v);
                } else {
                    Object[] objects = new Object[mMethod.getParameterTypes().length];
                    boolean setView = false;
                    for (int i = 0; i < mMethod.getParameterTypes().length; i++) {
                        if (!setView && mMethod.getParameterTypes()[i] == View.class) {
                            setView = true;
                            objects[i] = v;
                        } else {
                            if (mMethod.getParameterTypes()[i] == int.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == Integer.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == float.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == Float.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == long.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == Long.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == double.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == Double.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == Boolean.class) {
                                objects[i] = false;
                            } else if (mMethod.getParameterTypes()[i] == boolean.class) {
                                objects[i] = false;
                            } else if (mMethod.getParameterTypes()[i] == char.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == char.class) {
                                objects[i] = 0;
                            } else if (mMethod.getParameterTypes()[i] == Character.class) {
                                objects[i] = 0;
                            } else {
                                objects[i] = null;
                            }
                        }
                    }
                    object = mMethod.invoke(mReceiver, objects);
                }
                if (mMethod.getReturnType() == Boolean.class || mMethod.getReturnType() == boolean.class) {
                    return (Boolean) object;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
