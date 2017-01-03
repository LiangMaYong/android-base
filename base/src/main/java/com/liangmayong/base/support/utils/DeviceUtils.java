package com.liangmayong.base.support.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

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
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            info.put("DEVICE.Id(IMEI)", tm.getDeviceId());
            info.put("DEVICE.SoftwareVersion", tm.getDeviceSoftwareVersion());
            info.put("DEVICE.Line1Number", tm.getLine1Number());
            info.put("DEVICE.NetworkCountryIso", tm.getNetworkCountryIso());
            info.put("DEVICE.NetworkOperator", tm.getNetworkOperator());
            info.put("DEVICE.NetworkOperatorName", tm.getNetworkOperatorName());
            info.put("DEVICE.NetworkType", tm.getNetworkType() + "");
            info.put("DEVICE.PhoneType", tm.getPhoneType() + "");
            info.put("DEVICE.SimCountryIso", tm.getSimCountryIso());
            info.put("DEVICE.SimOperator", tm.getSimOperator());
            info.put("DEVICE.SimOperatorName", tm.getSimOperatorName());
            info.put("DEVICE.SimSerialNumber", tm.getSimSerialNumber());
            info.put("DEVICE.SimState", tm.getSimState() + "");
            info.put("DEVICE.SubscriberId(IMSI)", tm.getSubscriberId());
            info.put("DEVICE.VoiceMailNumber", tm.getVoiceMailNumber());
        } catch (Exception e) {
        }
        info.put("BUILD.MODEL", Build.MODEL);
        info.put("BUILD.DEVICE", Build.DEVICE);
        info.put("BUILD.PRODUCT", Build.PRODUCT);
        info.put("BUILD.SDK_INT", Build.VERSION.SDK_INT + "");
        info.put("BUILD.PREVIEW_SDK_INT", Build.VERSION.PREVIEW_SDK_INT + "");
        info.put("APP.VERSION_NAME", getVersionName(context));
        info.put("APP.VERSION_CODE", getVersionCode(context) + "");
        info.put("DEVICE.Width", ScreenUtils.getScreenWidth(context) + "");
        info.put("DEVICE.Height", ScreenUtils.getScreenHeight(context) + "");
        info.put("DEVICE.Type", "Android");
        return info;
    }

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
