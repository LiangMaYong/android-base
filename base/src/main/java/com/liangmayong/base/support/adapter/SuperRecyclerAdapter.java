package com.liangmayong.base.support.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.liangmayong.base.support.adapter.interfaces.ISuperAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/3/1.
 */
public class SuperRecyclerAdapter extends RecyclerView.Adapter<SuperRecyclerAdapter.ViewHolder> implements ISuperAdapter {

    // items
    private final List<SuperItemView<?>> items = new ArrayList<SuperItemView<?>>();
    // viewtypeSizes
    private final Map<Integer, Integer> viewtypeSizes = new HashMap<Integer, Integer>();
    // viewtypePositions
    private final Map<Integer, LinkedList<Integer>> viewtypePositions = new HashMap<Integer, LinkedList<Integer>>();
    private boolean reverse = false;

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    @Override
    public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        setMaxSize(viewType, getMaxSize(viewType) + 1);
        int position = getItemPosition(viewType);
        View view = getItem(position).callNewView(this, position, parent);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public final void onBindViewHolder(ViewHolder holder, int position) {
        getItem(position).callBindView(position, holder.itemView);
    }

    @Override
    public final int getItemCount() {
        return items.size();
    }

    @Override
    public final int getItemViewType(int position) {
        int viewType = getItem(position).getViewType().hashCode();
        if (viewtypePositions.containsKey(viewType)) {
            if (getMaxSize(viewType) + 1 > viewtypePositions.get(viewType).size()) {
                if (!viewtypePositions.get(viewType).contains(position)) {
                    viewtypePositions.get(viewType).add(position);
                }
            }
        } else {
            LinkedList<Integer> integers = new LinkedList<Integer>();
            integers.add(position);
            viewtypePositions.put(viewType, integers);
        }
        return viewType;
    }

    @Override
    public final long getItemId(int position) {
        return position;
    }

    /////////////////////////////////////////////////////////////////////
    ////// ISuperAdapter
    /////////////////////////////////////////////////////////////////////

    /**
     * getItem
     *
     * @param position position
     * @return item
     */
    public SuperItemView<?> getItem(int position) {
        if (isReverse()) {
            return items.get(items.size() - position - 1);
        }
        return items.get(position);
    }

    /**
     * add
     *
     * @param item item
     */
    public void add(SuperItemView<?> item) {
        items.add(item);
    }

    /**
     * add
     *
     * @param position position
     * @param item     item
     */
    public void add(int position, SuperItemView<?> item) {
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
     * addAll
     *
     * @param items items
     */
    public void addAll(List<SuperItemView<?>> items) {
        this.items.addAll(items);
    }

    /**
     * remove
     *
     * @param item item
     */
    public void remove(SuperItemView<?> item) {
        if (items.contains(item)) {
            items.remove(item);
        }
    }

    /**
     * clear
     */
    public void removeAll() {
        this.items.removeAll(this.items);
    }

    /**
     * replaceDatas
     *
     * @param items items
     */
    public void replaceItems(List<SuperItemView<?>> items) {
        this.items.removeAll(this.items);
        this.items.addAll(items);
    }

    @Override
    public List<SuperItemView<?>> getItems() {
        return items;
    }

    @Override
    public boolean contains(SuperItemView<?> item) {
        return items.contains(item);
    }

    @Override
    public void notifyItemChanged(int firstPosition, int lastPosition) {
        for (int i = firstPosition; i <= lastPosition; i++) {
            notifyItemChanged(i);
        }
    }

    /**
     * ViewHolder
     */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        private ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /////////////////////////////////////////////////////////////////////
    ////// Private
    /////////////////////////////////////////////////////////////////////

    /**
     * getMaxSize
     *
     * @param viewType viewType
     * @return size
     */
    private int getMaxSize(int viewType) {
        if (viewtypeSizes.containsKey(viewType)) {
            return viewtypeSizes.get(viewType);
        }
        return 0;
    }

    /**
     * setMaxSize
     *
     * @param viewType viewType
     * @param maxType  maxType
     */
    private void setMaxSize(int viewType, int maxType) {
        viewtypeSizes.put(viewType, maxType);
    }

    /**
     * getItemPosition
     *
     * @param viewType viewType
     * @return position
     */
    private int getItemPosition(int viewType) {
        if (viewtypePositions.containsKey(viewType) && viewtypePositions.get(viewType).size() > 0) {
            return viewtypePositions.get(viewType).getLast();
        }
        return 0;
    }
}
