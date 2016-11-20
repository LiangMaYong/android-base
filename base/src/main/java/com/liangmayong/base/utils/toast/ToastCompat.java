package com.liangmayong.base.utils.toast;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by ttt on 2016/7/5.
 */
public class ToastCompat implements IToast {

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    private static int checkNotification = -1;

    private IToast mIToast;

    public ToastCompat(Context context) {
        this(context, null, -1);
    }

    ToastCompat(Context context, String text, int duration) {
        if (checkNotification == -1) {
            checkNotification = isNotificationEnabled(context) ? 0 : 1;
        }

        if (checkNotification == 1) {
            mIToast = CustomToast.makeText(context, text, duration);
        } else {
            mIToast = SystemToast.makeText(context, text, duration);
        }
    }

    public static IToast makeText(Context context, String text, int duration) {
        return new ToastCompat(context, text, duration);
    }

    @Override
    public IToast setGravity(int gravity, int xOffset, int yOffset) {
        return mIToast.setGravity(gravity, xOffset, yOffset);
    }

    @Override
    public IToast setDuration(long durationMillis) {
        return mIToast.setDuration(durationMillis);
    }

    @Override
    public IToast setView(View view) {
        return mIToast.setView(view);
    }

    @Override
    public IToast setMargin(float horizontalMargin, float verticalMargin) {
        return mIToast.setMargin(horizontalMargin, verticalMargin);
    }

    @Override
    public IToast setText(String text) {
        return mIToast.setText(text);
    }

    @Override
    public void show() {
        mIToast.show();
    }

    @Override
    public void cancel() {
        mIToast.cancel();
    }

    public static boolean isNotificationEnabled(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                ApplicationInfo appInfo = context.getApplicationInfo();
                String pkg = context.getApplicationContext().getPackageName();
                int uid = appInfo.uid;
                Class appOpsClass = null; /* Context.APP_OPS_MANAGER */
                appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
                int value = (int) opPostNotificationValue.get(Integer.class);
                return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
            }
        } catch (Exception e) {
        }
        return true;
    }
}
