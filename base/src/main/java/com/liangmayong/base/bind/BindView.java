package com.liangmayong.base.bind;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangmayong.base.bind.annotations.ColorId;
import com.liangmayong.base.bind.annotations.Layout;
import com.liangmayong.base.bind.annotations.OnClick;
import com.liangmayong.base.bind.annotations.OnLongClick;
import com.liangmayong.base.bind.annotations.StringId;
import com.liangmayong.base.bind.annotations.Title;
import com.liangmayong.base.bind.annotations.TitleId;
import com.liangmayong.base.bind.annotations.ViewId;
import com.liangmayong.base.interfaces.AnotationTitle;
import com.liangmayong.base.utils.ResourceUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * BindView
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class BindView {

    /**
     * parserActivity
     *
     * @param act Activity
     */
    public static View parserActivity(Activity act) {
        if (null == act)
            return null;
        Class<?> cl = act.getClass();
        View root = null;
        if (isLayout(cl)) {
            Layout layout = cl.getAnnotation(Layout.class);
            root = LayoutInflater.from(act).inflate(layout.value(), null);
            act.setContentView(root);
        }
        if (isTitle(cl)) {
            Title title = cl.getAnnotation(Title.class);
            ((AnotationTitle) act).setAnotationTitle(title.value());
        } else if (isTitleId(cl)) {
            TitleId title = cl.getAnnotation(TitleId.class);
            ((AnotationTitle) act).setAnotationTitle(act.getString(title.value()));
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
        if (isTitle(cl)) {
            Title title = cl.getAnnotation(Title.class);
            ((AnotationTitle) obj).setAnotationTitle(title.value());
        } else if (isTitleId(cl)) {
            TitleId title = cl.getAnnotation(TitleId.class);
            ((AnotationTitle) obj).setAnotationTitle(root.getContext().getString(title.value()));
        }
        initFields(cl.getDeclaredFields(), root, obj);
        initMethods(cl.getDeclaredMethods(), root, obj);
    }

    /**
     * parserClass
     *
     * @param obj     obj
     * @param context context
     */
    public static View parserClass(Object obj, Context context) {
        if (null == obj || null == context)
            return null;
        Class<?> cl = obj.getClass();
        View root = null;
        if (isLayout(cl)) {
            Layout layout = cl.getAnnotation(Layout.class);
            root = LayoutInflater.from(context).inflate(layout.value(), null);
        }
        if (isTitle(cl)) {
            Title title = cl.getAnnotation(Title.class);
            ((AnotationTitle) obj).setAnotationTitle(title.value());
        } else if (isTitleId(cl)) {
            TitleId title = cl.getAnnotation(TitleId.class);
            ((AnotationTitle) obj).setAnotationTitle(context.getString(title.value()));
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
            Layout layout = cl.getAnnotation(Layout.class);
            view = fragment.getActivity().getLayoutInflater().inflate(layout.value(), group, false);
        }
        if (isTitle(cl)) {
            Title title = cl.getAnnotation(Title.class);
            ((AnotationTitle) fragment).setAnotationTitle(title.value());
        } else if (isTitleId(cl)) {
            TitleId title = cl.getAnnotation(TitleId.class);
            ((AnotationTitle) fragment).setAnotationTitle(fragment.getActivity().getString(title.value()));
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
    @TargetApi(Build.VERSION_CODES.M)
    private static void initFields(Field[] allField, View root, Object object) {
        for (Field field : allField) {
            // View
            if (isView(field)) {
                ViewId xkView = field.getAnnotation(ViewId.class);
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
                StringId xkString = field.getAnnotation(StringId.class);
                String s;
                if (xkString.value() == -1) {
                    s = ResourceUtils.getString(root.getContext(), field.getName());
                } else {
                    s = root.getContext().getString(xkString.value());
                }
                if (null != s) {
                    try {
                        field.setAccessible(true);
                        field.set(object, s);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Color
            if (isColor(field)) {
                ColorId xkColor = field.getAnnotation(ColorId.class);
                try {
                    int color;
                    if (xkColor.value() == -1) {
                        color = ResourceUtils.getColor(root.getContext(), field.getName());
                    } else {
                        color = root.getContext().getColor(xkColor.value());
                    }
                    field.setAccessible(true);
                    field.set(object, color);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
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
                OnClick onClick = method.getAnnotation(OnClick.class);
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
                OnLongClick onLongClick = method.getAnnotation(OnLongClick.class);
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
        return cls.isAnnotationPresent(Layout.class);
    }

    /**
     * isTitle
     *
     * @param cls cls
     * @return true or false
     */
    private static boolean isTitle(Class<?> cls) {
        return cls.isAnnotationPresent(Title.class) && isClassGeneric(cls, AnotationTitle.class.getName());
    }

    /**
     * isTitleId
     *
     * @param cls cls
     * @return true or false
     */
    private static boolean isTitleId(Class<?> cls) {
        return cls.isAnnotationPresent(TitleId.class) && isClassGeneric(cls, AnotationTitle.class.getName());
    }

    /**
     * isView
     *
     * @param field field
     * @return true or false
     */
    private static boolean isView(Field field) {
        return field.isAnnotationPresent(ViewId.class);
    }

    /**
     * isString
     *
     * @param field field
     * @return true or false
     */
    private static boolean isString(Field field) {
        return field.isAnnotationPresent(StringId.class);
    }

    /**
     * isColor
     *
     * @param field field
     * @return true or false
     */
    private static boolean isColor(Field field) {
        return field.isAnnotationPresent(ColorId.class);
    }

    /**
     * isOnClick
     *
     * @param method method
     * @return true or false
     */
    private static boolean isOnClick(Method method) {
        return method.isAnnotationPresent(OnClick.class);
    }

    /**
     * isOnLongClick
     *
     * @param method method
     * @return true or false
     */
    private static boolean isOnLongClick(Method method) {
        return method.isAnnotationPresent(OnLongClick.class);
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
                } else if (mMethod.getParameterTypes().length == 1 || mMethod.getParameterTypes()[0] == View.class) {
                    mMethod.invoke(mReceiver, v);
                } else {
                    Object[] objects = new Object[mMethod.getParameterTypes().length];
                    boolean setView = false;
                    for (int i = 0; i < mMethod.getParameterTypes().length; i++) {
                        if (!setView && mMethod.getParameterTypes()[i] == View.class) {
                            setView = true;
                            objects[i] = v;
                        } else {
                            objects[i] = null;
                        }
                    }
                    mMethod.invoke(mReceiver, null);
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
                } else if (mMethod.getParameterTypes().length == 1 || mMethod.getParameterTypes()[0] == View.class) {
                    object = mMethod.invoke(mReceiver, v);
                } else {
                    Object[] objects = new Object[mMethod.getParameterTypes().length];
                    boolean setView = false;
                    for (int i = 0; i < mMethod.getParameterTypes().length; i++) {
                        if (!setView && mMethod.getParameterTypes()[i] == View.class) {
                            setView = true;
                            objects[i] = v;
                        } else {
                            objects[i] = null;
                        }
                    }
                    object = mMethod.invoke(mReceiver, null);
                }
                if (mMethod.getReturnType() == Boolean.class || mMethod.getReturnType() == boolean.class) {
                    return (boolean) object;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

    }

    /**
     * isClassGeneric
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
