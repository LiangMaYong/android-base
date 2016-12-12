package com.liangmayong.base.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;
import java.security.MessageDigest;

/**
 * PhotoUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class PhotoUtils {
    //ourInstance
    private static volatile PhotoUtils ourInstance;

    /**
     * getInstance
     *
     * @return PhotoUtils
     */
    public static PhotoUtils getInstance() {
        if (ourInstance == null) {
            synchronized (PhotoUtils.class) {
                ourInstance = new PhotoUtils();
            }
        }
        return ourInstance;
    }

    /**
     * PhotoUtils
     */
    private PhotoUtils() {
    }

    //tempDir
    private static String tempDir = "android_base/take";

    /**
     * init
     *
     * @param tempDir tempDir
     */
    public void init(String tempDir) {
        PhotoUtils.tempDir = tempDir;
    }

    /**
     * startTake
     *
     * @param activity activity
     * @param id       id
     */
    public void startTake(Activity activity, int id, boolean crop) {
        if (crop) {
            activity.startActivityForResult(getTakeIntent(id + 0xAA), id + 0xAA);
        } else {
            activity.startActivityForResult(getTakeIntent(id), id);
        }
    }

    /**
     * startTake
     *
     * @param fragment fragment
     * @param id       id
     */
    public void startTake(Fragment fragment, int id, boolean crop) {
        if (crop) {
            fragment.startActivityForResult(getTakeIntent(id + 0xAA), id + 0xAA);
        } else {
            fragment.startActivityForResult(getTakeIntent(id), id);
        }
    }

    /**
     * startSelect
     *
     * @param activity activity
     * @param id       id
     */
    public void startSelect(Activity activity, int id, boolean crop) {
        if (crop) {
            activity.startActivityForResult(getSelectIntent(id + 0xFF), id + 0xFF);
        } else {
            activity.startActivityForResult(getSelectIntent(id), id);
        }
    }

    /**
     * startSelect
     *
     * @param fragment fragment
     * @param id       id
     */
    public void startSelect(Fragment fragment, int id, boolean crop) {
        if (crop) {
            fragment.startActivityForResult(getSelectIntent(id + 0xFF), id + 0xFF);
        } else {
            fragment.startActivityForResult(getSelectIntent(id), id);
        }
    }

    public interface OnPhotoResultListener {
        void onResult(Result result);
    }

    /**
     * handleResult
     *
     * @param id          id
     * @param width       width
     * @param height      height
     * @param fragment    activity
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     * @param listener    listener
     */
    public void handleResult(final int id, int width, int height, Fragment fragment, int requestCode, int resultCode,
                             Intent data, OnPhotoResultListener listener) {
        if (Activity.RESULT_OK == resultCode) {
            if (requestCode == id + 0xAA) {
                fragment.startActivityForResult(getCropIntent(getUri(id + 0xAA), id, width, height), id);
            } else if (requestCode == id + 0xFF) {
                fragment.startActivityForResult(getCropIntent(data.getData(), id, width, height), id);
            } else if (requestCode == id) {
                // delete take photo
                File file = new File(getPath(id + 0xAA));
                if (file.exists()) {
                    file.delete();
                }
                if (listener != null) {
                    listener.onResult(new Result(fragment.getActivity(), id, width, height, data.getData()));
                }
            }
        }
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
     * @param listener    listener
     */
    public void handleResult(final int id, int width, int height, Activity activity, int requestCode, int resultCode,
                             Intent data, OnPhotoResultListener listener) {
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
                if (listener != null) {
                    listener.onResult(new Result(activity, id, width, height, data.getData()));
                }
            }
        }
    }

    /**
     * Result
     */
    public class Result {
        private int requestId = 0;
        private int width = 0;
        private int height = 0;
        private Uri uri = null;
        private Context context;

        public Result(Context context, int requestId, int width, int height, Uri uri) {
            this.requestId = requestId;
            this.width = width;
            this.height = height;
            this.uri = uri;
            this.context = context;
        }

        public Bitmap getThumbnail() {
            return PhotoUtils.this.getThumbnail(getPath(), width, height);
        }

        /**
         * getPath
         *
         * @return uri
         */
        public String getPath() {
            if (uri != null) {
                return getRealFilePath(uri);
            }
            return PhotoUtils.this.getPath(requestId);
        }

        /**
         * Try to return the absolute file path from the given Uri
         *
         * @param uri uri
         * @return the file path or null
         */
        private String getRealFilePath(final Uri uri) {
            if (null == uri) return null;
            final String scheme = uri.getScheme();
            String data = null;
            if (scheme == null)
                data = uri.getPath();
            else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
                data = uri.getPath();
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        if (index > -1) {
                            data = cursor.getString(index);
                        }
                    }
                    cursor.close();
                }
            }
            return data;
        }
    }


    /**
     * clearCache
     */
    public void clearCache() {
        String dirName = tempDir;
        String fileDir = getRootPath() + "/" + dirName;
        if (!createDir(fileDir)) {
            dirName = "";
            fileDir = getRootPath() + "/" + dirName;
        }
        File file = new File(constrictFolder(fileDir, "/"));
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
        String fileDir = getRootPath() + "/" + dirName;
        if (!createDir(fileDir)) {
            dirName = "";
            fileDir = getRootPath() + "/" + dirName;
        }
        return constrictFolder(fileDir + "/" + "temp_" + encrypt(id + ""), "/");
    }

    /**
     * getRootPath
     *
     * @return root path
     */
    private String getRootPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * getThumbnail
     *
     * @param imagePath imagePath
     * @param width     width
     * @param height    height
     * @return Bitmap
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public Bitmap getThumbnail(String imagePath, int width, int height) {
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
    private Intent getSelectIntent(int id) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri(id));
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
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
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
        return Uri.parse(constrictFolder("file://" + "/" + getPath(id), "/"));
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

    /**
     * constrictFolder
     *
     * @param path      path
     * @param separator separator
     * @return path
     */
    private String constrictFolder(String path, String separator) {
        path = path.replaceAll("://", "#").replaceAll("\\\\", "/");
        String last = "";
        while (!last.equals(path)) {
            last = path;
            path = last.replaceAll("/([./]/)+/", "/");
        }
        while (path.contains("//")) {
            path = path.replaceAll("//", "/");
        }
        return path.replaceAll("#", "://").replaceAll(":///", "://").replaceAll("/", separator);
    }
}
