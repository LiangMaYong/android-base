package com.liangmayong.base.support.photo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.security.MessageDigest;
import java.util.List;

/**
 * Created by LiangMaYong on 2017/2/8.
 */

public class PhotoIntent {

    //ourInstance
    private static volatile PhotoIntent ourInstance;

    /**
     * getInstance
     *
     * @return PhotoUtils
     */
    public static PhotoIntent getInstance() {
        if (ourInstance == null) {
            synchronized (PhotoIntent.class) {
                ourInstance = new PhotoIntent();
            }
        }
        return ourInstance;
    }

    private PhotoIntent() {
    }

    /**
     * getSelectIntent
     *
     * @return Intent
     */
    public Intent getSelectIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return intent;
    }

    /**
     * getImageTakeIntent
     *
     * @return Intent
     */
    public Intent getTakeIntent(Context context, int id) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = getUri(context, id);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        return intent;
    }

    /**
     * getCropIntent
     *
     * @param dataUri old file uri
     * @param id      id
     * @param width   bitmap width
     * @param height  bitmap height
     * @return Intent
     */
    public Intent getCropIntent(Context context, Uri dataUri, int id, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(dataUri, "image/*");
        intent.putExtra("crop", "true");
        if (width != 0 && height != 0) {
            if (android.os.Build.MANUFACTURER.contains("HUAWEI") && width == height) {
                intent.putExtra("aspectX", 9998);
                intent.putExtra("aspectY", 9999);
            } else {
                intent.putExtra("aspectX", width);
                intent.putExtra("aspectY", height);
            }
            intent.putExtra("outputX", width);
            intent.putExtra("outputY", height);
            intent.putExtra("scale", true);
            intent.putExtra("scaleUpIfNeeded", true);
        }
        Uri formUri = getUri(context, id);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, formUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, formUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        return intent;
    }

    /**
     * getUri
     *
     * @param context context
     * @param id      id
     * @return Uri
     */
    public Uri getUri(Context context, int id) {
        return PhotoProvider.getCacheUri(context, getName(id));
    }


    /**
     * getPath
     *
     * @param context context
     * @param id      id
     * @return path
     */
    public String getPath(Context context, int id) {
        return new File(PhotoProvider.getCacheDirectory(context), getName(id)).getPath();
    }

    public String getName(int id) {
        return "cache_" + encrypt("photo_" + id);
    }


    /**
     * MD5 encrypt
     *
     * @param str string
     * @return encrypt string
     */
    @SuppressLint("DefaultLocale")
    private String encrypt(String str) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = str.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte tmp[] = mdTemp.digest();
            char strs[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                strs[k++] = hexDigits[byte0 >>> 4 & 0xf];
                strs[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(strs).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }
}
