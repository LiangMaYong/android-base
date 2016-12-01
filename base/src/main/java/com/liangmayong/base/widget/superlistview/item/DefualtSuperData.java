package com.liangmayong.base.widget.superlistview.item;

import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.iconfont.IconValue;

/**
 * Created by LiangMaYong on 2016/11/7.
 */

public final class DefualtSuperData {
    private String title;
    private String sub;
    private Object tag;
    private IconValue icon;
    private IconValue right_icon = Icon.icon_arrow;
    private String right_title;
    private String right_sub;

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

    public DefualtSuperData(String title, String sub, String right_title, String right_sub) {
        this.title = title;
        this.sub = sub;
        this.right_title = right_title;
        this.right_sub = right_sub;
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

    public IconValue getRight_icon() {
        return right_icon;
    }

    public void setRight_icon(IconValue right_icon) {
        this.right_icon = right_icon;
    }

    public String getRight_title() {
        return right_title;
    }

    public void setRight_title(String right_title) {
        this.right_title = right_title;
    }

    public String getRight_sub() {
        return right_sub;
    }

    public void setRight_sub(String right_sub) {
        this.right_sub = right_sub;
    }

    @Override
    public String toString() {
        return "DefualtSuperData{" +
                "title='" + title + '\'' +
                ", sub='" + sub + '\'' +
                ", tag=" + tag +
                ", icon=" + icon +
                '}';
    }
}
