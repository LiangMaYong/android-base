package com.liangmayong.base.widget.iconfont;

import android.content.Context;

/**
 * IconFont
 *
 * @author LiangMaYong
 * @version 1.0
 **/
public final class IconFont {


    private IconFont() {
    }

    public static final String PATH = "fonts/base_iconfont.ttf";

    public static void load(Context context) {
        FontTypeface.loadFont(context, PATH);
    }

    public static final FontValue base_icon_about = new FontValue(PATH, 0xe68d);
    public static final FontValue base_icon_delete = new FontValue(PATH, 0xe622);
    public static final FontValue base_icon_star = new FontValue(PATH, 0xe610);
    public static final FontValue base_icon_more = new FontValue(PATH, 0xe60c);
    public static final FontValue base_icon_notice = new FontValue(PATH, 0xe612);
    public static final FontValue base_icon_edit = new FontValue(PATH, 0xe615);
    public static final FontValue base_icon_message = new FontValue(PATH, 0xe60a);
    public static final FontValue base_icon_arrow = new FontValue(PATH, 0xe61a);
    public static final FontValue base_icon_unstar = new FontValue(PATH, 0xe611);
    public static final FontValue base_icon_upload = new FontValue(PATH, 0xe608);
    public static final FontValue base_icon_add = new FontValue(PATH, 0xe602);
    public static final FontValue base_icon_close = new FontValue(PATH, 0xe601);
    public static final FontValue base_icon_refresh = new FontValue(PATH, 0xe604);
    public static final FontValue base_icon_circle_error = new FontValue(PATH, 0xe635);
    public static final FontValue base_icon_share = new FontValue(PATH, 0xe609);
    public static final FontValue base_icon_circle_warning = new FontValue(PATH, 0xe64f);
    public static final FontValue base_icon_circle_help = new FontValue(PATH, 0xe6c5);
    public static final FontValue base_icon_my = new FontValue(PATH, 0xe603);
    public static final FontValue base_icon_camera = new FontValue(PATH, 0xe60f);
    public static final FontValue base_icon_copy = new FontValue(PATH, 0xe707);
    public static final FontValue base_icon_search = new FontValue(PATH, 0xe618);
    public static final FontValue base_icon_location = new FontValue(PATH, 0xe616);
    public static final FontValue base_icon_filtrate = new FontValue(PATH, 0xe60d);
    public static final FontValue base_icon_download = new FontValue(PATH, 0xe606);
    public static final FontValue base_icon_yes = new FontValue(PATH, 0xe617);
    public static final FontValue base_icon_full_heart = new FontValue(PATH, 0xe621);
    public static final FontValue base_icon_pay = new FontValue(PATH, 0xe619);
    public static final FontValue base_icon_circle_yes = new FontValue(PATH, 0xe61b);
    public static final FontValue base_icon_photo = new FontValue(PATH, 0xe657);
    public static final FontValue base_icon_setting = new FontValue(PATH, 0xe60b);
    public static final FontValue base_icon_password = new FontValue(PATH, 0xe613);
    public static final FontValue base_icon_switch = new FontValue(PATH, 0xe625);
    public static final FontValue base_icon_location_map = new FontValue(PATH, 0xe614);
    public static final FontValue base_icon_menu = new FontValue(PATH, 0xe605);
    public static final FontValue base_icon_back = new FontValue(PATH, 0xe600);
    public static final FontValue base_icon_hollow_heart = new FontValue(PATH, 0xe679);


}