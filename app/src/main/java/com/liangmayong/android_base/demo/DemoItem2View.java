package com.liangmayong.android_base.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liangmayong.android_base.R;
import com.liangmayong.base.widget.skin.SkinButton;
import com.liangmayong.base.widget.superlistview.SuperListView;

/**
 * Created by LiangMaYong on 2016/9/25.
 */
public class DemoItem2View extends SuperListView.Item<String> {

    public DemoItem2View(String s) {
        super(s);
    }

    @Override
    protected View newView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.item2_view, null);
    }

    @Override
    protected void bindView(View itemView, String s) {
        ViewHolder holder = new ViewHolder(itemView);
        holder.tv_txt.setText(s);
        if (getOnClickListener() != null) {
            holder.btn_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnClickListener().onClick(DemoItem2View.this, getPosition(), v);
                }
            });
        }
    }

    public static class ViewHolder {
        public View rootView;
        public SkinButton btn_show;
        public TextView tv_txt;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.btn_show = (SkinButton) rootView.findViewById(R.id.btn_show);
            this.tv_txt = (TextView) rootView.findViewById(R.id.tv_txt);
        }

    }
}
