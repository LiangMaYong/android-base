package com.liangmayong.base.support.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * ApkUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class ApkUtils {

    /**
     * According to the package name for the application name
     *
     * @param context     context
     * @param packageName packageName
     * @return AppName
     */
    public static String getAppNameByPackageName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo info = getAppInfoByPackageName(context, packageName);
        String name = "";
        if (info != null) {
            name = pm.getApplicationLabel(info).toString();
        }
        return name;
    }

    /**
     * According to the application package name for the version number
     *
     * @param context     context
     * @param packageName packageName
     * @return VersionCode
     */
    public static int getVersionCodeByPackageName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        int code = -1;
        try {
            code = pm.getPackageInfo(packageName, 0).versionCode;
        } catch (Exception e) {
        }
        return code;
    }

    /**
     * According to the package name for application version name
     *
     * @param context     context
     * @param packageName packageName
     * @return VersionName
     */
    public static String getVersionNameByPackageName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        String name = "";
        try {
            name = pm.getPackageInfo(packageName, 0).versionName;
        } catch (Exception e) {
        }
        return name;
    }

    /**
     * According to the package name for application icon
     *
     * @param context     context
     * @param packageName packageName
     * @return Drawable
     */
    public static Drawable getAppIconByPackageName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo info = getAppInfoByPackageName(context, packageName);
        Drawable drawable = null;
        if (info != null) {
            drawable = pm.getApplicationIcon(info);
        }
        return drawable;
    }

    /**
     * According to the APK for application package name
     *
     * @param context context
     * @param file    file
     * @return packageName
     */
    @SuppressLint("DefaultLocale")
    public static String getPackageNameByApk(Context context, File file) {
        String packageName = "";
        ApplicationInfo info = getAppInfoByApk(context, file);
        if (info != null) {
            packageName = info.packageName;
        }
        return packageName;
    }

    /**
     * According to the application package name for file information
     *
     * @param context     context
     * @param packageName packageName
     * @return ApplicationInfo
     */
    @SuppressLint("DefaultLocale")
    public static ApplicationInfo getAppInfoByPackageName(Context context, String packageName) {
        ApplicationInfo appInfo = null;
        try {
            PackageManager pm = context.getPackageManager();
            appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
        }
        return appInfo;
    }

    /**
     * According to the APK application file information
     *
     * @param context context
     * @param file    file
     * @return ApplicationInfo
     */
    @SuppressLint("DefaultLocale")
    public static ApplicationInfo getAppInfoByApk(Context context, File file) {
        ApplicationInfo appInfo = null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
            if (info != null) {
                appInfo = info.applicationInfo;
            }
        } catch (Exception e) {
        }
        return appInfo;
    }

    /**
     * uninstall apk
     *
     * @param context
     * @param packageName
     */
    public static void doUnInstall(Context context, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(uninstallIntent);
    }

    /**
     * install apk
     *
     * @param context  context
     * @param file     file
     * @param listener listener
     */
    public static void doInstall(Context context, File file, OnApkInstallListener listener) {
        if (!file.exists()) {
            if (listener != null) {
                listener.failure(new FileNotFoundException("apk not found:" + file.getAbsolutePath()));
            }
        } else {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                listener.startInstall(file);
            } catch (Exception e) {
                listener.failure(e);
            }
        }
    }

    /**
     * According to the package name to determine whether a application
     * installation
     *
     * @param context     context
     * @param packageName packageName
     * @return true or false
     */
    public static boolean verifyInstall(Context context, String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * According to the package name to start the external APP
     *
     * @param context     context
     * @param packageName packageName
     * @param listener    listener
     */
    public static void startApp(Context context, String packageName, OnApkOpenListener listener) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(intent);
            if (listener != null) {
                listener.startOpen(packageName);
            }
        } catch (Exception e) {
            if (listener != null) {
                listener.failure(e);
            }
        }
    }

    /**
     * According to the package name to start the external APP, and the
     * parameters
     *
     * @param context     context
     * @param packageName packageName
     * @param bundle      bundle
     * @param listener    listener
     */
    public static void startApp(Context context, String packageName, Bundle bundle, OnApkOpenListener listener) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            context.startActivity(intent);
            if (listener != null) {
                listener.startOpen(packageName);
            }
        } catch (Exception e) {
            if (listener != null) {
                listener.failure(e);
            }
        }
    }


    /**
     * OnApkInstallListener
     *
     * @author LiangMaYong
     * @version 1.0
     */
    public interface OnApkInstallListener {
        /**
         * install app
         */
        void startInstall(File file);

        /**
         * install failure
         *
         * @param e exception
         */
        void failure(Exception e);
    }

    /**
     * IBApkInstallListener
     *
     * @author LiangMaYong
     * @version 1.0
     */
    public interface OnApkOpenListener {

        /**
         * openFragment app
         */
        void startOpen(String packageName);

        /**
         * install failure
         *
         * @param e exception
         */
        void failure(Exception e);
    }
}
