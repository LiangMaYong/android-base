package com.liangmayong.base.widget.superlistview;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
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
import com.liangmayong.base.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangMaYong on 2016/8/24.
 */
public class SuperListView extends RelativeLayout {

    /**
     * OnReListViewRetryListener
     */
    public interface OnReListViewRetryListener {
        void setRetryView(View retryView);
    }

    // pool
    private Pool pool = null;
    // itemDecoration
    private RecyclerView.ItemDecoration itemDecoration = null;
    // isLast
    private boolean isLast = false;
    // lastItemVisibleListener
    private OnLastItemVisibleListener lastItemVisibleListener = null;
    // lastCount
    private int lastCount = 5;
    // lastDelay
    private long lastDelay = 1500;
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
    // columnCount
    private int columnCount = 1;
    // staggeredEnable
    private boolean staggeredEnable = false;
    //recyclerView
    private ProxyRecyclerView recyclerView;

    //errorRetryListener
    private OnReListViewRetryListener errorRetryListener;
    //emptyRetryListener
    private OnReListViewRetryListener emptyRetryListener;
    //loadingRetryListener
    private OnReListViewRetryListener loadingRetryListener;
    //layoutTouchListener
    private OnTouchListener layoutTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    };

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
     * getLoadingView
     *
     * @return loadingView
     */
    public View getLoadingView() {
        return loadingView;
    }

    /**
     * setEmptyLayout
     *
     * @param resLayoutId        resLayoutId
     * @param emptyRetryListener emptyRetryListener
     */
    public void setEmptyLayout(int resLayoutId, OnReListViewRetryListener emptyRetryListener) {
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
    public void setErrorLayout(int resLayoutId, OnReListViewRetryListener errorRetryListener) {
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
    public void setLoadingLayout(int resLayoutId, OnReListViewRetryListener loadingRetryListener) {
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
     * setDefualtEmpty
     *
     * @param text       text
     * @param imageResId imageResId
     */
    public void setDefualtEmpty(String text, int imageResId) {
        if (emptyLayout != null) {
            emptyLayout.removeAllViews();
            emptyView = LayoutInflater.from(getContext()).inflate(R.layout.base_defualt_retry_layout, null);
            emptyView.setOnTouchListener(layoutTouchListener);
            emptyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (text != null) {
                ((TextView) emptyView.findViewById(R.id.base_defualt_retry_text)).setText(text);
            }
            if (imageResId != 0) {
                ((ImageView) emptyView.findViewById(R.id.base_defualt_retry_img)).setImageResource(imageResId);
            } else {
                (emptyView.findViewById(R.id.base_defualt_retry_img)).setVisibility(GONE);
            }
            if (emptyRetryListener != null) {
                emptyRetryListener.setRetryView(emptyView);
            }
            emptyLayout.addView(emptyView);
        }
    }

    /**
     * setDefualtError
     *
     * @param text       text
     * @param imageResId imageResId
     */
    public void setDefualtError(String text, int imageResId) {
        if (errorLayout != null) {
            errorLayout.removeAllViews();
            errorView = LayoutInflater.from(getContext()).inflate(R.layout.base_defualt_retry_layout, null);
            errorView.setOnTouchListener(layoutTouchListener);
            errorView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (text != null) {
                ((TextView) errorView.findViewById(R.id.base_defualt_retry_text)).setText(text);
            }
            if (imageResId != 0) {
                ((ImageView) errorView.findViewById(R.id.base_defualt_retry_img)).setImageResource(imageResId);
            } else {
                (errorView.findViewById(R.id.base_defualt_retry_img)).setVisibility(GONE);
            }
            if (errorRetryListener != null) {
                errorRetryListener.setRetryView(errorView);
            }
            errorLayout.addView(errorView);
        }
    }

    /**
     * setDefualtError
     *
     * @param text       text
     * @param imageResId imageResId
     */
    public void setDefualtLoading(String text, int imageResId) {
        if (loadingLayout != null) {
            loadingLayout.removeAllViews();
            loadingView = LayoutInflater.from(getContext()).inflate(R.layout.base_defualt_retry_layout, null);
            loadingView.setOnTouchListener(layoutTouchListener);
            loadingView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (text != null) {
                ((TextView) loadingView.findViewById(R.id.base_defualt_retry_text)).setText(text);
            }
            if (imageResId != 0) {
                ((ImageView) loadingView.findViewById(R.id.base_defualt_retry_img)).setImageResource(imageResId);
            } else {
                (loadingView.findViewById(R.id.base_defualt_retry_img)).setVisibility(GONE);
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
    public LinearLayout getHeadLayout() {
        return headLayout;
    }

    /**
     * getFootLayout
     *
     * @return footLayout
     */
    public LinearLayout getFootLayout() {
        return footLayout;
    }

    /**
     * setOnLastItemVisibleListener
     *
     * @param lastItemVisibleListener lastItemVisibleListener
     */
    public void setOnLastItemVisibleListener(OnLastItemVisibleListener lastItemVisibleListener) {
        this.lastItemVisibleListener = lastItemVisibleListener;
    }

    //scrollListener
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) {
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
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    };

    public SuperListView(Context context) {
        super(context);
        initView();
    }

    public SuperListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * setLayoutManager
     *
     * @param layoutManager layoutManager
     */
    private void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager != null) {
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
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //add contentLayout
        contentLayout = new LinearLayout(getContext());
        contentLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        //add headLayout
        headLayout = new LinearLayout(getContext());
        headLayout.setOrientation(LinearLayout.VERTICAL);
        headLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        contentLayout.addView(headLayout);
        recyclerView = new ProxyRecyclerView(getContext(), new LinearLayoutManager(getContext()));
        if (Build.VERSION.SDK_INT >= 9) {
            recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        recyclerView.addOnScrollListener(scrollListener);
        pool = new Pool();
        pool.attachTo(recyclerView);
        contentLayout.addView(recyclerView);

        //add footLayout
        footLayout = new LinearLayout(getContext());
        footLayout.setOrientation(LinearLayout.VERTICAL);
        footLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        contentLayout.addView(footLayout);
        super.addView(contentLayout);
        //add emptyLayout
        emptyLayout = new RelativeLayout(getContext()) {
            @Override
            public boolean canScrollVertically(int direction) {
                return false;
            }
        };
        emptyLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        super.addView(emptyLayout);
        emptyLayout.setVisibility(GONE);
        setDefualtEmpty("", 0);

        //add loadingLayout
        loadingLayout = new RelativeLayout(getContext()) {
            @Override
            public boolean canScrollVertically(int direction) {
                return false;
            }
        };
        loadingLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        super.addView(loadingLayout);
        loadingLayout.setVisibility(GONE);
        setDefualtLoading("", 0);

        //add errorLayout
        errorLayout = new RelativeLayout(getContext()) {
            @Override
            public boolean canScrollVertically(int direction) {
                return false;
            }
        };
        errorLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        super.addView(errorLayout);
        errorLayout.setVisibility(GONE);
        setDefualtError("", 0);
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
     * getRecyclerListView
     *
     * @return recyclerView
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
        setItemDecoration(new SuperDecoration(size));
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
     * OnLastItemVisibleListener
     */
    public static interface OnLastItemVisibleListener {
        void onLastItemVisible(RecyclerView recyclerView, int dx, int dy);
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

    public static class Pool {
        // items
        private final List<Item> items = new ArrayList<Item>();
        // adapter
        private final SuperAdapter adapter;
        //tag
        private Object tag;
        //recyclerView
        private RecyclerView recyclerView;
        // isAttach
        private boolean isAttach = false;

        private Pool() {
            adapter = new SuperAdapter(this);
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
         * @param recyclerView recyclerView
         */
        private void attachTo(RecyclerView recyclerView) {
            if (recyclerView != null) {
                this.recyclerView = recyclerView;
                this.recyclerView.setAdapter(adapter);
                isAttach = true;
            }
        }

        /**
         * getItem
         *
         * @param position position
         * @return Item
         */
        public Item<?> get(int position) {
            if (items == null) {
                return null;
            }
            return items.get(position);
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
         * remove
         *
         * @param position position
         */
        public void remove(int position) {
            items.remove(position);
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
         * addAll
         *
         * @param items items
         */
        public void addAll(List<Item> items) {
            items.addAll(items);
        }

        /**
         * getItems
         *
         * @return items
         */
        public List<Item> getItems() {
            return items;
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
         * clear
         */
        public void clear() {
            this.items.removeAll(this.items);
        }
    }

    /**
     * SuperAdapter
     */
    private static class SuperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        // pool
        private final Pool pool;

        public SuperAdapter(Pool pool) {
            this.pool = pool;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            Item item = pool.get(position);
            item.setPosition(position);
            item.setPool(pool);
            if (item != null) {
                return item.proxyNewView(parent);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Item item = pool.get(position);
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
            return position;
        }

        public void proxyNotifyDataSetChanged() {
            synchronized (this) {
                notifyDataSetChanged();
            }
        }

        public void proxyNotifyItemChanged(int position) {
            synchronized (this) {
                notifyItemChanged(position);
            }
        }

        public void proxyNotifyItemChanged(int position, Object payload) {
            synchronized (this) {
                notifyItemChanged(position, payload);
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
        //pool
        private Pool pool = null;
        //clickListener
        private OnClickListener clickListener;
        //longClickListener
        private OnLongClickListener longClickListener;

        /**
         * setOnClickListener
         *
         * @param clickListener clickListener
         */
        public void setOnClickListener(OnClickListener clickListener) {
            this.clickListener = clickListener;
        }

        public void setOnLongClickListener(OnLongClickListener longClickListener) {
            this.longClickListener = longClickListener;
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
                ViewBinding.parserClassByView(this, itemView);
                if (clickListener != null) {
                    itemView.setOnClickListener(clickListener);
                }
                if (longClickListener != null) {
                    itemView.setOnLongClickListener(longClickListener);
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
     * SuperDecoration
     */
    private static class SuperDecoration extends RecyclerView.ItemDecoration {
        //space
        private int space = 0;

        public SuperDecoration(int spacingInPixels) {
            this.space = spacingInPixels / 2;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            // Add top margin only for the first item to avoid double space between items
            if (parent.getLayoutManager() instanceof GridLayoutManager || parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;
                outRect.top = space;
                parent.setPadding(space, space, space, space);
            } else {
                outRect.bottom = space;
            }
        }
    }
}
