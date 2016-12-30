package com.liangmayong.base.support.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * ShareUtils
 */
public final class ShareUtils {

    private ShareUtils() {
    }

    /**
     * shareText
     *
     * @param context     context
     * @param dialogTitle dialogTitle
     * @param content     content
     */
    public static void shareText(Context context, String dialogTitle, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, content);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, dialogTitle));
    }

    /**
     * shareFile
     *
     * @param context     context
     * @param dialogTitle dialogTitle
     * @param content     content
     * @param uri         uri
     */
    public static void shareFile(Context context, String dialogTitle, String content, Uri uri) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        if (uri != null) {
            shareIntent.setType("image/*");
            shareIntent.putExtra("sms_body", content);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        } else {
            shareIntent.setType("text/plain");
        }
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, content);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(shareIntent, dialogTitle));
    }
}
