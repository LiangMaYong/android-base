package com.liangmayong.base.support.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.liangmayong.base.BaseApplication;
import com.liangmayong.base.support.database.DataPreferences;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by LiangMaYong on 2017/1/3.
 */
public class DeviceUtils {

    private DeviceUtils() {
    }

    public static Map<String, String> getDeviceInfo(Context context) {
        Map<String, String> info = new TreeMap<String, String>();
        try {
            info.put("BUILD.SDK_INT", Build.VERSION.SDK_INT + "");
        } catch (Exception e) {
            info.put("BUILD.SDK_INT", Build.VERSION.SDK + "");
        }
        info.put("APP.NAME", getAppName(context));
        info.put("APP.USER", getUserId(getDeviceId(context)));
        info.put("APP.VERSION_NAME", getVersionName(context));
        info.put("APP.VERSION_CODE", getVersionCode(context) + "");
        info.put("APP.BASIC_VERSION", BaseApplication.getBaseVersion());
        info.put("DEVICE.User", Build.USER);
        info.put("DEVICE.Width", ScreenUtils.getScreenWidth(context) + "");
        info.put("DEVICE.Height", ScreenUtils.getScreenHeight(context) + "");
        info.put("DEVICE.Type", "Android");
        info.put("DEVICE.UUID", getDeviceId(context));
        info.put("DEVICE.MODEL", Build.MODEL);
        info.put("DEVICE.DEVICE", Build.DEVICE);
        info.put("DEVICE.PRODUCT", Build.PRODUCT);
        info.put("DEVICE.BOARD", Build.BOARD);
        info.put("DEVICE.ID", Build.ID);
        try {
            info.put("DEVICE.ANDROID_ID", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        } catch (Exception e) {
        }
        return info;
    }

    /**
     * setUserId
     *
     * @param userId userId
     */
    public static void setUserId(String userId) {
        DataPreferences.getPreferences("android_base_device_utils").setString("userId", userId);
    }


    /**
     * getUserId
     *
     * @param defValue defValue
     * @return userId
     */
    public static String getUserId(String defValue) {
        return DataPreferences.getPreferences("android_base_device_utils").getString("userId", defValue);
    }

    /**
     * getVersionName
     *
     * @param context context
     * @return version name
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * getDeviceId
     *
     * @return device id
     */
    public static String getDeviceId(Context context) {
        String deviceId = Build.BOARD +
                Build.BRAND +
                Build.CPU_ABI +
                Build.DEVICE +
                Build.DISPLAY +
                Build.HOST +
                Build.ID +
                Build.MANUFACTURER +
                Build.MODEL +
                Build.PRODUCT +
                Build.TAGS +
                Build.TYPE +
                Build.SERIAL +
                Build.USER;
        try {
            deviceId = deviceId + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
        }
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId += tm.getDeviceId();
        } catch (Exception e) {
        }
        deviceId += "w" + ScreenUtils.getScreenWidth(context) + "";
        deviceId += "h" + ScreenUtils.getScreenHeight(context) + "";
        return Md5Utils.encrypt(deviceId);
    }

    /**
     * getAppName
     *
     * @param context context
     * @return app name
     */
    public static String getAppName(Context context) {
        PackageManager pm = context.getPackageManager();
        String appName = context.getApplicationInfo().loadLabel(pm).toString();
        return appName;
    }

    /**
     * getVersionCode
     *
     * @param context context
     * @return version code
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            return 0;
        }
    }
}
