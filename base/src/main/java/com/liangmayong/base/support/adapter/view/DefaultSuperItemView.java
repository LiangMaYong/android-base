package com.liangmayong.base.support.adapter.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liangmayong.base.R;
import com.liangmayong.base.support.adapter.SuperItemView;

/**
 * Created by LiangMaYong on 2017/3/1.
 */
public class DefaultSuperItemView extends SuperItemView<DefaultSuperItemView.Data> {

    public DefaultSuperItemView(Data superDefaultData) {
        super(superDefaultData);
    }

    @Override
    protected View newView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.base_default_super_item, parent, false);
    }

    @Override
    protected void bindView(View itemView, Data defaultData) {
        ViewHolder holder = new ViewHolder(itemView);
        holder.base_default_item_title.setText(defaultData.getTitle());
        if (defaultData.getSub() != null) {
            holder.base_default_item_sub.setText(defaultData.getSub());
            holder.base_default_item_sub.setVisibility(View.VISIBLE);
        } else {
            holder.base_default_item_sub.setVisibility(View.GONE);
        }
        if (defaultData.getContentSub() != null) {
            holder.base_default_item_right_sub.setText(defaultData.getContentSub());
            holder.base_default_item_right_sub.setVisibility(View.VISIBLE);
        } else {
            holder.base_default_item_right_sub.setVisibility(View.GONE);
        }
        if (defaultData.getContent() != null) {
            holder.base_default_item_right_title.setText(defaultData.getContent());
            holder.base_default_item_right_title.setVisibility(View.VISIBLE);
        } else {
            holder.base_default_item_right_title.setVisibility(View.GONE);
        }
        if (defaultData.isShowArrow()) {
            holder.base_default_item_arrow.setVisibility(View.GONE);
        } else {
            holder.base_default_item_arrow.setVisibility(View.VISIBLE);
        }
        if (getData().getIconRes() != 0) {
            holder.base_default_item_icon.setVisibility(View.VISIBLE);
            holder.base_default_item_icon.setImageResource(getData().getIconRes());
        } else {
            holder.base_default_item_icon.setVisibility(View.GONE);
        }
    }

    /**
     * ViewHolder
     */
    private static class ViewHolder {
        public View rootView;
        public ImageView base_default_item_icon;
        public TextView base_default_item_title;
        public TextView base_default_item_sub;
        public TextView base_default_item_right_title;
        public TextView base_default_item_right_sub;
        public ImageView base_default_item_arrow;
        public LinearLayout base_default_item_linear;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.base_default_item_icon = (ImageView) rootView.findViewById(R.id.base_default_item_icon);
            this.base_default_item_title = (TextView) rootView.findViewById(R.id.base_default_item_title);
            this.base_default_item_sub = (TextView) rootView.findViewById(R.id.base_default_item_sub);
            this.base_default_item_right_title = (TextView) rootView.findViewById(R.id.base_default_item_right_title);
            this.base_default_item_right_sub = (TextView) rootView.findViewById(R.id.base_default_item_right_sub);
            this.base_default_item_arrow = (ImageView) rootView.findViewById(R.id.base_default_item_arrow);
            this.base_default_item_linear = (LinearLayout) rootView.findViewById(R.id.base_default_item_linear);
        }
    }

    /**
     * Created by LiangMaYong on 2016/11/7.
     */
    public static final class Data {
        private String title;
        private String sub;
        private Object tag;
        private String content;
        private String contentSub;
        private boolean showArrow = false;
        private int iconRes = 0;

        public Data(String title) {
            this.title = title;
        }

        public Data(String title, String sub) {
            this.title = title;
            this.sub = sub;
        }

        public Data(String title, String sub, String content, String content_sub) {
            this.title = title;
            this.sub = sub;
            this.content = content;
            this.contentSub = content_sub;
        }

        public Data setTitle(String title) {
            this.title = title;
            return this;
        }

        public Data setSub(String sub) {
            this.sub = sub;
            return this;
        }

        public Data setContent(String content) {
            this.content = content;
            return this;
        }

        public Data setContentSub(String content_sub) {
            this.contentSub = content_sub;
            return this;
        }

        public Data setShowArrow(boolean showArrow) {
            this.showArrow = showArrow;
            return this;
        }

        public Data setIconRes(int iconRes) {
            this.iconRes = iconRes;
            return this;
        }

        public Data setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        public int getIconRes() {
            return iconRes;
        }

        public String getTitle() {
            return title;
        }

        public String getSub() {
            return sub;
        }

        public String getContent() {
            return content;
        }

        public String getContentSub() {
            return contentSub;
        }

        public boolean isShowArrow() {
            return showArrow;
        }

        public Object getTag() {
            return tag;
        }

        @Override
        public String toString() {
            return "DefaultSuperItemData{" +
                    "title='" + title + '\'' +
                    ", sub='" + sub + '\'' +
                    ", tag=" + tag +
                    ", content='" + content + '\'' +
                    ", contentSub='" + contentSub + '\'' +
                    ", showArrow=" + showArrow +
                    ", iconRes=" + iconRes +
                    '}';
        }
    }
}
