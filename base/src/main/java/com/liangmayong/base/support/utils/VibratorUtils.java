package com.liangmayong.base.support.utils;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Vibrator;

/**
 * Created by LiangMaYong on 2017/2/14.
 */

public class VibratorUtils {

    private VibratorUtils() {
    }

    public static void vibrate(final Context context, long milliseconds) {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = PackageManager.PERMISSION_GRANTED == context.checkSelfPermission(Manifest.permission.VIBRATE);
        }
        if (flag) {
            Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
            vib.vibrate(milliseconds);
        }
    }

    public static void vibrate(final Context context, long[] pattern, boolean isRepeat) {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = PackageManager.PERMISSION_GRANTED == context.checkSelfPermission(Manifest.permission.VIBRATE);
        }
        if (flag) {
            Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
            vib.vibrate(pattern, isRepeat ? 1 : -1);
        }
    }
}
