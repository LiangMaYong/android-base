package com.liangmayong.base.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.security.MessageDigest;

/**
 * PhotoUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class PhotoUtils {

    //takePhoto
    private static volatile PhotoUtils takePhoto;

    /**
     * getInstance
     *
     * @return PhotoUtils
     */
    public static PhotoUtils getInstance() {
        if (takePhoto == null) {
            synchronized (PhotoUtils.class) {
                takePhoto = new PhotoUtils();
            }
        }
        return takePhoto;
    }

    /**
     * PhotoUtils
     */
    private PhotoUtils() {
    }

    private static String tempDir = "/takephoto/take/";

    /**
     * init
     *
     * @param tempDir tempDir
     */
    public static void setTempDir(String tempDir) {
        PhotoUtils.tempDir = tempDir;
    }

    /**
     * startTake
     *
     * @param activity activity
     * @param id       id
     */
    public void startTake(final Activity activity, final int id) {
        activity.startActivityForResult(getTakeIntent(id + 0xAA), id + 0xAA);
    }

    /**
     * startSelect
     *
     * @param activity activity
     * @param id       id
     */
    public void startSelect(final Activity activity, final int id) {
        activity.startActivityForResult(getSelectIntent(), id + 0xFF);
    }

    /**
     * handleResult
     *
     * @param id          id
     * @param width       width
     * @param height      height
     * @param activity    activity
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     * @return bitmap
     */
    public Bitmap handleResult(final int id, int width, int height, Activity activity, int requestCode, int resultCode,
                               Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            if (requestCode == id + 0xAA) {
                activity.startActivityForResult(getCropIntent(getUri(id + 0xAA), id, width, height), id);
            } else if (requestCode == id + 0xFF) {
                activity.startActivityForResult(getCropIntent(data.getData(), id, width, height), id);
            } else if (requestCode == id) {
                // delete take photo
                File file = new File(getPath(id + 0xAA));
                if (file.exists()) {
                    file.delete();
                }
                return getThumbnail(id, width, height);
            }
        }
        return null;
    }


    /**
     * clearTemp
     */
    public void clearTemp() {
        String dirName = tempDir;
        String fileDir = Environment.getExternalStorageDirectory().getPath() + "/" + dirName;
        if (!createDir(fileDir)) {
            dirName = "";
            fileDir = Environment.getExternalStorageDirectory().getPath() + "/" + dirName;
        }
        File file = new File(fileDir);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * getPath
     *
     * @param id name
     * @return temp filename
     */
    public String getPath(int id) {
        String dirName = tempDir;
        String fileDir = Environment.getExternalStorageDirectory().getPath() + "/" + dirName;
        if (!createDir(fileDir)) {
            dirName = "";
            fileDir = Environment.getExternalStorageDirectory().getPath() + "/" + dirName;
        }
        return fileDir + "/" + "temp_photoselector_" + encrypt(id + "");
    }

    /**
     * getThumbnail
     *
     * @param id     id
     * @param width  width
     * @param height height
     * @return Bitmap
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public Bitmap getThumbnail(int id, int width, int height) {
        String imagePath = getPath(id);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false;
        int w = options.outWidth;
        int h = options.outHeight;
        if (width == 0 || height == 0) {
            width = w;
            height = h;
        }
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * getSelectIntent
     *
     * @return Intent
     */
    private Intent getSelectIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return intent;
    }

    /**
     * getImageTakeIntent
     *
     * @return Intent
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private Intent getTakeIntent(int id) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri(id));
        return intent;
    }

    /**
     * getCropIntent
     *
     * @param uri    old file uri
     * @param id     id
     * @param width  bitmap width
     * @param height bitmap height
     * @return Intent
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private Intent getCropIntent(Uri uri, int id, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri(id));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        return intent;
    }

    /**
     * getUri
     *
     * @param id id
     * @return Uri
     */
    private Uri getUri(int id) {
        return Uri.parse("file://" + "/" + getPath(id));
    }

    /**
     * createDir
     *
     * @param dirName dirName
     * @return boolean
     */
    private boolean createDir(String dirName) {
        File file = new File(dirName);
        if (!file.exists()) {
            return file.mkdirs();
        } else {
            return true;
        }
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
