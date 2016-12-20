package com.liangmayong.base.web.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangmayong.base.R;
import com.liangmayong.base.widget.iconfont.IconValue;
import com.liangmayong.base.widget.iconfont.IconView;
import com.liangmayong.base.widget.skinview.SkinIconView;
import com.liangmayong.base.widget.skinview.SkinTextView;
import com.liangmayong.base.widget.recyclerbox.RecyclerBox;

/**
 * Created by LiangMaYong on 2016/9/25.
 */
public class MoreItem extends RecyclerBox.Item<MoreItem.MoreData> {

    public MoreItem(MoreData s) {
        super(s);
    }

    public static class MoreData {
        private IconValue icon;
        private String txt;

        public MoreData(IconValue icon, String txt) {
            this.icon = icon;
            this.txt = txt;
        }

        public IconValue getIcon() {
            return icon;
        }

        public void setIcon(IconValue icon) {
            this.icon = icon;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }

    @Override
    protected View newView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.base_default_item_more, parent, false);
    }

    @Override
    protected void bindView(View itemView, MoreData data) {
        ViewHolder holder = new ViewHolder(itemView);
        holder.base_more_icon.setIcon("", data.getIcon(), IconView.ICON_LEFT);
        holder.base_more_title.setText(data.getTxt());
    }

    private class ViewHolder {
        public View rootView;
        public SkinIconView base_more_icon;
        public SkinTextView base_more_title;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.base_more_icon = (SkinIconView) rootView.findViewById(R.id.base_more_icon);
            this.base_more_title = (SkinTextView) rootView.findViewById(R.id.base_more_title);
        }

    }
}
