package com.liangmayong.base.support.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.liangmayong.base.support.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LiangMaYong on 2017/1/17.
 */
public final class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Context mContext;
    private Thread.UncaughtExceptionHandler handler;

    public static void init(Context context) {
        init(context, null);
    }

    public static void init(Context context, Thread.UncaughtExceptionHandler handler) {
        new CrashHandler(context, handler);
    }

    private CrashHandler(Context context, Thread.UncaughtExceptionHandler handler) {
        this.mContext = context.getApplicationContext();
        this.handler = handler;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        String errMsg = getError(thread, ex);
        Logger.e(errMsg);
        if (handler != null) {
            handler.uncaughtException(thread, ex);
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private String getError(Thread td, Throwable ex) {
        String errInfo = "";
        if (ex != null) {
            try {
                errInfo += "\r\n|====================================================================|\r\n";
                // write currtime
                errInfo += getCurrTime();
                // write device
                errInfo += getUEPROF();
                // write clientInfo
                errInfo += getPkgInfo();
                // write thread
                if (td != null) {
                    errInfo += td.toString();
                }
                errInfo += "\r\n";

                ByteArrayOutputStream baos = null;
                try {
                    baos = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(baos);
                    int times = 0;
                    while (ex != null) {
                        ex.printStackTrace(ps);
                        ex = ex.getCause();
                        times++;
                    }
                    baos.flush();
                    byte[] bytes = baos.toByteArray();
                    baos.close();
                    ps.close();
                    errInfo += new String(bytes, "utf-8");
                } catch (Exception e) {
                }
                errInfo += "\r\n|====================================================================|\r\n";
            } catch (Exception e) {
            }
        }
        return errInfo;
    }

    private String getUEPROF() {
        StringBuilder mBuffer = new StringBuilder();

        int appCode = 1;
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
            appCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        mBuffer.setLength(0);
        mBuffer.append("ME-2-Android_");
        mBuffer.append(android.os.Build.VERSION.RELEASE.replace('-', '_'));
        mBuffer.append("-");
        mBuffer.append(appCode);
        mBuffer.append("-");
        mBuffer.append(android.os.Build.VERSION.SDK_INT);
        mBuffer.append("-");

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mBuffer.append(dm.heightPixels);
        mBuffer.append("*");
        mBuffer.append(dm.widthPixels);
        mBuffer.append("-");
        mBuffer.append(android.os.Build.MODEL.replace('-', '_'));
        mBuffer.append("\r\n");

        return mBuffer.toString();
    }

    private String getPkgInfo() {
        StringBuffer sb = new StringBuffer();
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), 0);
            String verName = info.versionName;
            int verCode = info.versionCode;
            sb.append("PackageName=");
            sb.append(mContext.getPackageName());
            sb.append("; VersionName=");
            sb.append(verName);
            sb.append("; VersionCode=");
            sb.append(verCode);
            sb.append("\r\n");
        } catch (Exception e) {
        }
        return sb.toString();
    }

    private String getCurrTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(new Date()) + "\r\n";
    }

}
