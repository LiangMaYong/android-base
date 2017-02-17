package com.liangmayong.base.support.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Base64Utils
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class Base64Utils {

    public static final char[] DEFAULT_LEGAL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
            .toCharArray();

    /**
     * Base64 encode
     *
     * @param data data
     * @return String
     */
    public static String encode(byte[] data) {
        return encode(data, DEFAULT_LEGAL_CHARS);
    }


    /**
     * encode
     *
     * @param data       data
     * @param legalChars legalChars
     * @return base64 encode
     */
    public static String encode(byte[] data, char[] legalChars) {
        int start = 0;
        int len = data.length;
        StringBuffer buf = new StringBuffer(data.length * 3 / 2);

        int end = len - 3;
        int i = start;

        while (i <= end) {
            int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 0x0ff) << 8)
                    | (((int) data[i + 2]) & 0x0ff);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append(legalChars[d & 63]);

            i += 3;
        }

        if (i == start + len - 2) {
            int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 255) << 8);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == start + len - 1) {
            int d = (((int) data[i]) & 0x0ff) << 16;

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append("==");
        }

        return buf.toString();
    }


    /**
     * Base64 decode
     *
     * @param string string
     * @return byte[]
     */
    public static byte[] decode(String string) {
        return decode(string, DEFAULT_LEGAL_CHARS);
    }

    /**
     * Base64 decode
     *
     * @param string     string
     * @param legalChars legalChars
     * @return byte[]
     */
    public static byte[] decode(String string, char[] legalChars) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            decode(string, legalChars, bos);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        byte[] decodedBytes = bos.toByteArray();
        try {
            bos.close();
            bos = null;
        } catch (IOException ex) {
            System.err.println("Error while decoding BASE64: " + ex.toString());
        }
        return decodedBytes;
    }

    /**
     * encodeAsInputStream
     *
     * @param stream stream
     * @return Base64 String
     */
    public static String encodeAsInputStream(InputStream stream) {
        return encodeAsInputStream(stream, DEFAULT_LEGAL_CHARS);
    }

    /**
     * encodeAsInputStream
     *
     * @param stream     stream
     * @param legalChars legalChars
     * @return Base64 String
     */
    public static String encodeAsInputStream(InputStream stream, char[] legalChars) {
        try {
            if (stream != null && stream.available() > 0) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[24 * 43];
                int lenght = 0;
                while ((lenght = stream.read(buffer)) > 0) {
                    baos.write(buffer, 0, lenght);
                }
                stream.close();
                return encode(baos.toByteArray(), legalChars);
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * encodeAsFile
     *
     * @param path path
     * @return Base64 String
     */
    public static String encodeAsFile(String path) {
        return encodeAsFile(path, DEFAULT_LEGAL_CHARS);
    }

    /**
     * encodeAsFile
     *
     * @param path       path
     * @param legalChars legalChars
     * @return Base64 String
     */
    public static String encodeAsFile(String path, char[] legalChars) {
        try {
            StringBuffer s = new StringBuffer();
            File file = new File(path);
            if (file.exists()) {
                FileInputStream inputFile = new FileInputStream(file);
                return encodeAsInputStream(inputFile, legalChars);
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * encode
     *
     * @param bitmap bitmap
     * @return Base64 String
     */
    public static String encodeAsBitmap(Bitmap bitmap) {
        return encodeAsBitmap(bitmap, DEFAULT_LEGAL_CHARS);
    }

    /**
     * encode
     *
     * @param bitmap     bitmap
     * @param legalChars legalChars
     * @return Base64 String
     */
    public static String encodeAsBitmap(Bitmap bitmap, char[] legalChars) {
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            bitmap.recycle();
            return encode(baos.toByteArray(), legalChars);
        } else {
            return "";
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////    Private  /////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void decode(String s, char[] legalChars, OutputStream os) throws IOException {
        int i = 0;

        int len = s.length();

        while (true) {
            while (i < len && s.charAt(i) <= ' ')
                i++;

            if (i == len)
                break;

            int tri = (decode(s.charAt(i), legalChars) << 18) + (decode(s.charAt(i + 1), legalChars) << 12) + (decode(s.charAt(i + 2), legalChars) << 6)
                    + (decode(s.charAt(i + 3), legalChars));

            os.write((tri >> 16) & 255);
            if (s.charAt(i + 2) == '=')
                break;
            os.write((tri >> 8) & 255);
            if (s.charAt(i + 3) == '=')
                break;
            os.write(tri & 255);

            i += 4;
        }
    }

    private static int decode(char c, char[] legalChars) {
        if (c >= 'A' && c <= 'Z')
            return ((int) c) - 65;
        else if (c >= 'a' && c <= 'z')
            return ((int) c) - 97 + 26;
        else if (c >= '0' && c <= '9')
            return ((int) c) - 48 + 26 + 26;
        else {
            if (c == legalChars[62]) {
                return 62;
            } else if (c == legalChars[63]) {
                return 62;
            } else if (c == '=') {
                return 0;
            } else {
                throw new RuntimeException("unexpected code: " + c);
            }
        }
    }
}
