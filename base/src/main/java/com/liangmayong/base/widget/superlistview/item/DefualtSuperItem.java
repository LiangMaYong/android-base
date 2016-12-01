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
public final class DefualtSuperItem extends SuperItem<DefualtSuperData> {

    public DefualtSuperItem(String title) {
        super(new DefualtSuperData(title, null));
    }

    public DefualtSuperItem(String title, String sub) {
        super(new DefualtSuperData(title, sub));
    }

    public DefualtSuperItem(String title, String sub, String r_title, String r_sub) {
        super(new DefualtSuperData(title, sub, r_title, r_sub));
    }

    @Override
    protected View onNewView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.base_defualt_item_superview, null);
    }

    private boolean showRightIcon = false;

    public boolean isShowRightIcon() {
        return showRightIcon;
    }

    public DefualtSuperItem showRightIcon(boolean showRightIcon) {
        this.showRightIcon = showRightIcon;
        return this;
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
    protected void onBindView(View itemView, DefualtSuperData defualtSuperData) {
        ViewHolder holder = new ViewHolder(itemView);
        holder.base_defualt_adapter_title.setText(defualtSuperData.getTitle());
        if (defualtSuperData.getSub() != null) {
            holder.base_defualt_adapter_sub.setText(defualtSuperData.getSub());
            holder.base_defualt_adapter_sub.setVisibility(View.VISIBLE);
        } else {
            holder.base_defualt_adapter_sub.setVisibility(View.GONE);
        }
        if (defualtSuperData.getRight_sub() != null) {
            holder.base_defualt_adapter_right_sub.setText(defualtSuperData.getRight_sub());
            holder.base_defualt_adapter_right_sub.setVisibility(View.VISIBLE);
        } else {
            holder.base_defualt_adapter_right_sub.setVisibility(View.GONE);
        }
        if (defualtSuperData.getRight_title() != null) {
            holder.base_defualt_adapter_right_title.setText(defualtSuperData.getRight_title());
            holder.base_defualt_adapter_right_title.setVisibility(View.VISIBLE);
        } else {
            holder.base_defualt_adapter_right_title.setVisibility(View.GONE);
        }
        if (!isShowRightIcon()) {
            holder.base_defualt_adapter_arrow.setVisibility(View.GONE);
        } else {
            holder.base_defualt_adapter_arrow.setVisibility(View.VISIBLE);
            holder.base_defualt_adapter_arrow.setIcon("", defualtSuperData.getRight_icon(), IconView.ICON_LEFT);
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
        public TextView base_defualt_adapter_right_title;
        public TextView base_defualt_adapter_right_sub;
        public IconView base_defualt_adapter_arrow;
        public LinearLayout base_defualt_adapter_linear;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.base_defualt_adapter_icon = (IconView) rootView.findViewById(R.id.base_defualt_adapter_icon);
            this.base_defualt_adapter_title = (TextView) rootView.findViewById(R.id.base_defualt_adapter_title);
            this.base_defualt_adapter_sub = (TextView) rootView.findViewById(R.id.base_defualt_adapter_sub);
            this.base_defualt_adapter_right_title = (TextView) rootView.findViewById(R.id.base_defualt_adapter_right_title);
            this.base_defualt_adapter_right_sub = (TextView) rootView.findViewById(R.id.base_defualt_adapter_right_sub);
            this.base_defualt_adapter_arrow = (IconView) rootView.findViewById(R.id.base_defualt_adapter_arrow);
            this.base_defualt_adapter_linear = (LinearLayout) rootView.findViewById(R.id.base_defualt_adapter_linear);
        }

    }
}
