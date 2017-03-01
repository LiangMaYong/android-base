package com.liangmayong.base.widget.recyclerbox.item;

import com.liangmayong.base.widget.iconfont.FontValue;

/**
 * Created by LiangMaYong on 2016/11/7.
 */

public final class DefaultBoxData {
    private String title;
    private String sub;
    private Object tag;
    private FontValue icon;
    private String content;
    private String contentSub;
    private boolean showArrow = false;

    public void setIcon(FontValue icon) {
        this.icon = icon;
    }

    public FontValue getIcon() {
        return icon;
    }

    public DefaultBoxData(String title, String sub) {
        this.title = title;
        this.sub = sub;
    }

    public DefaultBoxData(String title, String sub, String content, String content_sub) {
        this.title = title;
        this.sub = sub;
        this.content = content;
        this.contentSub = content_sub;
    }

    public DefaultBoxData setTitle(String title) {
        this.title = title;
        return this;
    }

    public DefaultBoxData setSub(String sub) {
        this.sub = sub;
        return this;
    }

    public DefaultBoxData setContent(String content) {
        this.content = content;
        return this;
    }

    public DefaultBoxData setContentSub(String content_sub) {
        this.contentSub = content_sub;
        return this;
    }

    public DefaultBoxData setShowArrow(boolean showArrow) {
        this.showArrow = showArrow;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getSub() {
        return sub;
    }

    public String getContent() {
        return content;
    }

    public String getContentSub() {
        return contentSub;
    }

    public boolean isShowArrow() {
        return showArrow;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "DefaultBoxData{" +
                "title='" + title + '\'' +
                ", sub='" + sub + '\'' +
                ", tag=" + tag +
                ", content='" + content + '\'' +
                ", contentSub='" + contentSub + '\'' +
                '}';
    }
}
