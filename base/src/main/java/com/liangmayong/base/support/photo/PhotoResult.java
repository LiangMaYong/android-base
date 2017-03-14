package com.liangmayong.base.support.photo;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.liangmayong.base.support.photo.intent.PhotoCache;
import com.liangmayong.base.support.photo.intent.PhotoIntent;

/**
 * Created by LiangMaYong on 2017/2/8.
 */
public class PhotoResult {

    private int requestId = 0;
    private int width = 0;
    private int height = 0;
    private Uri uri = null;
    private Context context;

    public PhotoResult(Context context, int requestId, int width, int height, Uri uri) {
        this.requestId = requestId;
        this.width = width;
        this.height = height;
        this.uri = uri;
        this.context = context;
    }


    public Bitmap getThumbnail() {
        return getThumbnail(getPath(), width, height);
    }

    /**
     * getPath
     *
     * @return uri
     */
    public String getPath() {
        if (uri != null) {
            return getFilePath(context, uri) + "";
        }
        return PhotoIntent.getInstance().getPath(context, requestId) + "";
    }

    /**
     * getUri
     *
     * @return uri
     */
    public Uri getUri() {
        if (uri != null) {
            return uri;
        }
        return PhotoIntent.getInstance().getUri(context, requestId);
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
    private Bitmap getThumbnail(String imagePath, int width, int height) {
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
     * Try to return the absolute file path from the given Uri
     *
     * @param context context
     * @param uri     uri
     * @return the file path or null
     */
    private static String getFilePath(Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Files.FileColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
            if (data == null) {
                data = PhotoCache.getCachePath(context, uri);
            }
        }
        return data;
    }

    /**
     * OnPhotoResultListener
     */
    public interface Listener {
        void onResult(PhotoResult result);
    }
}
