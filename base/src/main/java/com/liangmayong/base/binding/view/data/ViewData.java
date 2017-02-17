package com.liangmayong.base.binding.view.data;

/**
 * Created by LiangMaYong on 2017/2/16.
 */
public class ViewData {
    // BindTitle
    private String title;

    public String getTitle() {
        if (title == null) {
            return "";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
