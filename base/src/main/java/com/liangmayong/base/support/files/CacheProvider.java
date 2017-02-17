package com.liangmayong.base.support.files;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.List;

/**
 * Created by LiangMaYong on 2017/2/8.
 * file_paths.xml
 * <p>
 * <?xml version="1.0" encoding="utf-8"?>
 * <resource>
 * <files-path
 * name="base_files_provider"
 * path="base/base_files_provider/"/>
 * </resource>
 * <p>
 * androidManifest.xml
 * <p>
 * <provider
 * android:name="android.support.v4.content.FileProvider"
 * android:authorities="com.liangmayong.base.authority.fileprovider"
 * android:exported="false"
 * android:grantUriPermissions="true">
 * <meta-data
 * android:name="android.support.FILE_PROVIDER_PATHS"
 * android:resource="@xml/file_paths"/>
 * </provider>
 */
public class CacheProvider {

    // AUTHORITY
    private static final String AUTHORITY = ".base.authority.fileprovider";
    // PATH
    private static final String PATH = "/base/base_cache_provider";
    // NAME
    private static final String NAME = "base_cache_provider";

    /**
     * getCacheUri
     *
     * @param context  context
     * @param filename filename
     * @return uri
     */
    public static Uri getCacheUri(Context context, String filename) {
        final String dir = getCacheDirectory(context);
        File file = new File(dir, filename);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + AUTHORITY, file);
        return uri;
    }

    /**
     * getCacheDirectory
     *
     * @param context context
     * @return dir
     */
    public static String getCacheDirectory(Context context) {
        final String dir = constrictFolder(context.getCacheDir().getPath() + PATH, "/");
        return dir;
    }

    /**
     * getCachePath
     *
     * @param context context
     * @param uri     uri
     * @return path
     */
    public static String getCachePath(Context context, final Uri uri) {
        return uri.toString().replaceAll("content://" + context.getPackageName() + AUTHORITY + "/" + NAME, getCacheDirectory(context));
    }

    /**
     * grantUriPermission
     *
     * @param context context
     * @param intent  intent
     * @param uri     uri
     */
    public static void grantUriPermission(Context context, Intent intent, Uri uri) {
        if (intent != null) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }
    }

    /**
     * constrictFolder
     *
     * @param path      path
     * @param separator separator
     * @return path
     */
    private static String constrictFolder(String path, String separator) {
        path = path.replaceAll("://", "#").replaceAll("\\\\", "/");
        String last = "";
        while (!last.equals(path)) {
            last = path;
            path = last.replaceAll("//[^/]+/..//", "/");
        }
        last = "";
        while (!last.equals(path)) {
            last = path;
            path = last.replaceAll("/([./]/)+/", "/");
        }
        while (path.contains("//")) {
            path = path.replaceAll("//", "/");
        }
        path = path.replaceAll("#", "://").replaceAll(":///", "://").replaceAll("/", separator);
        return path;
    }
}
