package com.liangmayong.base.utils;

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

	private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
			.toCharArray();

	/**
	 * Base64 encode
	 * 
	 * @param data
	 *            data
	 * @return String
	 */
	public static String encode(byte[] data) {
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
	 * @param string
	 *            string
	 * @return byte[]
	 */
	public static byte[] decode(String string) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			decode(string, bos);
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

	private static void decode(String s, OutputStream os) throws IOException {
		int i = 0;

		int len = s.length();

		while (true) {
			while (i < len && s.charAt(i) <= ' ')
				i++;

			if (i == len)
				break;

			int tri = (decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12) + (decode(s.charAt(i + 2)) << 6)
					+ (decode(s.charAt(i + 3)));

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

	private static int decode(char c) {
		if (c >= 'A' && c <= 'Z')
			return ((int) c) - 65;
		else if (c >= 'a' && c <= 'z')
			return ((int) c) - 97 + 26;
		else if (c >= '0' && c <= '9')
			return ((int) c) - 48 + 26 + 26;
		else
			switch (c) {
			case '+':
				return 62;
			case '/':
				return 63;
			case '=':
				return 0;
			default:
				throw new RuntimeException("unexpected code: " + c);
			}
	}

	/**
	 * encodeAsFile
	 * 
	 * @param path
	 *            path
	 * @return Base64 String
	 */
	public static String encodeAsFile(String path) {
		try {
			StringBuffer s = new StringBuffer();
			File file = new File(path);
			if (file.exists()) {
				FileInputStream inputFile = new FileInputStream(file);
				byte[] buffer = new byte[10 * 10];
				while (inputFile.read(buffer) > 0) {
					s.append(encode(buffer));
					buffer = new byte[10 * 10];
				}
				inputFile.close();
				return s.toString();
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * encodeAsInputStream
	 * 
	 * @param stream
	 *            stream
	 * @return Base64 String
	 */
	public static String encodeAsInputStream(InputStream stream) {
		try {
			StringBuffer s = new StringBuffer();
			if (stream != null && stream.available() > 0) {
				byte[] buffer = new byte[10 * 10];
				while (stream.read(buffer) > 0) {
					s.append(encode(buffer));
					buffer = new byte[10 * 10];
				}
				stream.close();
				return s.toString();
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
	 * @param bitmap
	 *            bitmap
	 * @return Base64 String
	 */
	public static String encodeAsBitmap(Bitmap bitmap) {
		if (bitmap != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			bitmap.recycle();
			return encode(baos.toByteArray());
		} else {
			return "";
		}
	}
}
