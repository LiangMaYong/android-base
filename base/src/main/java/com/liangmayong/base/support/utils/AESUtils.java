package com.liangmayong.base.support.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by LiangMaYong on 2016/12/12.
 */
public class AESUtils {

    private static String KEY_AES = "AES";
    private static String CIPHER_AES = "AES/CBC/PKCS5Padding";
    private static String ivE = "nationalnational";
    private static AESUtils des = null;

    private AESUtils() {
    }

    private static AESUtils getAes() {
        if (des == null) {
            des = new AESUtils();
        }
        return des;
    }

    /**
     * encrypt
     *
     * @param encryptByte encryptByte
     * @param encryptKey  encryptKey
     * @return encrypt string
     */
    public static String encrypt(byte[] encryptByte, String encryptKey, boolean convertKey) {
        if (convertKey) {
            encryptKey = getKey(encryptKey);
        }
        return getAes()._encrypt(encryptByte, encryptKey);
    }


    /**
     * decrypt
     *
     * @param encryptString encryptString
     * @param encryptKey    encryptKey
     * @return bytes
     */
    public static byte[] decrypt(String encryptString, String encryptKey, boolean convertKey) {
        if (convertKey) {
            encryptKey = getKey(encryptKey);
        }
        return getAes()._decrypt(encryptString, encryptKey);
    }

    /**
     * _encrypt
     *
     * @param src        src
     * @param encryptKey encryptKey
     * @return
     */
    public String _encrypt(byte[] src, String encryptKey) {
        try {
            IvParameterSpec iv = new IvParameterSpec(ivE.getBytes());
            SecretKeySpec skeySpec = new SecretKeySpec(encryptKey.getBytes(), KEY_AES);
            Cipher cipher = Cipher.getInstance(CIPHER_AES);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(src);
            return Base64Utils.encode(encrypted);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * _decrypt
     *
     * @param encryptString encryptString
     * @param encryptKey    encryptKey
     * @return bytes
     */
    public byte[] _decrypt(String encryptString, String encryptKey) {
        try {
            byte[] encryptByte = Base64Utils.decode(encryptString);
            IvParameterSpec iv = new IvParameterSpec(ivE.getBytes());
            SecretKeySpec skeySpec = new SecretKeySpec(encryptKey.getBytes(), KEY_AES);
            Cipher cipher = Cipher.getInstance(CIPHER_AES);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            return cipher.doFinal(encryptByte);
        } catch (Exception e) {
        }
        return new byte[0];
    }

    /**
     * md5 encode
     *
     * @param plain plain
     * @return string
     */
    private final static String md5(String plain) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plain.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return re_md5;
    }

    /**
     * The encryptKey to 8 characters
     *
     * @param encryptKey encryptKey
     * @return string
     */
    private static String getKey(String encryptKey) {
        return md5(encryptKey).substring(4, 20);
    }

}
