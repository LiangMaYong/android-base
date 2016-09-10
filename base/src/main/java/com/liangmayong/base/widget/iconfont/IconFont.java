package com.liangmayong.base.widget.iconfont;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Icon font
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class IconFont {
    private static Map<String, Typeface> typefaceMap = new HashMap<String, Typeface>();

    /**
     * getFont
     *
     * @param context  context
     * @param fontPath fontPath
     * @return typeface
     */
    public static Typeface getFont(Context context, String fontPath) {
        loadFont(context, fontPath);
        if (typefaceMap.containsKey(fontPath)) {
            return typefaceMap.get(fontPath);
        }
        return null;
    }

    /**
     * loadFont
     *
     * @param context  context
     * @param fontPath fontPath
     */
    public static void loadFont(Context context, String fontPath) {
        if (typefaceMap.containsKey(fontPath)) {
            return;
        }
        try {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
            typefaceMap.put(fontPath, tf);
        } catch (Exception e) {
        }
    }
}
