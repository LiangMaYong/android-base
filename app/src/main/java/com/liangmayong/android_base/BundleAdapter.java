package com.liangmayong.android_base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liangmayong.apkbox.bundle.api.ApkBundleViewAdapter;
import com.liangmayong.apkbox.bundle.api.Listener;

/**
 * Created by LiangMaYong on 2017/5/15.
 */

public class BundleAdapter implements ApkBundleViewAdapter {

    @Override
    public View newView(Context context, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_text_view, parent, false);
    }

    @Override
    public void bindView(Context context, View view, Listener listener) {
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tv_txt.setText("test ----------- ");
        listener.show();
    }

    public class ViewHolder {
        public View rootView;
        public TextView tv_txt;
        public TextView tv_sub_txt;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_txt = (TextView) rootView.findViewById(R.id.tv_txt);
            this.tv_sub_txt = (TextView) rootView.findViewById(R.id.tv_sub_txt);
        }

    }
}
