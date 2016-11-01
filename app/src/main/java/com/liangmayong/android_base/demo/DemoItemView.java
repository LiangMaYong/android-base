package com.liangmayong.android_base.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liangmayong.android_base.R;
import com.liangmayong.base.widget.superlistview.SuperListView;

/**
 * Created by LiangMaYong on 2016/9/25.
 */
public class DemoItemView extends SuperListView.Item<String> {

    public DemoItemView(String s) {
        super(s);
    }

    @Override
    protected View newView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.item_view, null);
    }

    @Override
    protected void bindView(View itemView, String s) {
        ViewHolder holder = new ViewHolder(itemView);
        holder.textView.setText(s);
    }

    public static class ViewHolder {
        public View rootView;
        public TextView textView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.textView = (TextView) rootView.findViewById(R.id.textView);
        }

    }
}
