package com.liangmayong.base.widget.recycler;

import android.content.Context;
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
import com.liangmayong.base.bind.BindView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangMaYong on 2016/8/24.
 */
public class RecyclerListView extends RelativeLayout {

    /**
     * OnRecyclerListViewRetryListener
     */
    public interface OnRecyclerListViewRetryListener {
        void setRetryView(View retryView);
    }

    //itemPool
    private ItemPool itemPool = null;
    //itemDecoration
    private RecyclerView.ItemDecoration itemDecoration = null;
    //isLast
    private boolean isLast = false;
    //lastItemVisibleListener
    private OnLastItemVisibleListener lastItemVisibleListener = null;
    //lastCount
    private int lastCount = 5;
    //contentLayout
    private LinearLayout contentLayout = null;
    //headLayout
    private LinearLayout headLayout = null;
    //footLayout
    private LinearLayout footLayout = null;
    //emptyLayout
    private RelativeLayout emptyLayout = null;
    //errorLayout
    private RelativeLayout errorLayout = null;
    //loadingLayout
    private RelativeLayout loadingLayout = null;
    //emptyView
    private View emptyView = null;
    //errorView
    private View errorView = null;
    //loadingView
    private View loadingView = null;
    //is empty
    private boolean isEmpty = false;
    //is error
    private boolean isError = false;
    //is loading
    private boolean isLoading = false;
    //is content
    private boolean isContent = true;

    //errorListViewRetryListener
    private OnRecyclerListViewRetryListener errorListViewRetryListener;
    //emptyListViewRetryListener
    private OnRecyclerListViewRetryListener emptyListViewRetryListener;
    //loadingListViewRetryListener
    private OnRecyclerListViewRetryListener loadingListViewRetryListener;
    //emptyView
    private OnTouchListener layoutTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    };

    /**
     * setEmptyRetryListener
     *
     * @param emptyListViewRetryListener emptyListViewRetryListener
     */
    public void setEmptyRetryListener(OnRecyclerListViewRetryListener emptyListViewRetryListener) {
        this.emptyListViewRetryListener = emptyListViewRetryListener;
    }


    /**
     * setLoadingRetryListener
     *
     * @param loadingListViewRetryListener loadingListViewRetryListener
     */
    public void setLoadingRetryListener(OnRecyclerListViewRetryListener loadingListViewRetryListener) {
        this.loadingListViewRetryListener = loadingListViewRetryListener;
    }

    /**
     * setErrorRetryListener
     *
     * @param errorListViewRetryListener errorListViewRetryListener
     */
    public void setErrorRetryListener(OnRecyclerListViewRetryListener errorListViewRetryListener) {
        this.errorListViewRetryListener = errorListViewRetryListener;
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
     * @param resLayoutId resLayoutId
     */
    public void setEmptyLayout(int resLayoutId) {
        if (emptyLayout != null) {
            emptyLayout.removeAllViews();
            emptyView = LayoutInflater.from(getContext()).inflate(resLayoutId, null);
            emptyView.setOnTouchListener(layoutTouchListener);
            emptyView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (emptyListViewRetryListener != null) {
                emptyListViewRetryListener.setRetryView(emptyView);
            }
            emptyLayout.addView(emptyView);
        }
    }

    /**
     * setErrorLayout
     *
     * @param resLayoutId resLayoutId
     */
    public void setErrorLayout(int resLayoutId) {
        if (errorLayout != null) {
            errorLayout.removeAllViews();
            errorView = LayoutInflater.from(getContext()).inflate(resLayoutId, null);
            errorView.setOnTouchListener(layoutTouchListener);
            errorView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (errorListViewRetryListener != null) {
                errorListViewRetryListener.setRetryView(errorView);
            }
            errorLayout.addView(errorView);
        }
    }

    /**
     * setErrorLayout
     *
     * @param resLayoutId resLayoutId
     */
    public void setLoadingLayout(int resLayoutId) {
        if (loadingLayout != null) {
            loadingLayout.removeAllViews();
            loadingView = LayoutInflater.from(getContext()).inflate(resLayoutId, null);
            loadingView.setOnTouchListener(layoutTouchListener);
            loadingView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (loadingListViewRetryListener != null) {
                loadingListViewRetryListener.setRetryView(loadingView);
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
            emptyView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (text != null) {
                ((TextView) emptyView.findViewById(R.id.base_defualt_retry_text)).setText(text);
            }
            if (imageResId != 0) {
                ((ImageView) emptyView.findViewById(R.id.base_defualt_retry_img)).setImageResource(imageResId);
            } else {
                ((ImageView) emptyView.findViewById(R.id.base_defualt_retry_img)).setImageResource(R.mipmap.base_defualt_empty_img);
            }
            if (emptyListViewRetryListener != null) {
                emptyListViewRetryListener.setRetryView(emptyView);
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
            errorView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (text != null) {
                ((TextView) errorView.findViewById(R.id.base_defualt_retry_text)).setText(text);
            }
            if (imageResId != 0) {
                ((ImageView) errorView.findViewById(R.id.base_defualt_retry_img)).setImageResource(imageResId);
            }
            if (errorListViewRetryListener != null) {
                errorListViewRetryListener.setRetryView(errorView);
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
            loadingView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            if (text != null) {
                ((TextView) loadingView.findViewById(R.id.base_defualt_retry_text)).setText(text);
            }
            if (imageResId != 0) {
                ((ImageView) loadingView.findViewById(R.id.base_defualt_retry_img)).setImageResource(imageResId);
            } else {
                ((ImageView) loadingView.findViewById(R.id.base_defualt_retry_img)).setImageResource(R.mipmap.base_defualt_loading_img);
            }
            if (loadingListViewRetryListener != null) {
                loadingListViewRetryListener.setRetryView(loadingView);
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
        contentLayout.setVisibility(GONE);
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
        contentLayout.setVisibility(GONE);
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
        contentLayout.setVisibility(GONE);
        isError = true;
        isEmpty = false;
        isLoading = false;
        isContent = false;
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
                    if (!isLast) {
                        isLast = true;
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

    public RecyclerListView(Context context) {
        super(context);
        initView();
    }

    public RecyclerListView(Context context, AttributeSet attrs) {
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
            getRecyclerListView().setLayoutManager(layoutManager);
        }
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return getRecyclerListView().canScrollVertically(direction);
    }

    //recyclerView
    private ProxyRecyclerView recyclerView;

    /**
     * initView
     */
    private void initView() {
        //add contentLayout
        contentLayout = new LinearLayout(getContext());
        contentLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
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
        itemPool = new ItemPool();
        itemPool.attachTo(recyclerView);
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
     * getItemPool
     *
     * @return itemPool
     */
    public ItemPool getItemPool() {
        return itemPool;
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
     * setItemDecoration
     *
     * @param itemDecoration itemDecoration
     */
    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        if (this.itemDecoration != null) {
            getRecyclerListView().removeItemDecoration(this.itemDecoration);
        } else {
            if (itemDecoration != null) {
                this.itemDecoration = itemDecoration;
                getRecyclerListView().addItemDecoration(this.itemDecoration);
            }
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
     * RecyclerListView
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

    public static class ItemPool {

        private ItemPool() {
        }

        //tag
        private Object tag;

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

        // datas
        private List<Item<?>> items = new ArrayList<Item<?>>();
        // adapter
        private ItemAdapterInterface adapter = null;

        /**
         * attachTo
         *
         * @param recyclerView recyclerView
         */
        public void attachTo(RecyclerView recyclerView) {
            if (recyclerView != null) {
                adapter = new ItemPoolRecyclerViewAdapter(this);
                recyclerView.setAdapter((ItemPoolRecyclerViewAdapter) adapter);
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
            if (adapter != null) {
                adapter.proxyNotifyDataSetChanged();
            }
        }

        /**
         * add
         *
         * @param item item
         */
        public void add(Item<?> item) {
            items.add(item);
        }

        /**
         * addAll
         *
         * @param items  items
         * @param notify notify
         */
        public void addAll(List<Item<?>> items, boolean notify) {
            this.items.addAll(items);
            if (notify) {
                notifyDataSetChanged();
            }
        }

        /**
         * getItems
         *
         * @return items
         */
        public List<Item<?>> getItems() {
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
         * replaceDatas
         *
         * @param items  items
         * @param notify notify
         */
        public void replaceDatas(List<Item<?>> items, boolean notify) {
            this.items.removeAll(this.items);
            this.items.addAll(items);
            if (notify) {
                notifyDataSetChanged();
            }
        }


    }

    /**
     * ItemAdapter
     */
    private static interface ItemAdapterInterface {
        void proxyNotifyDataSetChanged();
    }

    /**
     * ItemPoolAdapter
     */
    private static class ItemPoolRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemAdapterInterface {

        private ItemPool pool;

        public ItemPoolRecyclerViewAdapter(ItemPool pool) {
            this.pool = pool;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            Item item = pool.get(position);
            item.setPosition(position);
            item.setItemPool(pool);
            if (item != null) {
                return item.proxyOnCreateViewHolder(parent);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Item item = pool.get(position);
            item.setPosition(position);
            item.setItemPool(pool);
            if (item != null) {
                item.proxyOnBindViewHolder();
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

        @Override
        public void proxyNotifyDataSetChanged() {
            synchronized (this) {
                notifyDataSetChanged();
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
        //position
        private ItemPool itemPool = null;

        /**
         * getItemPool
         *
         * @return itemPool
         */
        public ItemPool getItemPool() {
            return itemPool;
        }

        /**
         * setItemPool
         *
         * @param itemPool itemPool
         */
        private void setItemPool(ItemPool itemPool) {
            this.itemPool = itemPool;
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

        //rootView
        private View rootView;
        // view holder
        private InternalViewHolder internalViewHolder;

        public Item(Data data) {
            this.data = data;
        }

        /**
         * onCreateItemView
         *
         * @param inflater inflater
         * @param parent   parent
         * @return view
         */
        private final View onCreateItemView(LayoutInflater inflater, ViewGroup parent) {
            if (getItemLayoutId() > 0) {
                rootView = inflater.inflate(getItemLayoutId(), null);
                BindView.parserClassByView(this, rootView);
            } else {
                rootView = BindView.parserClass(this, inflater.getContext());
            }
            initView(rootView);
            return rootView;
        }

        /**
         * initView
         *
         * @param rootView rootView
         */
        protected abstract void initView(View rootView);

        /**
         * getContainerViewId
         *
         * @return containerViewId
         */
        protected int getItemLayoutId() {
            return -1;
        }

        /**
         * onBindItem
         *
         * @param data data
         */
        protected abstract void onBindItem(Data data);


        /**
         * getData
         *
         * @return data
         */
        public Data getData() {
            return data;
        }

        /**
         * proxyOnCreateViewHolder
         *
         * @param parent parent
         * @return internalViewHolder
         */
        private final RecyclerView.ViewHolder proxyOnCreateViewHolder(ViewGroup parent) {
            View itemView = onCreateItemView(LayoutInflater.from(parent.getContext()), parent);
            internalViewHolder = new InternalViewHolder(itemView);
            return internalViewHolder;
        }

        /**
         * proxyOnBindViewHolder
         */
        private final void proxyOnBindViewHolder() {
            onBindItem(data);
        }

        /**
         * InternalViewHolder
         */
        private class InternalViewHolder extends RecyclerView.ViewHolder {
            public InternalViewHolder(View itemView) {
                super(itemView);
            }
        }

    }
}
