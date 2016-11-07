package com.liangmayong.base.widget.superlistview.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liangmayong.base.R;
import com.liangmayong.base.widget.iconfont.Icon;
import com.liangmayong.base.widget.iconfont.IconValue;
import com.liangmayong.base.widget.iconfont.IconView;
import com.liangmayong.base.widget.skin.SkinRippleLinearLayout;
import com.liangmayong.base.widget.superlistview.SuperListView;

/**
 * Created by LiangMaYong on 2016/11/7.
 */
public final class DefualtSuperItem extends SuperListView.Item<DefualtSuperData> {

    public DefualtSuperItem(String title) {
        super(new DefualtSuperData(title, null));
    }

    public DefualtSuperItem(String title, String sub) {
        super(new DefualtSuperData(title, sub));
    }

    @Override
    protected View newView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.base_defualt_adapter_view, null);
    }

    /**
     * setIcon
     *
     * @param icon icon
     * @return icon
     */
    public DefualtSuperItem setIcon(IconValue icon) {
        getData().setIcon(icon);
        return this;
    }

    @Override
    protected void bindView(View itemView, DefualtSuperData defualtData) {
        ViewHolder holder = new ViewHolder(itemView);
        holder.base_defualt_adapter_title.setText(defualtData.getTitle());
        if (defualtData.getSub() != null) {
            holder.base_defualt_adapter_sub.setText(defualtData.getSub());
            holder.base_defualt_adapter_sub.setVisibility(View.VISIBLE);
        } else {
            holder.base_defualt_adapter_sub.setVisibility(View.GONE);
        }
        if (getOnItemClickListener() == null || !isClickable()) {
            holder.base_defualt_adapter_arrow.setVisibility(View.GONE);
            holder.base_defualt_adapter_ripple.setClickable(false);
        } else {
            holder.base_defualt_adapter_arrow.setVisibility(View.VISIBLE);
            holder.base_defualt_adapter_arrow.setIcon("", Icon.icon_arrow, IconView.ICON_LEFT);
            holder.base_defualt_adapter_ripple.setClickable(true);
        }
        if (getData().getIcon() != null) {
            holder.base_defualt_adapter_icon.setVisibility(View.VISIBLE);
            holder.base_defualt_adapter_icon.setIcon("", getData().getIcon(), IconView.ICON_LEFT);
        } else {
            holder.base_defualt_adapter_icon.setVisibility(View.GONE);
        }
    }

    public static class ViewHolder {
        public View rootView;
        public IconView base_defualt_adapter_icon;
        public TextView base_defualt_adapter_title;
        public TextView base_defualt_adapter_sub;
        public IconView base_defualt_adapter_arrow;
        public SkinRippleLinearLayout base_defualt_adapter_ripple;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.base_defualt_adapter_icon = (IconView) rootView.findViewById(R.id.base_defualt_adapter_icon);
            this.base_defualt_adapter_title = (TextView) rootView.findViewById(R.id.base_defualt_adapter_title);
            this.base_defualt_adapter_sub = (TextView) rootView.findViewById(R.id.base_defualt_adapter_sub);
            this.base_defualt_adapter_arrow = (IconView) rootView.findViewById(R.id.base_defualt_adapter_arrow);
            this.base_defualt_adapter_ripple = (SkinRippleLinearLayout) rootView.findViewById(R.id.base_defualt_adapter_ripple);
        }

    }
}
