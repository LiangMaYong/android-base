package com.liangmayong.base.utils;

import android.annotation.SuppressLint;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DesUtils
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class DesUtils {

	private String iv = "national";
	private static DesUtils des = null;

	private DesUtils() {
	}

	private static DesUtils getDes() {
		if (des == null) {
			des = new DesUtils();
		}
		return des;
	}

	/**
	 * encrypt
	 * 
	 * @param encryptByte
	 *            encryptByte
	 * @param encryptKey
	 *            encryptKey
	 * @return encrypt string
	 */
	public static String encrypt(byte[] encryptByte, String encryptKey) {
		return getDes()._encrypt(encryptByte, getKey(encryptKey));
	}

	@SuppressLint("TrulyRandom")
	private String _encrypt(byte[] encryptByte, String encryptKey) {
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes());
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
			byte[] encryptedData = cipher.doFinal(encryptByte);
			return Base64Utils.encode(encryptedData);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * decrypt
	 * 
	 * @param encryptString
	 *            encryptString
	 * @param encryptKey
	 *            encryptKey
	 * @return byte[]
	 */
	public static byte[] decrypt(String encryptString, String encryptKey) {
		return getDes()._decrypt(encryptString, getKey(encryptKey));
	}

	private byte[] _decrypt(String encryptString, String encryptKey) {
		try {
			byte[] encryptByte = Base64Utils.decode(encryptString);
			IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes());
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			return cipher.doFinal(encryptByte);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * md5 encode
	 * 
	 * @param plain
	 *            plain
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
	 * @param encryptKey
	 *            encryptKey
	 * @return string
	 */
	private static String getKey(String encryptKey) {
		return md5(encryptKey).substring(4, 12);
	}
}