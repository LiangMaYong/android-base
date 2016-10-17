package com.liangmayong.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.liangmayong.base.activitys.WebActivity;
import com.liangmayong.base.interfaces.BaseInterface;

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
        Bundle extras = new Bundle();
        extras.putString(BaseInterface.WEB_EXTRA_TITLE, title);
        extras.putString(BaseInterface.WEB_EXTRA_URL, url);
        goTo(context, WebActivity.class, extras);
    }
}
