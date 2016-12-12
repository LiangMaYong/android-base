package com.liangmayong.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.liangmayong.base.web.WebActivity;
import com.liangmayong.base.support.base.IBase;

/**
 * Created by LiangMaYong on 2016/9/18.
 */
public final class BaseUtils {

    private BaseUtils() {
    }

    /**
     * goTo
     *
     * @param context context
     * @param cls     cls
     */
    public static void goTo(Context context, Class<? extends Activity> cls) {
        goTo(context, cls, null);
    }

    /**
     * goTo
     *
     * @param context context
     * @param cls     cls
     * @param extras  extras
     */
    public static void goTo(Context context, Class<? extends Activity> cls, Bundle extras) {
        Intent intent = new Intent(context, cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    /**
     * goTo
     *
     * @param context context
     * @param title   title
     * @param url     url
     */
    public static void goTo(Context context, String title, String url) {
        Bundle extras = new Bundle();
        extras.putString(IBase.WEB_EXTRA_TITLE, title);
        extras.putString(IBase.WEB_EXTRA_URL, url);
        extras.putString(IBase.WEB_EXTRA_MORE, "false");
        goTo(context, WebActivity.class, extras);
    }

    /**
     * goToWeb
     *
     * @param context context
     * @param title   title
     * @param url     url
     */
    public static void goToWeb(Context context, String title, String url) {
        Bundle extras = new Bundle();
        extras.putString(IBase.WEB_EXTRA_TITLE, title);
        extras.putString(IBase.WEB_EXTRA_URL, url);
        extras.putString(IBase.WEB_EXTRA_MORE, "true");
        goTo(context, WebActivity.class, extras);
    }

    /**
     * goHome
     *
     * @param context context
     */
    public static void goHome(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * goTo
     *
     * @param context   context
     * @param className className
     */
    public static void goTo(Context context, String className) {
        goTo(context, className, new Bundle());
    }

    /**
     * goTo
     *
     * @param context   context
     * @param className className
     * @param extras    extras
     */
    public static void goTo(Context context, String className, Bundle extras) {
        try {
            Intent intent = new Intent(context, Class.forName(className));
            if (extras != null) {
                intent.putExtras(extras);
            }
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
        }
    }

    /**
     * goToForResult
     *
     * @param activity    activity
     * @param cls         cls
     * @param requestCode requestCode
     */
    public static void goToForResult(Activity activity, Class<? extends Activity> cls, int requestCode) {
        goToForResult(activity, cls, null, requestCode);
    }

    /**
     * goToForResult
     *
     * @param activity    activity
     * @param cls         cls
     * @param extras      extras
     * @param requestCode requestCode
     */
    public static void goToForResult(Activity activity, Class<? extends Activity> cls, Bundle extras, int requestCode) {
        Intent intent = new Intent(activity, cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        activity.startActivityForResult(intent, requestCode);
    }
}
