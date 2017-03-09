package com.liangmayong.base.support.adapter.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by LiangMaYong on 2017/3/6.
 */
public class RecyclerLastVisible {
    /**
     * OnRecyclerBoxLastItemVisibleListener
     */
    public interface OnRecyclerViewLastItemVisibleListener {
        void onLastItemVisible(RecyclerView recyclerView, int dx, int dy);
    }

    public static void setLastVisibleListener(RecyclerView recyclerView, OnRecyclerViewLastItemVisibleListener lastItemVisibleListener) {
        new RecyclerLastVisible(recyclerView, lastItemVisibleListener);
    }

    // lastCount
    private int lastCount = 5;
    // lastDelay
    private long lastDelay = 500;
    // lastTime
    private long lastTime = 0;
    // isLast
    private boolean isLast = false;
    private final RecyclerView recyclerView;
    //errorRetryListener
    private final OnRecyclerViewLastItemVisibleListener lastItemVisibleListener;

    private RecyclerLastVisible(RecyclerView recyclerView, OnRecyclerViewLastItemVisibleListener lastItemVisibleListener) {
        this.recyclerView = recyclerView;
        this.lastItemVisibleListener = lastItemVisibleListener;
        this.recyclerView.addOnScrollListener(scrollListener);
    }

    public void setLastCount(int lastCount) {
        this.lastCount = lastCount;
    }

    public void setLastDelay(long lastDelay) {
        this.lastDelay = lastDelay;
    }

    //scrollListener
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            boolean flag = dy > 0;
            if (isReverseLayout(recyclerView) || isStackFromEnd(recyclerView)) {
                flag = dy < 0;
            }
            if (flag) {
                if (lastVisible(recyclerView, dy)) {
                    if (!isLast || (System.currentTimeMillis() - lastTime > lastDelay)) {
                        isLast = true;
                        lastTime = System.currentTimeMillis();
                        if (lastItemVisibleListener != null) {
                            lastItemVisibleListener.onLastItemVisible(recyclerView, dx, dy);
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
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    };

    /**
     * isReverseLayout
     *
     * @param recyclerView recyclerView
     * @return bool
     */
    private boolean isReverseLayout(RecyclerView recyclerView) {
        boolean reverseLayout = false;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            reverseLayout = ((LinearLayoutManager) recyclerView.getLayoutManager()).getReverseLayout();
        } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            reverseLayout = ((GridLayoutManager) recyclerView.getLayoutManager()).getReverseLayout();
        } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            reverseLayout = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).getReverseLayout();
        }
        return reverseLayout;
    }

    /**
     * isStackFromEnd
     *
     * @param recyclerView recyclerView
     * @return bool
     */
    private boolean isStackFromEnd(RecyclerView recyclerView) {
        boolean stackFromEnd = false;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            stackFromEnd = ((LinearLayoutManager) recyclerView.getLayoutManager()).getStackFromEnd();
        } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            stackFromEnd = ((GridLayoutManager) recyclerView.getLayoutManager()).getStackFromEnd();
        }
        return stackFromEnd;
    }

    /**
     * lastVisible
     *
     * @param dy dy
     * @return true or false
     */
    private boolean lastVisible(RecyclerView recyclerView, int dy) {
        int lastVisibleItem = getLastVisiblePosition(recyclerView);
        int totalItemCount = recyclerView.getLayoutManager().getItemCount() - 1;
        if (lastVisibleItem >= totalItemCount - lastCount) {
            return true;
        }
        return false;
    }


    /**
     * getLastVisiblePosition
     *
     * @return last visible position
     */
    private int getLastVisiblePosition(RecyclerView recyclerView) {
        int position;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = recyclerView.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * getFirstVisiblePosition
     *
     * @return frist visible position
     */
    private int getFirstVisiblePosition(RecyclerView recyclerView) {
        int position;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            int[] fristPositions = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMinPosition(fristPositions);
        } else {
            position = recyclerView.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * getMaxPosition
     *
     * @param positions positions
     * @return max position
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }

    /**
     * getMinPositions
     *
     * @param positions positions
     * @return min position
     */
    private int getMinPosition(int[] positions) {
        int size = positions.length;
        int minPosition = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            minPosition = Math.min(minPosition, positions[i]);
        }
        return minPosition;
    }
}
