package com.liangmayong.base.binding.view.data;

import android.view.View;

/**
 * Created by LiangMaYong on 2017/2/16.
 */
public class ViewBindingData {
    // BindTitle
    private String title;
    // view
    private View view;

    public String getTitle() {
        if (title == null) {
            return "";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }
}
