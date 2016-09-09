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
	 * 
	 * @param str
	 *            string
	 * @return encrypt string
	 */
	public final static String encrypt(String str) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
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
