package com.liangmayong.base.widget.recyclerbox;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liangmayong.base.R;
import com.liangmayong.base.utils.Md5Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangMaYong on 2016/8/24.
 */
public class RecyclerBox extends RelativeLayout {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////   Listener   ////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * OnRecyclerBoxListViewRetryListener
     */
    public interface OnRecyclerBoxListViewRetryListener {
        void setRetryView(View retryView);
    }

    /**
     * OnRecyclerBoxItemClickListener
     *
     * @param <Data> data type
     */
    public interface OnRecyclerBoxItemClickListener<Data> {
        void onClick(Item<Data> item, int position, View itemView);
    }

    /**
     * OnRecyclerBoxItemLongClickListener
     *
     * @param <Data> data type
     */
    public interface OnRecyclerBoxItemLongClickListener<Data> {
        boolean onLongClick(Item<Data> item, int position, View itemView);
    }

    /**
     * OnRecyclerBoxLastItemVisibleListener
     */
    public static interface OnRecyclerBoxLastItemVisibleListener {
        void onLastItemVisible(RecyclerView recyclerView, int dx, int dy);
    }

    /**
     * OnRecyclerBoxLastItemVisibleListener
     */
    public interface OnRecyclerBoxItemMoveVisibleListener {
        void onItemMoveVisible(RecyclerView recyclerView, int fristPosition, int lastPosition);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////   RecyclerBox   /////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    // HORIZONTAL
    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    // VERTICAL
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;
    // pool
    private Pool pool = null;
    // itemDecoration
    private RecyclerView.ItemDecoration itemDecoration = null;
    // isLast
    private boolean isLast = false;
    // lastItemVisibleListener
    private OnRecyclerBoxLastItemVisibleListener lastItemVisibleListener = null;
    //itemMoveVisibleListener
    private OnRecyclerBoxItemMoveVisibleListener itemMoveVisibleListener = null;
    // lastCount
    private int lastCount = 5;
    // lastDelay
    private long lastDelay = 500;
    // lastTime
    private long lastTime = 0;
    // contentLayout
    private LinearLayout contentLayout = null;
    // headLayout
    private LinearLayout headLayout = null;
    // footLayout
    private LinearLayout footLayout = null;
    // emptyLayout
    private RelativeLayout emptyLayout = null;
    // errorLayout
    private RelativeLayout errorLayout = null;
    // loadingLayout
    private RelativeLayout loadingLayout = null;
    // emptyView
    private View emptyView = null;
    // errorView
    private View errorView = null;
    // loadingView
    private View loadingView = null;
    // is empty
    private boolean isEmpty = false;
    // is error
    private boolean isError = false;
    // is loading
    private boolean isLoading = false;
    // is content
    private boolean isContent = true;
    // currentPosition
    private int currentPosition = 0;
    // currentLastPosition
    private int currentLastPosition = 0;
    // columnCount
    private int columnCount = 1;
    // staggeredEnable
    private boolean staggeredEnable = true;
    //recyclerBox
    private ProxyRecyclerView recyclerView;
    //orientation
    private int orientation = VERTICAL;
    //stackFromEnd
    private boolean stackFromEnd = false;
    //reverseLayout
    private boolean reverseLayout = false;
    //errorRetryListener
    private OnRecyclerBoxListViewRetryListener errorRetryListener;
    //emptyRetryListener
    private OnRecyclerBoxListViewRetryListener emptyRetryListener;
    //loadingRetryListener
    private OnRecyclerBoxListViewRetryListener loadingRetryListener;
    //layoutTouchListener
    private OnTouchListener layoutTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    };

    /**
     * setStackFromEnd
     *
     * @param stackFromEnd stackFromEnd
     */
    public void setStackFromEnd(boolean stackFromEnd) {
        this.stackFromEnd = stackFromEnd;
        if (this.stackFromEnd) {
            this.reverseLayout = false;
        }
        if (recyclerView != null) {
            setLayoutManager(recyclerView.getLayoutManager());
        }
    }

    /**
     * setStackFromEnd
     *
     * @param reverseLayout reverseLayout
     */
    public void setReverseLayout(boolean reverseLayout) {
        this.reverseLayout = reverseLayout;
        if (this.reverseLayout) {
            this.stackFromEnd = false;
        }
        if (recyclerView != null) {
            setLayoutManager(recyclerView.getLayoutManager());
        }
    }

    /**
     * isStackFromEnd
     *
     * @return stackFromEnd
     */
    public boolean isStackFromEnd() {
        return stackFromEnd;
    }

    /**
     * isReverseLayout
     *
     * @return reverseLayout
     */
    public boolean isReverseLayout() {
        return reverseLayout;
    }

    /**
     * getLoadingView
     *
     * @return loadingView
     */
    public View getLoadingView() {
        return loadingView;
    }

    /**
     * getEmptyView
     *
     * @return emptyView
     */
    public View getEmptyView() {
        return emptyView;
    }

    /**
     * getErrorView
     *
     * @return errorView
     */
    public View getErrorView() {
        return errorView;
    }

    /**
     * setEmptyLayout
     *
     * @param resLayoutId        resLayoutId
     * @param emptyRetryListener emptyRetryListener
     */
    public void setEmptyLayout(int resLayoutId, OnRecyclerBoxListViewRetryListener emptyRetryListener) {
        this.emptyRetryListener = emptyRetryListener;
        if (emptyLayout != null) {
            emptyLayout.removeAllViews();
            emptyView = LayoutInflater.from(getContext()).inflate(resLayoutId, null);
            emptyView.setOnTouchListener(layoutTouchListener);
            emptyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (this.emptyRetryListener != null) {
                this.emptyRetryListener.setRetryView(emptyView);
            }
            emptyLayout.addView(emptyView);
        }
    }

    /**
     * setErrorLayout
     *
     * @param resLayoutId        resLayoutId
     * @param errorRetryListener errorRetryListener
     */
    public void setErrorLayout(int resLayoutId, OnRecyclerBoxListViewRetryListener errorRetryListener) {
        this.errorRetryListener = errorRetryListener;
        if (errorLayout != null) {
            errorLayout.removeAllViews();
            errorView = LayoutInflater.from(getContext()).inflate(resLayoutId, null);
            errorView.setOnTouchListener(layoutTouchListener);
            errorView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (this.errorRetryListener != null) {
                this.errorRetryListener.setRetryView(errorView);
            }
            errorLayout.addView(errorView);
        }
    }

    /**
     * setErrorLayout
     *
     * @param resLayoutId          resLayoutId
     * @param loadingRetryListener loadingRetryListener
     */
    public void setLoadingLayout(int resLayoutId, OnRecyclerBoxListViewRetryListener loadingRetryListener) {
        this.loadingRetryListener = loadingRetryListener;
        if (loadingLayout != null) {
            loadingLayout.removeAllViews();
            loadingView = LayoutInflater.from(getContext()).inflate(resLayoutId, null);
            loadingView.setOnTouchListener(layoutTouchListener);
            loadingView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (this.loadingRetryListener != null) {
                this.loadingRetryListener.setRetryView(loadingView);
            }
            loadingLayout.addView(loadingView);
        }
    }

    /**
     * recyclerView
     *
     * @return recyclerView
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * setDefaultEmpty
     *
     * @param text       text
     * @param imageResId imageResId
     */
    public void setDefaultEmpty(String text, int imageResId) {
        if (emptyLayout != null) {
            emptyLayout.removeAllViews();
            emptyView = LayoutInflater.from(getContext()).inflate(R.layout.base_default_retry_layout, null);
            emptyView.setOnTouchListener(layoutTouchListener);
            emptyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (text != null) {
                ((TextView) emptyView.findViewById(R.id.base_default_retry_text)).setText(text);
            }
            if (imageResId != 0) {
                ((ImageView) emptyView.findViewById(R.id.base_default_retry_img)).setImageResource(imageResId);
            } else {
                (emptyView.findViewById(R.id.base_default_retry_img)).setVisibility(GONE);
            }
            if (emptyRetryListener != null) {
                emptyRetryListener.setRetryView(emptyView);
            }
            emptyLayout.addView(emptyView);
        }
    }

    /**
     * setDefaultError
     *
     * @param text       text
     * @param imageResId imageResId
     */
    public void setDefaultError(String text, int imageResId) {
        if (errorLayout != null) {
            errorLayout.removeAllViews();
            errorView = LayoutInflater.from(getContext()).inflate(R.layout.base_default_retry_layout, null);
            errorView.setOnTouchListener(layoutTouchListener);
            errorView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (text != null) {
                ((TextView) errorView.findViewById(R.id.base_default_retry_text)).setText(text);
            }
            if (imageResId != 0) {
                ((ImageView) errorView.findViewById(R.id.base_default_retry_img)).setImageResource(imageResId);
            } else {
                (errorView.findViewById(R.id.base_default_retry_img)).setVisibility(GONE);
            }
            if (errorRetryListener != null) {
                errorRetryListener.setRetryView(errorView);
            }
            errorLayout.addView(errorView);
        }
    }

    /**
     * setDefaultError
     *
     * @param text       text
     * @param imageResId imageResId
     */
    public void setDefaultLoading(String text, int imageResId) {
        if (loadingLayout != null) {
            loadingLayout.removeAllViews();
            loadingView = LayoutInflater.from(getContext()).inflate(R.layout.base_default_retry_layout, null);
            loadingView.setOnTouchListener(layoutTouchListener);
            loadingView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (text != null) {
                ((TextView) loadingView.findViewById(R.id.base_default_retry_text)).setText(text);
            }
            if (imageResId != 0) {
                ((ImageView) loadingView.findViewById(R.id.base_default_retry_img)).setImageResource(imageResId);
            } else {
                (loadingView.findViewById(R.id.base_default_retry_img)).setVisibility(GONE);
            }
            if (loadingRetryListener != null) {
                loadingRetryListener.setRetryView(loadingView);
            }
            loadingLayout.addView(loadingView);
        }
    }

    /**
     * showEmpty
     */
    public void showEmpty() {
        emptyLayout.setVisibility(VISIBLE);
        errorLayout.setVisibility(GONE);
        loadingLayout.setVisibility(GONE);
        contentLayout.setVisibility(INVISIBLE);
        isEmpty = true;
        isError = false;
        isLoading = false;
        isContent = false;
    }

    /**
     * smoothToPosition
     *
     * @param index index
     */
    public void smoothToPosition(int index) {
        getRecyclerView().smoothScrollToPosition(index);
    }

    /**
     * smoothToPosition
     *
     * @param dx dx
     * @param dy dy
     */
    public void smoothToPosition(int dx, int dy) {
        getRecyclerView().smoothScrollBy(dx, dy);
    }

    /**
     * showContent
     */
    public void showContent() {
        emptyLayout.setVisibility(GONE);
        errorLayout.setVisibility(GONE);
        loadingLayout.setVisibility(GONE);
        contentLayout.setVisibility(VISIBLE);
        isEmpty = false;
        isError = false;
        isLoading = false;
        isContent = true;
    }

    /**
     * showEmpty
     */
    public void showLoading() {
        emptyLayout.setVisibility(GONE);
        errorLayout.setVisibility(GONE);
        loadingLayout.setVisibility(VISIBLE);
        contentLayout.setVisibility(INVISIBLE);
        isEmpty = false;
        isError = false;
        isLoading = true;
        isContent = false;
    }

    /**
     * showContent
     */
    public void showError() {
        emptyLayout.setVisibility(GONE);
        errorLayout.setVisibility(VISIBLE);
        loadingLayout.setVisibility(GONE);
        contentLayout.setVisibility(INVISIBLE);
        isError = true;
        isEmpty = false;
        isLoading = false;
        isContent = false;
    }

    /**
     * getColumnCount
     *
     * @return columnCount
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * isStaggeredEnable
     *
     * @return staggeredEnable
     */
    public boolean isStaggeredEnable() {
        return staggeredEnable;
    }

    /**
     * isEmpty
     *
     * @return isEmpty
     */
    public boolean isShowEmpty() {
        return isEmpty;
    }

    /**
     * isError
     *
     * @return isError
     */
    public boolean isShowError() {
        return isError;
    }

    /**
     * isLoading
     *
     * @return isLoading
     */
    public boolean isShowLoading() {
        return isLoading;
    }

    /**
     * isContent
     *
     * @return isContent
     */
    public boolean isShowContent() {
        return isContent;
    }

    /**
     * setLastCount
     *
     * @param lastCount lastCount
     */
    public void setLastCount(int lastCount) {
        if (lastCount < 0) {
            lastCount = 0;
        }
        this.lastCount = lastCount;
    }

    /**
     * setColumnCount
     *
     * @param columnCount columnCount
     */
    public void setColumnCount(int columnCount) {
        if (columnCount <= 1) {
            columnCount = 1;
        }
        this.columnCount = columnCount;
        if (this.columnCount == 1) {
            setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            if (staggeredEnable) {
                setLayoutManager(new StaggeredGridLayoutManager(this.columnCount, StaggeredGridLayoutManager.VERTICAL));
            } else {
                setLayoutManager(new GridLayoutManager(getContext(), this.columnCount));
            }
        }
        setOrientation(orientation);
    }

    /**
     * setStaggeredEnable
     *
     * @param staggeredEnable staggeredEnable
     */
    public void setStaggeredEnable(boolean staggeredEnable) {
        this.staggeredEnable = staggeredEnable;
        if (this.columnCount > 1) {
            if (this.staggeredEnable) {
                setLayoutManager(new StaggeredGridLayoutManager(this.columnCount, StaggeredGridLayoutManager.VERTICAL));
            } else {
                setLayoutManager(new GridLayoutManager(getContext(), this.columnCount));
            }
        }
    }

    /**
     * setLastDelay
     *
     * @param lastDelay lastDelay
     */
    public void setLastDelay(long lastDelay) {
        this.lastDelay = lastDelay;
    }

    /**
     * headLayout
     *
     * @return headLayout
     */
    private LinearLayout getHeadLayout() {
        return headLayout;
    }

    /**
     * getFootLayout
     *
     * @return footLayout
     */
    private LinearLayout getFootLayout() {
        return footLayout;
    }

    /**
     * setHeaderView
     *
     * @param view view
     */
    public void setHeaderView(View view) {
        getHeadLayout().removeAllViews();
        getHeadLayout().addView(view);
    }

    /**
     * setFooterView
     *
     * @param view view
     */
    public void setFooterView(View view) {
        getFootLayout().removeAllViews();
        getFootLayout().addView(view);
    }


    /**
     * getHeaderView
     *
     * @return header view
     */
    public View getHeaderView() {
        if (getHeadLayout().getChildCount() > 0) {
            return getHeadLayout().getChildAt(0);
        }
        return null;
    }

    /**
     * getFooterView
     *
     * @return footer view
     */
    public View getFooterView() {
        if (getFootLayout().getChildCount() > 0) {
            return getFootLayout().getChildAt(0);
        }
        return null;
    }

    /**
     * setOnLastItemVisibleListener
     *
     * @param lastItemVisibleListener lastItemVisibleListener
     */
    public void setOnLastItemVisibleListener(OnRecyclerBoxLastItemVisibleListener lastItemVisibleListener) {
        this.lastItemVisibleListener = lastItemVisibleListener;
    }

    /**
     * setOnItemMoveVisibleListener
     *
     * @param itemMoveVisibleListener itemMoveVisibleListener
     */
    public void setOnItemMoveVisibleListener(OnRecyclerBoxItemMoveVisibleListener itemMoveVisibleListener) {
        this.itemMoveVisibleListener = itemMoveVisibleListener;
    }

    /**
     * handler
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1001) {
                if (itemMoveVisibleListener != null) {
                    itemMoveVisibleListener.onItemMoveVisible(recyclerView, currentPosition, currentLastPosition);
                }
            }
        }
    };

    /**
     * getCurrentPosition
     *
     * @return currentPosition
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * getCurrentLastPosition
     *
     * @return currentLastPosition
     */
    public int getCurrentLastPosition() {
        return currentLastPosition;
    }

    //scrollListener
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            boolean flag = dy > 0;
            if (isReverseLayout() || isStackFromEnd()) {
                flag = dy < 0;
            }
            if (flag) {
                if (lastVisible(dy)) {
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
                int position = getRecyclerListView().getFristVisiblePosition();
                currentLastPosition = getRecyclerListView().getLastVisiblePosition();
                if (currentPosition != position) {
                    currentPosition = position;
                    post(new Runnable() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(1001);
                        }
                    });
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    };

    public RecyclerBox(Context context) {
        this(context, null);
    }

    public RecyclerBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * setLayoutManager
     *
     * @param layoutManager layoutManager
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager != null) {
            if (layoutManager instanceof LinearLayoutManager) {
                ((LinearLayoutManager) layoutManager).setStackFromEnd(isStackFromEnd());
                ((LinearLayoutManager) layoutManager).setReverseLayout(isReverseLayout());
            }
            getRecyclerListView().setLayoutManager(layoutManager);
        }
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return getRecyclerListView().canScrollVertically(direction);
    }

    /**
     * initView
     */
    private void initView() {
        if (isInEditMode()) return;
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //insert contentLayout
        contentLayout = new LinearLayout(getContext());
        contentLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        //insert headLayout
        headLayout = new LinearLayout(getContext());
        headLayout.setOrientation(LinearLayout.VERTICAL);
        headLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        contentLayout.addView(headLayout);
        recyclerView = new ProxyRecyclerView(getContext(), new LinearLayoutManager(getContext()));
        if (Build.VERSION.SDK_INT >= 9) {
            recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
        recyclerView.addOnScrollListener(scrollListener);
        pool = new Pool();
        pool.attachTo(this);
        contentLayout.addView(recyclerView);

        //insert footLayout
        footLayout = new LinearLayout(getContext());
        footLayout.setOrientation(LinearLayout.VERTICAL);
        footLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        contentLayout.addView(footLayout);
        super.addView(contentLayout);
        //insert emptyLayout
        emptyLayout = new RelativeLayout(getContext()) {
            @Override
            public boolean canScrollVertically(int direction) {
                return false;
            }
        };
        emptyLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        super.addView(emptyLayout);
        emptyLayout.setVisibility(GONE);
        setDefaultEmpty("", 0);

        //insert loadingLayout
        loadingLayout = new RelativeLayout(getContext()) {
            @Override
            public boolean canScrollVertically(int direction) {
                return false;
            }
        };
        loadingLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        super.addView(loadingLayout);
        loadingLayout.setVisibility(GONE);
        setDefaultLoading("", 0);

        //insert errorLayout
        errorLayout = new RelativeLayout(getContext()) {
            @Override
            public boolean canScrollVertically(int direction) {
                return false;
            }
        };
        errorLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        super.addView(errorLayout);
        errorLayout.setVisibility(GONE);
        setDefaultError("", 0);
    }

    /**
     * getPool
     *
     * @return pool
     */
    public Pool getPool() {
        return pool;
    }

    /**
     * setOrientation
     *
     * @return pool
     */
    public void setOrientation(int orientation) {
        if (getRecyclerListView().getLayoutManager() instanceof LinearLayoutManager) {
            ((LinearLayoutManager) getRecyclerListView().getLayoutManager()).setOrientation(orientation);
            if (orientation == HORIZONTAL) {
                ((LinearLayoutManager) getRecyclerListView().getLayoutManager()).setReverseLayout(false);
            }
        } else if (getRecyclerListView().getLayoutManager() instanceof GridLayoutManager) {
            ((GridLayoutManager) getRecyclerListView().getLayoutManager()).setOrientation(orientation);
        } else if (getRecyclerListView().getLayoutManager() instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) getRecyclerListView().getLayoutManager()).setOrientation(orientation);
        }
        this.orientation = orientation;
    }

    /**
     * getOrientation
     *
     * @return orientation
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * getRecyclerListView
     *
     * @return recyclerBox
     */
    private ProxyRecyclerView getRecyclerListView() {
        return recyclerView;
    }


    /**
     * setDecorationSize
     *
     * @param size size
     */
    public void setDecorationSize(int size) {
        setItemDecoration(new BoxDecoration(size));
    }

    /**
     * setItemDecoration
     *
     * @param itemDecoration itemDecoration
     */
    private void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        if (this.itemDecoration != null) {
            getRecyclerListView().removeItemDecoration(this.itemDecoration);
            itemDecoration = null;
        }
        if (itemDecoration != null) {
            this.itemDecoration = itemDecoration;
            getRecyclerListView().addItemDecoration(this.itemDecoration);
        }
    }

    /**
     * lastVisible
     *
     * @param dy dy
     * @return true or false
     */
    private boolean lastVisible(int dy) {
        int lastVisibleItem = getRecyclerListView().getLastVisiblePosition();
        int totalItemCount = getRecyclerListView().getLayoutManager().getItemCount() - 1;
        if (lastVisibleItem >= totalItemCount - lastCount) {
            return true;
        }
        return false;
    }

    /**
     * ProxyRecyclerView
     */
    private static class ProxyRecyclerView extends RecyclerView {

        public ProxyRecyclerView(Context context, LayoutManager layoutManager) {
            super(context);
            initView(layoutManager);
        }

        /**
         * initView
         */
        private void initView(LayoutManager layoutManager) {
            setLayoutManager(layoutManager);
        }

        @Override
        public boolean canScrollVertically(int direction) {
            return super.canScrollVertically(direction);
        }

        /**
         * getLastVisiblePosition
         *
         * @return last visible position
         */
        private int getLastVisiblePosition() {
            int position;
            if (getLayoutManager() instanceof LinearLayoutManager) {
                position = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
            } else if (getLayoutManager() instanceof GridLayoutManager) {
                position = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
            } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
                int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
                position = getMaxPosition(lastPositions);
            } else {
                position = getLayoutManager().getItemCount() - 1;
            }
            return position;
        }

        /**
         * getFristVisiblePosition
         *
         * @return frist visible position
         */
        private int getFristVisiblePosition() {
            int position;
            if (getLayoutManager() instanceof LinearLayoutManager) {
                position = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            } else if (getLayoutManager() instanceof GridLayoutManager) {
                position = ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
                int[] fristPositions = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
                position = getMinPosition(fristPositions);
            } else {
                position = getLayoutManager().getItemCount() - 1;
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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////   Classes    ////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Pool
     */
    public static class Pool {
        // items
        private final List<Item> items = new ArrayList<Item>();
        // types
        private final List<Item> types = new ArrayList<Item>();
        // adapter
        private final BoxAdapter adapter;
        //tag
        private Object tag;
        //recyclerBox
        private RecyclerBox recyclerBox;
        // isAttach
        private boolean isAttach = false;

        private Pool() {
            adapter = new BoxAdapter(this);
        }

        /**
         * getRecyclerBox
         *
         * @return recyclerBox
         */
        public RecyclerBox getRecyclerBox() {
            return recyclerBox;
        }

        /**
         * setTag
         *
         * @param tag tag
         */
        public void setTag(Object tag) {
            this.tag = tag;
        }

        /**
         * getTag
         *
         * @return tag
         */
        public Object getTag() {
            return tag;
        }


        /**
         * attachTo
         *
         * @param recyclerView recyclerBox
         */
        private void attachTo(RecyclerBox recyclerView) {
            if (recyclerView != null) {
                this.recyclerBox = recyclerView;
                this.recyclerBox.getRecyclerListView().setAdapter(adapter);
                isAttach = true;
            }
        }

        /**
         * getItem
         *
         * @param position position
         * @return Item
         */
        public Item<?> getItem(int position) {
            if (items == null) {
                return null;
            }
            return items.get(position);
        }

        /**
         * getByItemIype
         *
         * @param type type
         * @return Item
         */
        public Item<?> getItemByIype(int type) {
            if (items == null) {
                return null;
            }
            for (int i = 0; i < types.size(); i++) {
                if (types.get(i).getItemType() == type) {
                    return types.get(i);
                }
            }
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getItemType() == type) {
                    Item item = items.get(i);
                    types.add(item);
                    return items.get(i);
                }
            }
            return null;
        }

        /**
         * notifyDataSetChanged
         */
        public void notifyDataSetChanged() {
            if (!isAttach) {
                return;
            }
            adapter.proxyNotifyDataSetChanged();
        }

        /**
         * notifyDataSetChanged
         */
        public void notifyItemChanged(int position) {
            if (!isAttach) {
                return;
            }
            adapter.proxyNotifyItemChanged(position);
        }

        /**
         * notifyDataSetChanged
         */
        public void notifyItemChanged(int position, Object payload) {
            if (!isAttach) {
                return;
            }
            adapter.proxyNotifyItemChanged(position, payload);
        }

        /**
         * add
         *
         * @param item item
         */
        public void add(Item item) {
            items.add(item);
        }

        /**
         * add
         *
         * @param position position
         * @param item     item
         */
        public void add(int position, Item item) {
            items.add(position, item);
        }

        /**
         * remove
         *
         * @param position position
         */
        public void remove(int position) {
            items.remove(position);
        }


        /**
         * insertList
         *
         * @param items items
         */
        public void addAll(List<Item> items) {
            this.items.addAll(items);
        }

        /**
         * remove
         *
         * @param item item
         */
        public void remove(Item item) {
            if (items.contains(item)) {
                items.remove(item);
            }
        }

        /**
         * getItemCount
         *
         * @return count
         */
        public int getItemCount() {
            if (items == null) {
                return 0;
            }
            return items.size();
        }

        /**
         * replaceDatas
         *
         * @param items items
         */
        public void replaceDatas(List<Item> items) {
            this.items.removeAll(this.items);
            this.items.addAll(items);
        }

        /**
         * clear
         */
        public void clear() {
            this.items.removeAll(this.items);
        }
    }

    /**
     * BoxAdapter
     */
    private static class BoxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        // pool
        private final Pool pool;

        public BoxAdapter(Pool pool) {
            this.pool = pool;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int itemType) {
            Item item = pool.getItemByIype(itemType);
            if (item != null) {
                return item.proxyNewView(parent);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Item item = pool.getItem(position);
            item.setPosition(position);
            item.setPool(pool);
            if (item != null) {
                item.proxyBindView(holder.itemView);
            }
        }

        @Override
        public int getItemCount() {
            return pool.getItemCount();
        }

        @Override
        public int getItemViewType(int position) {
            return pool.getItem(position).getItemType();
        }

        public void proxyNotifyDataSetChanged() {
            synchronized (this) {
                try {
                    pool.getRecyclerBox().getRecyclerView().post(new Runnable() {
                        @Override
                        public void run() {
                            // Notify adapter with appropriate notify methods
                            notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                }
            }
        }

        public void proxyNotifyItemChanged(final int position) {
            synchronized (this) {
                try {
                    pool.getRecyclerBox().getRecyclerView().post(new Runnable() {
                        @Override
                        public void run() {
                            notifyItemChanged(position);
                        }
                    });
                } catch (Exception e) {
                }
            }
        }

        public void proxyNotifyItemChanged(final int position, final Object payload) {
            synchronized (this) {
                try {
                    pool.getRecyclerBox().getRecyclerView().post(new Runnable() {
                        @Override
                        public void run() {
                            notifyItemChanged(position, payload);
                        }
                    });
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Item
     *
     * @param <Data> data
     */
    public static abstract class Item<Data> {

        //data
        private Data data;
        //position
        private int position = 0;
        //itemType
        private int itemType = 0;
        //pool
        private Pool pool = null;
        //clickable
        private boolean clickable = true;
        //longclickable
        private boolean longClickable = true;
        //clickListener
        private OnRecyclerBoxItemClickListener<Data> clickListener;
        //longClickListener
        private OnRecyclerBoxItemLongClickListener<Data> longClickListener;

        /**
         * getOnItemClickListener
         *
         * @return clickListener
         */
        protected OnRecyclerBoxItemClickListener<Data> getOnItemClickListener() {
            return clickListener;
        }

        /**
         * getOnItemLongClickListener
         *
         * @return longClickListener
         */
        protected OnRecyclerBoxItemLongClickListener<Data> getOnItemLongClickListener() {
            return longClickListener;
        }

        /**
         * setOnClickListener
         *
         * @param clickListener clickListener
         * @return item
         */
        public Item<Data> setOnItemClickListener(OnRecyclerBoxItemClickListener<Data> clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        /**
         * setOnItemLongClickListener
         *
         * @param longClickListener longClickListener
         * @return item
         */
        public Item<Data> setOnItemLongClickListener(OnRecyclerBoxItemLongClickListener<Data> longClickListener) {
            this.longClickListener = longClickListener;
            return this;
        }

        public Item(Data data) {
            this.data = data;
        }

        /**
         * notifyChanged
         */
        public void notifyChanged() {
            getPool().notifyItemChanged(getPosition());
        }

        /**
         * getPool
         *
         * @return pool
         */
        public Pool getPool() {
            return pool;
        }

        /**
         * setPool
         *
         * @param pool pool
         */
        private void setPool(Pool pool) {
            this.pool = pool;
        }

        /**
         * setPosition
         *
         * @param position position
         */
        private void setPosition(int position) {
            this.position = position;
        }

        /**
         * getPosition
         *
         * @return position
         */
        public int getPosition() {
            return position;
        }

        /**
         * setClickable
         *
         * @param clickable clickable
         */
        public void setClickable(boolean clickable) {
            this.clickable = clickable;
        }

        /**
         * setLongClickable
         *
         * @param longClickable longClickable
         */
        public void setLongClickable(boolean longClickable) {
            this.longClickable = longClickable;
        }


        /**
         * isClickable
         *
         * @return clickable
         */
        public boolean isClickable() {
            return clickable;
        }

        /**
         * isLongClickable
         *
         * @return longClickable
         */
        public boolean isLongClickable() {
            return longClickable;
        }

        /**
         * newView
         *
         * @param inflater inflater
         * @param parent   parent
         * @return view
         */
        protected abstract View newView(LayoutInflater inflater, ViewGroup parent);

        /**
         * bindView
         *
         * @param data data
         */
        protected abstract void bindView(View itemView, Data data);

        /**
         * getItemType
         *
         * @return doGet type
         */
        public int getItemType() {
            if (itemType == 0) {
                itemType = Md5Utils.encryptInt(getClass().getName());
            }
            return itemType;
        }

        /**
         * getData
         *
         * @return data
         */
        public Data getData() {
            return data;
        }

        /**
         * proxyNewView
         *
         * @param parent parent
         * @return holder
         */
        private final RecyclerView.ViewHolder proxyNewView(ViewGroup parent) {
            View itemView = newView(LayoutInflater.from(parent.getContext()), parent);
            if (itemView != null) {
                itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            ItemHolder holder = new ItemHolder(itemView);
            return holder;
        }

        /**
         * proxyBindView
         */
        private final void proxyBindView(View itemView) {
            if (itemView != null) {
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!clickable) {
                            return;
                        }
                        if (clickListener != null) {
                            clickListener.onClick(Item.this, getPosition(), v);
                        }
                    }
                });
                itemView.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (!longClickable) {
                            return false;
                        }
                        if (longClickListener != null) {
                            return longClickListener.onLongClick(Item.this, getPosition(), v);
                        }
                        return false;
                    }
                });
            }
            if (itemView != null) {
                if (getPool().getRecyclerBox().getOrientation() == HORIZONTAL) {
                    itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                } else {
                    itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }
            }
            bindView(itemView, data);
        }

        /**
         * ItemHolder
         */
        private class ItemHolder extends RecyclerView.ViewHolder {
            public ItemHolder(View itemView) {
                super(itemView);
            }
        }
    }

    /**
     * BoxDecoration
     */
    public static class BoxDecoration extends RecyclerView.ItemDecoration {
        //space
        private int space = 0;
        private boolean full = false;

        public BoxDecoration(int spacingInPixels) {
            this.space = spacingInPixels / 2;
        }

        public BoxDecoration(int spacingInPixels, boolean full) {
            this(spacingInPixels);
            this.full = full;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            // Add top margin only for the first item to avoid double space between items
            if (full || parent.getLayoutManager() instanceof GridLayoutManager || parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;
                outRect.top = space;
                parent.setPadding(space, space, space, space);
            } else {
                int or = VERTICAL;
                if (parent.getLayoutManager() instanceof LinearLayoutManager) {
                    or = ((LinearLayoutManager) (parent.getLayoutManager())).getOrientation();
                }
                if (or == VERTICAL) {
                    outRect.bottom = space;
                } else {
                    outRect.right = space;
                }
            }
        }
    }
}
