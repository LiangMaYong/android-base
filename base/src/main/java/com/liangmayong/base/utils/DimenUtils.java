package com.liangmayong.base.utils;

import android.content.Context;

/**
 * DimenUtils
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public final class DimenUtils {

	private DimenUtils() {
	}

	/**
	 * dip2px
	 * 
	 * @param context
	 *            context
	 * @param dpValue
	 *            dpValue
	 * @return pxValue
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px2dip
	 * 
	 * @param context
	 *            context
	 * @param pxValue
	 *            pxValue
	 * @return dpValue
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
