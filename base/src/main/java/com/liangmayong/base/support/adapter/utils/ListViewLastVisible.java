package com.liangmayong.base.support.adapter.utils;

import android.widget.AbsListView;

/**
 * Created by LiangMaYong on 2017/3/6.
 */
public class ListViewLastVisible {
    /**
     * OnRecyclerBoxLastItemVisibleListener
     */
    public interface OnListViewLastItemVisibleListener {
        void onLastItemVisible(AbsListView listView);
    }

    public static void setLastVisibleListener(AbsListView listView, OnListViewLastItemVisibleListener lastItemVisibleListener) {
        new ListViewLastVisible(listView, lastItemVisibleListener);
    }

    // lastCount
    private int lastCount = 5;
    // lastDelay
    private long lastDelay = 500;
    // lastTime
    private long lastTime = 0;
    // isLast
    private boolean isLast = false;
    // listView
    private final AbsListView listView;
    //errorRetryListener
    private final OnListViewLastItemVisibleListener lastItemVisibleListener;

    private ListViewLastVisible(AbsListView listView, OnListViewLastItemVisibleListener lastItemVisibleListener) {
        this.listView = listView;
        this.lastItemVisibleListener = lastItemVisibleListener;
        this.listView.setOnScrollListener(scrollListener);
    }

    public void setLastCount(int lastCount) {
        this.lastCount = lastCount;
    }

    public void setLastDelay(long lastDelay) {
        this.lastDelay = lastDelay;
    }

    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                if (view.getLastVisiblePosition() == view.getCount() - lastCount) {
                    if (!isLast || (System.currentTimeMillis() - lastTime > lastDelay)) {
                        isLast = true;
                        lastTime = System.currentTimeMillis();
                        if (lastItemVisibleListener != null) {
                            lastItemVisibleListener.onLastItemVisible(view);
                        }
                    }
                } else {
                    if (isLast) {
                        isLast = false;
                    }
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
        }
    };
}
