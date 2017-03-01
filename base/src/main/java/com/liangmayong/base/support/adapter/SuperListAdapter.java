package com.liangmayong.base.support.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.liangmayong.base.support.adapter.interfaces.ISuperAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/2/27.
 */
public class SuperListAdapter extends BaseAdapter implements ISuperAdapter {

    // listView
    private AbsListView listView = null;
    private final List<SuperItemView<?>> items = new ArrayList<SuperItemView<?>>();
    private Map<String, List<View>> recyclerViewTypeMap = new HashMap<String, List<View>>();

    @Override
    public final int getCount() {
        return items.size();
    }

    @Override
    public final SuperItemView<?> getItem(int position) {
        return items.get(position);
    }

    @Override
    public final long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (listView == null && parent instanceof AbsListView) {
            listView = (AbsListView) parent;
        }
        SuperItemView<?> item = getItem(position);
        convertView = getRecyclerTypeView(item.getViewType());
        if (convertView == null) {
            convertView = item.callNewView(this, position,parent);
            if (recyclerViewTypeMap.containsKey(item.getViewType())) {
                recyclerViewTypeMap.get(item.getViewType()).add(convertView);
            } else {
                List<View> views = new ArrayList<>();
                views.add(convertView);
                recyclerViewTypeMap.put(item.getViewType(), views);
            }
        }
        item.callBindView(position, convertView);
        return convertView;
    }


    /**
     * getRecyclerTypeView
     *
     * @param viewType viewType
     * @return view
     */
    private View getRecyclerTypeView(String viewType) {
        List<View> list = recyclerViewTypeMap.get(viewType);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getParent() == null) {
                    return list.get(i);
                }
            }
        }
        return null;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    /**
     * notifyDataSetChanged
     *
     * @param position position
     */
    public void notifyItemChanged(int position) {
        if (listView == null) {
            return;
        }
        try {
            int firstVisiblePosition = listView.getFirstVisiblePosition();
            int lastVisiblePosition = listView.getLastVisiblePosition();
            if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
                View view = listView.getChildAt(position - firstVisiblePosition);
                getItem(position).callBindView(position, view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * notifyDataSetChanged
     *
     * @param firstPosition firstPosition
     * @param lastPosition  lastPosition
     */
    public void notifyItemChanged(int firstPosition, int lastPosition) {
        if (listView == null) {
            return;
        }
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int lastVisiblePosition = listView.getLastVisiblePosition();
        if (firstPosition < firstVisiblePosition) {
            firstPosition = firstVisiblePosition;
        }
        if (lastPosition > lastVisiblePosition) {
            lastPosition = lastVisiblePosition;
        }
        for (int i = firstPosition; i < lastPosition; i++) {
            notifyItemChanged(i);
        }
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

}
