package com.liangmayong.base.utils;

import android.annotation.SuppressLint;

import java.security.MessageDigest;

/**
 * Md5Utils
 *
 * @author LiangMaYong
 * @version 1.0
 */
@SuppressLint("DefaultLocale")
public class Md5Utils {

    /**
     * MD5 encrypt
     *
     * @param str string
     * @return encrypt string
     */
    public final static String encrypt(String str) {
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
     * MD5 encrypt
     *
     * @param str string
     * @return encrypt int
     */
    public final static int encryptInt(String str) {
        String md5 = encrypt(str);
        String hex1 = md5.substring(0, 2) + md5.substring(10, 12) + md5.substring(20, 22) + md5.substring(30, 32);
        String hex2 = md5.substring(2, 4) + md5.substring(12, 14) + md5.substring(22, 24);
        String hex3 = md5.substring(4, 6) + md5.substring(14, 16) + md5.substring(24, 26);
        String hex4 = md5.substring(6, 8) + md5.substring(16, 18) + md5.substring(26, 28);
        String hex5 = md5.substring(8, 10) + md5.substring(18, 20) + md5.substring(28, 30);
        long type = Long.parseLong(hex1, 16) + Long.parseLong(hex2, 16) + Long.parseLong(hex3, 16) + Long.parseLong(hex4, 16) + Long.parseLong(hex5, 16);
        while (type > Integer.MAX_VALUE) {
            type = type / 2;
        }
        return (int) type;
    }
}
