package com.liangmayong.base.support.audio;

import android.content.Context;

import com.liangmayong.base.support.http.HttpError;
import com.liangmayong.base.support.http.HttpUtils;
import com.liangmayong.base.support.http.OnHttpListener;
import com.liangmayong.base.support.utils.ContextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

/**
 * Created by LiangMaYong on 2017/1/3.
 */
public class AudioLoader {
    /**
     * OnAudioLoadListener
     */
    public interface OnAudioLoadListener {

        void onLoaded(String path);

        void onError(Exception e);
    }

    /**
     * load
     *
     * @param url          url
     * @param size         size
     * @param loadListener loadListener
     */
    public static void load(final String url, final long size, final OnAudioLoadListener loadListener) {
        HttpUtils.get(url, new OnHttpListener() {
            @Override
            public void success(byte[] data, String encode, String cookie) {
                saveAudioFile(url, data, size);
                if (loadListener != null) {
                    loadListener.onLoaded(getAudioPath(url));
                }
            }

            @Override
            public void error(HttpError e) {
                if (loadListener != null) {
                    loadListener.onError(e.getException());
                }
            }
        });
    }

    /**
     * saveAudioFile
     *
     * @param url  url
     * @param data data
     * @param size size
     */
    private static void saveAudioFile(String url, byte[] data, long size) {
        if (data.length >= size) {
            File file = new File(getAudioPath(url));
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                OutputStream output = new FileOutputStream(file);
                output.write(data);
                output.flush();
                output.close();
            } catch (IOException e) {
            }
        }
    }


    /**
     * getAudioPath
     *
     * @param url url
     * @return path
     */
    private static String getAudioPath(String url) {
        File file = ContextUtils.getApplication().getDir("audio_loader", Context.MODE_PRIVATE);
        return new File(file.getPath() + "/" + md5(url)).getPath();
    }

    /**
     * md5
     *
     * @param str str
     * @return md5 code
     */
    private static String md5(String str) {
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
