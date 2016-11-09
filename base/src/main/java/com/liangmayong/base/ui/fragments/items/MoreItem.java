package com.liangmayong.base.ui.fragments.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangmayong.base.R;
import com.liangmayong.base.widget.iconfont.IconValue;
import com.liangmayong.base.widget.iconfont.IconView;
import com.liangmayong.base.widget.superlistview.SuperListView;

/**
 * Created by LiangMaYong on 2016/9/25.
 */
public class MoreItem extends SuperListView.Item<IconValue> {

    public MoreItem(IconValue s) {
        super(s);
    }

    @Override
    protected View newView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.base_defualt_item_more, null);
    }

    @Override
    protected void bindView(View itemView, IconValue iconValue) {
        ViewHolder holder = new ViewHolder(itemView);
        holder.base_more_title.setIcon("", iconValue, IconView.ICON_LEFT);
    }

    public static class ViewHolder {
        public View rootView;
        public IconView base_more_title;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.base_more_title = (IconView) rootView.findViewById(R.id.base_more_title);
        }

    }
}
