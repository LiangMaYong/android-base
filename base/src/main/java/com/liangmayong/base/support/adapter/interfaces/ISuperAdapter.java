package com.liangmayong.base.support.adapter.interfaces;

import com.liangmayong.base.support.adapter.SuperItemView;

import java.util.List;

/**
 * Created by LiangMaYong on 2017/3/1.
 */

public interface ISuperAdapter {

    /**
     * getItem
     *
     * @param position position
     * @return item
     */
    SuperItemView<?> getItem(int position);

    /**
     * add
     *
     * @param item item
     */
    void add(SuperItemView<?> item);

    /**
     * add
     *
     * @param position position
     * @param item     item
     */
    void add(int position, SuperItemView<?> item);

    /**
     * remove
     *
     * @param position position
     */
    void remove(int position);

    /**
     * addAll
     *
     * @param items items
     */
    void addAll(List<SuperItemView<?>> items);

    /**
     * remove
     *
     * @param item item
     */
    void remove(SuperItemView<?> item);

    /**
     * clear
     */
    void removeAll();

    /**
     * replaceDatas
     *
     * @param items items
     */
    void replaceItems(List<SuperItemView<?>> items);

    /**
     * getItems
     *
     * @return get items
     */
    List<SuperItemView<?>> getItems();

    /**
     * contains
     *
     * @param item item
     * @return contains
     */
    boolean contains(SuperItemView<?> item);

    /**
     * notifyItemChanged
     *
     * @param position position
     */
    void notifyItemChanged(int position);
}
