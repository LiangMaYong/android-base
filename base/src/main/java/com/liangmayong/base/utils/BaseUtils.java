package com.liangmayong.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.liangmayong.base.activitys.WebActivity;
import com.liangmayong.base.interfaces.BaseInterface;

import java.util.HashMap;

/**
 * Created by LiangMaYong on 2016/9/18.
 */
public class BaseUtils {

    private BaseUtils() {
    }

    /**
     * goTo
     *
     * @param cls cls
     */
    public static void goTo(Context context, Class<? extends Activity> cls) {
        goTo(context, cls, null);
    }

    /**
     * goTo
     *
     * @param cls    cls
     * @param extras extras
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
     * @param title title
     * @param url   url
     */
    public static void goTo(Context context, String title, String url) {
        goTo(context, title, url, null);
    }

    /**
     * goTo
     *
     * @param title   title
     * @param url     url
     * @param headers headers
     */
    public static void goTo(Context context, String title, String url, HashMap<String, String> headers) {
        Bundle extras = new Bundle();
        extras.putString(BaseInterface.WEB_EXTRA_TITLE, title);
        extras.putString(BaseInterface.WEB_EXTRA_URL, url);
        if (headers != null) {
            extras.putSerializable(BaseInterface.WEB_EXTRA_HEADERS, headers);
        }
        goTo(context, WebActivity.class, extras);
    }
}
