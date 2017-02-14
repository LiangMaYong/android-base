package com.liangmayong.base.support.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Vibrator;

/**
 * Created by LiangMaYong on 2017/2/14.
 */

public class VibratorUtils {

    public static void vibrate(final Activity activity, long milliseconds) {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = PackageManager.PERMISSION_GRANTED == activity.checkSelfPermission(Manifest.permission.VIBRATE);
        }
        if (flag) {
            Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
            vib.vibrate(milliseconds);
        }
    }

    public static void vibrate(final Activity activity, long[] pattern, boolean isRepeat) {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = PackageManager.PERMISSION_GRANTED == activity.checkSelfPermission(Manifest.permission.VIBRATE);
        }
        if (flag) {
            Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
            vib.vibrate(pattern, isRepeat ? 1 : -1);
        }
    }
}
