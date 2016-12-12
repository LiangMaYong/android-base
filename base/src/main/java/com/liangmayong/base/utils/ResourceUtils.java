package com.liangmayong.base.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * ResourceUtils
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public final class ResourceUtils {
	private ResourceUtils(Context context) {
	}

	/**
	 * According to the names of the resources to obtain its ID
	 * 
	 * 
	 * @param name
	 *            The name of the resource
	 * @param defType
	 *            The name of the resource,such as: drawable,string...
	 * @return resource id
	 */
	public static int getResourceId(Context context, String name, String defType) {
		String packageName = context.getPackageName();
		return context.getResources().getIdentifier(name, defType, packageName);
	}

	/**
	 * get resources ID
	 * 
	 * @param name
	 *            The name of the resource
	 * @param defType
	 *            The name of the resource,such as: drawable,string...
	 * @param packageName
	 *            packageName
	 * @return resource id
	 */
	public static int getResourceId(Context context, String name, String defType, String packageName) {
		return context.getResources().getIdentifier(name, defType, packageName);
	}

	/**
	 * get string ID
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @return string id
	 */
	public static int getStringId(Context context, String name) {
		return getResourceId(context, name, "string");
	}

	/**
	 * get string ID
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @param packageName
	 *            packageName
	 * @return string id
	 */
	public static int getStringId(Context context, String name, String packageName) {
		return getResourceId(context, name, "string", packageName);
	}

	/**
	 * get string
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @return string
	 */
	public static String getString(Context context, String name) {
		return context.getString(getStringId(context, name));
	}

	/**
	 * get string
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @param packageName
	 *            packageName
	 * @return string
	 */
	public static String getString(Context context, String name, String packageName) {
		return context.getString(getStringId(context, name, packageName));
	}

	/**
	 * get color ID
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @return color id
	 */
	public static int getColorId(Context context, String name) {
		return getResourceId(context, name, "color");
	}

	/**
	 * get color ID
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @param packageName
	 *            packageName
	 * @return color id
	 */
	public static int getColorId(Context context, String name, String packageName) {
		return getResourceId(context, name, "color", packageName);
	}

	/**
	 * get color
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @return color
	 */
	public static int getColor(Context context, String name) {
		return context.getResources().getColor(getColorId(context, name));
	}

	/**
	 * get color
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @param packageName
	 *            packageName
	 * @return color
	 */
	public static int getColor(Context context, String name, String packageName) {
		return context.getResources().getColor(getColorId(context, name, packageName));
	}

	/**
	 * get drawable ID
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @return drawable id
	 */
	public static int getDrawableId(Context context, String name) {
		return getResourceId(context, name, "drawable");
	}

	/**
	 * get drawable ID
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @param packageName
	 *            packageName
	 * @return drawable id
	 */
	public static int getDrawableId(Context context, String name, String packageName) {
		return getResourceId(context, name, "drawable", packageName);
	}

	/**
	 * get drawable
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @return drawable
	 */
	public static Drawable getDrawable(Context context, String name) {
		return context.getResources().getDrawable(getDrawableId(context, name));
	}

	/**
	 * get drawable
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @param packageName
	 *            packageName
	 * @return drawable
	 */
	public static Drawable getDrawable(Context context, String name, String packageName) {
		return context.getResources().getDrawable(getDrawableId(context, name, packageName));
	}

	/**
	 * get dimen ID
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @return dimen id
	 */
	public static int getDimenId(Context context, String name) {
		return getResourceId(context, name, "dimen");
	}

	/**
	 * get dimen ID
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @param packageName
	 *            packageName
	 * @return dimen id
	 */
	public static int getDimenId(Context context, String name, String packageName) {
		return getResourceId(context, name, "dimen", packageName);
	}

	/**
	 * get Dimension
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @return Dimension
	 */
	public static float getDimension(Context context, String name) {
		return context.getResources().getDimension(getDimenId(context, name));
	}

	/**
	 * get Dimension
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @param packageName
	 *            packageName
	 * @return Dimension
	 */
	public static float getDimension(Context context, String name, String packageName) {
		return context.getResources().getDimension(getDimenId(context, name, packageName));
	}

	/**
	 * get DimensionPixelSize
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @return DimensionPixelSize
	 */
	public static int getDimensionPixelSize(Context context, String name) {
		return context.getResources().getDimensionPixelSize(getDimenId(context, name));
	}

	/**
	 * get DimensionPixelSize
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @param packageName
	 *            packageName
	 * @return DimensionPixelSize
	 */
	public static int getDimensionPixelSize(Context context, String name, String packageName) {
		return context.getResources().getDimensionPixelSize(getDimenId(context, name, packageName));
	}

	/**
	 * get Animation ID
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @return Animation id
	 */
	public static int getAnimationId(Context context, String name) {
		return getResourceId(context, name, "anim");
	}

	/**
	 * get Animation ID
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @param packageName
	 *            packageName
	 * @return Animation id
	 */
	public static int getAnimationId(Context context, String name, String packageName) {
		return getResourceId(context, name, "anim", packageName);
	}

	/**
	 * get Animation
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @return Animation
	 */
	public static Animation getAnimation(Context context, String name) {
		return AnimationUtils.loadAnimation(context, getAnimationId(context, name));
	}

	/**
	 * get Animation
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            The name of the resource
	 * @param packageName
	 *            packageName
	 * @return Animation
	 */
	public static Animation getAnimation(Context context, String name, String packageName) {
		return AnimationUtils.loadAnimation(context, getAnimationId(context, name, packageName));
	}
}