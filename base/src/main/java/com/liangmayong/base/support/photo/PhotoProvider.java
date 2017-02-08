package com.liangmayong.base.support.photo;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;

/**
 * Created by LiangMaYong on 2017/2/8.
 * file_paths.xml
 * <p>
 * <?xml version="1.0" encoding="utf-8"?>
 * <resource>
 * <cache-path
 * name="base_photo_provider"
 * path="base/base_photo_provider/"/>
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
public class PhotoProvider {

    // AUTHORITY
    private static final String AUTHORITY = "com.liangmayong.base.authority.fileprovider";
    // PATH
    private static final String PATH = "/base/base_photo_provider/";
    // NAME
    private static final String NAME = "base_photo_provider";

    /**
     * getCacheUri
     *
     * @param context  context
     * @param filename filename
     * @return uri
     */
    public static Uri getCacheUri(Context context, String filename) {
        final String dir = getCacheDirectory(context);
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File file = new File(dir, filename);
        Uri uri = FileProvider.getUriForFile(context, AUTHORITY, file);
        return uri;
    }

    /**
     * getCacheDirectory
     *
     * @param context context
     * @return dir
     */
    public static String getCacheDirectory(Context context) {
        final String dir = context.getCacheDir().getPath() + PATH;
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
        return uri.toString().replaceAll("content://" + AUTHORITY + "/" + NAME, getCacheDirectory(context));
    }
}
