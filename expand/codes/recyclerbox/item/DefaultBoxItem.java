package com.liangmayong.base.widget.recyclerbox.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liangmayong.base.R;
import com.liangmayong.base.widget.iconfont.FontValue;
import com.liangmayong.base.widget.iconfont.IconView;

/**
 * Created by LiangMaYong on 2016/11/7.
 */
public final class DefaultBoxItem extends RecyclerBoxItem<DefaultBoxData> {

    public DefaultBoxItem(String title) {
        super(new DefaultBoxData(title, null));
    }

    public DefaultBoxItem(String title, String sub) {
        super(new DefaultBoxData(title, sub));
    }

    public DefaultBoxItem(String title, String sub, String content, String contentSub) {
        super(new DefaultBoxData(title, sub, content, contentSub));
    }

    @Override
    protected View onNewView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.base_default_super_item, null);
    }

    public DefaultBoxItem setShowArrow(boolean showArrow) {
        getData().setShowArrow(showArrow);
        return this;
    }

    /**
     * setIcon
     *
     * @param icon icon
     * @return icon
     */
    public DefaultBoxItem setIcon(FontValue icon) {
        getData().setIcon(icon);
        return this;
    }

    @Override
    protected void onBindView(View itemView, DefaultBoxData defaultSuperData) {
        ViewHolder holder = new ViewHolder(itemView);
        holder.base_default_adapter_title.setText(defaultSuperData.getTitle());
        if (defaultSuperData.getSub() != null) {
            holder.base_default_adapter_sub.setText(defaultSuperData.getSub());
            holder.base_default_adapter_sub.setVisibility(View.VISIBLE);
        } else {
            holder.base_default_adapter_sub.setVisibility(View.GONE);
        }
        if (defaultSuperData.getContentSub() != null) {
            holder.base_default_adapter_right_sub.setText(defaultSuperData.getContentSub());
            holder.base_default_adapter_right_sub.setVisibility(View.VISIBLE);
        } else {
            holder.base_default_adapter_right_sub.setVisibility(View.GONE);
        }
        if (defaultSuperData.getContent() != null) {
            holder.base_default_adapter_right_title.setText(defaultSuperData.getContent());
            holder.base_default_adapter_right_title.setVisibility(View.VISIBLE);
        } else {
            holder.base_default_adapter_right_title.setVisibility(View.GONE);
        }
        if (defaultSuperData.isShowArrow()) {
            holder.base_default_adapter_arrow.setVisibility(View.GONE);
        } else {
            holder.base_default_adapter_arrow.setVisibility(View.VISIBLE);
        }
        if (getData().getIcon() != null) {
            holder.base_default_adapter_icon.setVisibility(View.VISIBLE);
            holder.base_default_adapter_icon.setIcon(getData().getIcon());
        } else {
            holder.base_default_adapter_icon.setVisibility(View.GONE);
        }
    }

    public static class ViewHolder {
        public View rootView;
        public IconView base_default_adapter_icon;
        public TextView base_default_adapter_title;
        public TextView base_default_adapter_sub;
        public TextView base_default_adapter_right_title;
        public TextView base_default_adapter_right_sub;
        public IconView base_default_adapter_arrow;
        public LinearLayout base_default_adapter_linear;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.base_default_adapter_icon = (IconView) rootView.findViewById(R.id.base_default_item_icon);
            this.base_default_adapter_title = (TextView) rootView.findViewById(R.id.base_default_item_title);
            this.base_default_adapter_sub = (TextView) rootView.findViewById(R.id.base_default_item_sub);
            this.base_default_adapter_right_title = (TextView) rootView.findViewById(R.id.base_default_item_right_title);
            this.base_default_adapter_right_sub = (TextView) rootView.findViewById(R.id.base_default_item_right_sub);
            this.base_default_adapter_arrow = (IconView) rootView.findViewById(R.id.base_default_adapter_arrow);
            this.base_default_adapter_linear = (LinearLayout) rootView.findViewById(R.id.base_default_item_linear);
        }

    }
}
