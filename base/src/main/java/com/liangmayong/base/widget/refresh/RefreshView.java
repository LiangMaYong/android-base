package com.liangmayong.base.widget.refresh;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LiangMaYong on 2016/12/7.
 */
public interface RefreshView {

    void setChildView(ViewGroup view);

    void removeAllViews();

    void addView(View view);

    void setRefreshing(boolean refreshing);

    void setEnabled(boolean enabled);

    void setOnRefreshListener(OnRefreshListener listener);

    interface OnRefreshListener {
        void onRefresh();
    }
}
