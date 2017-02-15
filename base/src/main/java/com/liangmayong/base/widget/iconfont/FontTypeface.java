package com.liangmayong.base.widget.iconfont;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * FontTypeface
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class FontTypeface {

    private final static Map<String, Typeface> TYPEFACE_MAP = new HashMap<String, Typeface>();

    /**
     * getFont
     *
     * @param context  context
     * @param fontPath fontPath
     * @return typeface
     */
    public static Typeface getFont(Context context, String fontPath) {
        loadFont(context, fontPath);
        if (TYPEFACE_MAP.containsKey(fontPath)) {
            return TYPEFACE_MAP.get(fontPath);
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
        if (TYPEFACE_MAP.containsKey(fontPath)) {
            return;
        }
        try {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
            TYPEFACE_MAP.put(fontPath, tf);
        } catch (Exception e) {
        }
    }
}
