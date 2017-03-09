package com.liangmayong.base.basic.flow.stack;

import android.support.v4.app.Fragment;

import com.liangmayong.base.basic.flow.FlowBaseFragment;

import java.util.ArrayList;

/**
 * Stack
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class FragmentStack {
    private ArrayList<ArrayList<FlowBaseFragment>> stackList = new ArrayList<ArrayList<FlowBaseFragment>>();

    public FragmentStack() {
        ArrayList<FlowBaseFragment> stack = new ArrayList<FlowBaseFragment>();
        stackList.add(stack);
    }

    /**
     * standard mode,Directly add to the current task stack
     *
     * @param fragment Added fragment
     */
    public void putStandard(FlowBaseFragment fragment) {
        synchronized (stackList) {
            stackList.get(stackList.size() - 1).add(fragment);
        }
    }

    /**
     * singleInstance mode,Create a new task stack at a time.
     *
     * @param fragment fragment
     */
    public void putSingleInstance(FlowBaseFragment fragment) {
        synchronized (stackList) {
            ArrayList<FlowBaseFragment> frags = new ArrayList<FlowBaseFragment>();
            frags.add(fragment);
            stackList.add(frags);
        }
    }

    public void closeFragment(FlowBaseFragment fragment) {
        synchronized (FragmentStack.this) {
            int i = stackList.size() - 1;
            if (i >= 0) {
                ArrayList<FlowBaseFragment> lastStack = stackList.get(i);
                if (lastStack != null && (!lastStack.isEmpty())) {
                    lastStack.remove(fragment);
                    if (lastStack.isEmpty()) {
                        stackList.remove(lastStack);
                    }
                } else {
                    stackList.remove(lastStack);
                }
            } else {
                stackList.clear();
            }
        }
    }

    public void onBackPressed() {
        synchronized (stackList) {
            int i = stackList.size() - 1;
            if (i >= 0) {
                ArrayList<FlowBaseFragment> lastStack = stackList.get(i);
                if (lastStack != null && (!lastStack.isEmpty())) {
                    lastStack.remove(lastStack.size() - 1);
                    if (lastStack.isEmpty()) {
                        stackList.remove(lastStack);
                    }
                } else {
                    stackList.remove(lastStack);
                }
            } else {
                stackList.clear();
            }
        }
    }

    /**
     * getLast
     *
     * @return lsat
     */
    public Fragment[] getLast() {
        Fragment[] fagArr = new Fragment[2];
        synchronized (stackList) {
            if (stackList.size() > 0) {
                ArrayList<FlowBaseFragment> list = stackList.get(stackList.size() - 1);
                if (list != null && (!list.isEmpty())) {
                    fagArr[0] = list.get(list.size() - 1);
                    if (list.size() > 1) {
                        fagArr[1] = list.get(list.size() - 2);
                    }
                }
            }
        }
        return fagArr;
    }
}
