package com.liangmayong.base.widget.superlistview.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liangmayong.base.R;
import com.liangmayong.base.widget.iconfont.IconValue;
import com.liangmayong.base.widget.iconfont.IconView;

/**
 * Created by LiangMaYong on 2016/11/7.
 */
public final class DefaultSuperItem extends SuperItem<DefaultSuperData> {

    public DefaultSuperItem(String title) {
        super(new DefaultSuperData(title, null));
    }

    public DefaultSuperItem(String title, String sub) {
        super(new DefaultSuperData(title, sub));
    }

    public DefaultSuperItem(String title, String sub, String r_title, String r_sub) {
        super(new DefaultSuperData(title, sub, r_title, r_sub));
    }

    @Override
    protected View onNewView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.base_default_item_superview, null);
    }

    private boolean showRightIcon = false;

    public boolean isShowRightIcon() {
        return showRightIcon;
    }

    public DefaultSuperItem showRightIcon(boolean showRightIcon) {
        this.showRightIcon = showRightIcon;
        return this;
    }

    /**
     * setIcon
     *
     * @param icon icon
     * @return icon
     */
    public DefaultSuperItem setIcon(IconValue icon) {
        getData().setIcon(icon);
        return this;
    }

    @Override
    protected void onBindView(View itemView, DefaultSuperData defaultSuperData) {
        ViewHolder holder = new ViewHolder(itemView);
        holder.base_default_adapter_title.setText(defaultSuperData.getTitle());
        if (defaultSuperData.getSub() != null) {
            holder.base_default_adapter_sub.setText(defaultSuperData.getSub());
            holder.base_default_adapter_sub.setVisibility(View.VISIBLE);
        } else {
            holder.base_default_adapter_sub.setVisibility(View.GONE);
        }
        if (defaultSuperData.getRight_sub() != null) {
            holder.base_default_adapter_right_sub.setText(defaultSuperData.getRight_sub());
            holder.base_default_adapter_right_sub.setVisibility(View.VISIBLE);
        } else {
            holder.base_default_adapter_right_sub.setVisibility(View.GONE);
        }
        if (defaultSuperData.getRight_title() != null) {
            holder.base_default_adapter_right_title.setText(defaultSuperData.getRight_title());
            holder.base_default_adapter_right_title.setVisibility(View.VISIBLE);
        } else {
            holder.base_default_adapter_right_title.setVisibility(View.GONE);
        }
        if (!isShowRightIcon()) {
            holder.base_default_adapter_arrow.setVisibility(View.GONE);
        } else {
            holder.base_default_adapter_arrow.setVisibility(View.VISIBLE);
            holder.base_default_adapter_arrow.setIcon("", defaultSuperData.getRight_icon(), IconView.ICON_LEFT);
        }
        if (getData().getIcon() != null) {
            holder.base_default_adapter_icon.setVisibility(View.VISIBLE);
            holder.base_default_adapter_icon.setIcon("", getData().getIcon(), IconView.ICON_LEFT);
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
            this.base_default_adapter_icon = (IconView) rootView.findViewById(R.id.base_default_adapter_icon);
            this.base_default_adapter_title = (TextView) rootView.findViewById(R.id.base_default_adapter_title);
            this.base_default_adapter_sub = (TextView) rootView.findViewById(R.id.base_default_adapter_sub);
            this.base_default_adapter_right_title = (TextView) rootView.findViewById(R.id.base_default_adapter_right_title);
            this.base_default_adapter_right_sub = (TextView) rootView.findViewById(R.id.base_default_adapter_right_sub);
            this.base_default_adapter_arrow = (IconView) rootView.findViewById(R.id.base_default_adapter_arrow);
            this.base_default_adapter_linear = (LinearLayout) rootView.findViewById(R.id.base_default_adapter_linear);
        }

    }
}
