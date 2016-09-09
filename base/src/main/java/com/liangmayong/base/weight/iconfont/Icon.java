package com.liangmayong.base.weight.iconfont;

import android.content.Context;

/**
 * Icon
 *
 * @author LiangMaYong
 * @version 1.0
 **/
public final class Icon {

    private Icon() {
    }


    private static final String FONT_PATH = "fonts/iconfont.ttf";

    public static void loadFont(Context context) {
        IconFont.loadFont(context, FONT_PATH);
    }

    public static final IconValue icon_upload = new IconValue(FONT_PATH, 0xe608);
    public static final IconValue icon_about = new IconValue(FONT_PATH, 0xe607);
    public static final IconValue icon_download = new IconValue(FONT_PATH, 0xe606);
    public static final IconValue icon_menu = new IconValue(FONT_PATH, 0xe605);
    public static final IconValue icon_refresh = new IconValue(FONT_PATH, 0xe604);
    public static final IconValue icon_my = new IconValue(FONT_PATH, 0xe603);
    public static final IconValue icon_add = new IconValue(FONT_PATH, 0xe602);
    public static final IconValue icon_close = new IconValue(FONT_PATH, 0xe601);
    public static final IconValue icon_back = new IconValue(FONT_PATH, 0xe600);

}