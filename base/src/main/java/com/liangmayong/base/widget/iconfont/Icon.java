package com.liangmayong.base.widget.iconfont;

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

    public static final IconValue icon_about = new IconValue(FONT_PATH, 0xe607);
    public static final IconValue icon_delete = new IconValue(FONT_PATH, 0xe60e);
    public static final IconValue icon_star = new IconValue(FONT_PATH, 0xe610);
    public static final IconValue icon_more = new IconValue(FONT_PATH, 0xe60c);
    public static final IconValue icon_notice = new IconValue(FONT_PATH, 0xe612);
    public static final IconValue icon_edit = new IconValue(FONT_PATH, 0xe615);
    public static final IconValue icon_message = new IconValue(FONT_PATH, 0xe60a);
    public static final IconValue icon_arrow = new IconValue(FONT_PATH, 0xe61a);
    public static final IconValue icon_unstar = new IconValue(FONT_PATH, 0xe611);
    public static final IconValue icon_upload = new IconValue(FONT_PATH, 0xe608);
    public static final IconValue icon_add = new IconValue(FONT_PATH, 0xe602);
    public static final IconValue icon_close = new IconValue(FONT_PATH, 0xe601);
    public static final IconValue icon_refresh = new IconValue(FONT_PATH, 0xe604);
    public static final IconValue icon_share = new IconValue(FONT_PATH, 0xe609);
    public static final IconValue icon_my = new IconValue(FONT_PATH, 0xe603);
    public static final IconValue icon_camera = new IconValue(FONT_PATH, 0xe60f);
    public static final IconValue icon_copy = new IconValue(FONT_PATH, 0xe707);
    public static final IconValue icon_search = new IconValue(FONT_PATH, 0xe618);
    public static final IconValue icon_location = new IconValue(FONT_PATH, 0xe616);
    public static final IconValue icon_filtrate = new IconValue(FONT_PATH, 0xe60d);
    public static final IconValue icon_download = new IconValue(FONT_PATH, 0xe606);
    public static final IconValue icon_yes = new IconValue(FONT_PATH, 0xe617);
    public static final IconValue icon_full_heart = new IconValue(FONT_PATH, 0xe621);
    public static final IconValue icon_pay = new IconValue(FONT_PATH, 0xe619);
    public static final IconValue icon_photo = new IconValue(FONT_PATH, 0xe657);
    public static final IconValue icon_setting = new IconValue(FONT_PATH, 0xe60b);
    public static final IconValue icon_password = new IconValue(FONT_PATH, 0xe613);
    public static final IconValue icon_switch = new IconValue(FONT_PATH, 0xe625);
    public static final IconValue icon_location_map = new IconValue(FONT_PATH, 0xe614);
    public static final IconValue icon_menu = new IconValue(FONT_PATH, 0xe605);
    public static final IconValue icon_back = new IconValue(FONT_PATH, 0xe600);
    public static final IconValue icon_hollow_heart = new IconValue(FONT_PATH, 0xe679);

}