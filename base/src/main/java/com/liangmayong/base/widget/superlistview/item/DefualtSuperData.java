package com.liangmayong.base.widget.superlistview.item;

import com.liangmayong.base.widget.iconfont.IconValue;

/**
 * Created by LiangMaYong on 2016/11/7.
 */

public final class DefualtSuperData {
    private String title;
    private String sub;
    private Object tag;
    private IconValue icon;

    public void setIcon(IconValue icon) {
        this.icon = icon;
    }

    public IconValue getIcon() {
        return icon;
    }

    public DefualtSuperData(String title, String sub) {
        this.title = title;
        this.sub = sub;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
