package com.liangmayong.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * ScreenUtils
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class ScreenUtils {

	private ScreenUtils() {
	}

	/**
	 * getScreenWidthAndHeight
	 *
	 * @param context
	 *            context
	 * @return screen width and height
	 */
	public static int[] getScreenWidthAndHeight(Context context) {

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return new int[] { outMetrics.widthPixels, outMetrics.heightPixels };

	}

	/**
	 * getScreenWidth
	 *
	 * @param context
	 *            context
	 * @return screen width
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * getScreenHeight
	 *
	 * @param context
	 *            context
	 * @return screen height
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * getScreenHeightWithoutStatusBar
	 *
	 * @param context
	 *            context
	 * @return screen height without statusBar
	 */
	public static int getScreenHeightWithoutStatusBar(Context context) {
		return getScreenHeight(context) - getStatusHeight(context);
	}

	/**
	 * getStatusHeight
	 * 
	 * @param context
	 *            context
	 * @return status height
	 */
	public static int getStatusHeight(Context context) {
		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
		}
		return statusHeight;

	}

	/**
	 * snapShotWithStatusBar
	 * 
	 * @param activity
	 *            activity
	 * @return bitmap
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity) {

		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * snapShotWithoutStatusBar
	 *
	 * @param activity
	 *            activity
	 * @return bitmap
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}
}