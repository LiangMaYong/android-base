package com.liangmayong.base.basic.flow.stack;

import android.support.v4.app.Fragment;

import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.basic.flow.interfaces.IClose;

import java.util.ArrayList;

/**
 * Stack
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class Stack {
    private ArrayList<ArrayList<FlowBaseFragment>> stackList = new ArrayList<ArrayList<FlowBaseFragment>>();
    private IClose close;
    private boolean instance = false;

    public Stack() {
        ArrayList<FlowBaseFragment> stack = new ArrayList<FlowBaseFragment>();
        stackList.add(stack);
    }

    /**
     * standard mode,Directly add to the current task stack
     *
     * @param fragment Added fragment
     */
    public void putStandard(FlowBaseFragment fragment) {
        instance = false;
        synchronized (stackList) {
            stackList.get(stackList.size() - 1).add(fragment);
        }
    }

    /**
     * SingleTop mode ,If the top is not created
     *
     * @param fragment Added fragment
     * @return Whether to contain the current instance
     */
    public boolean putSingleTop(FlowBaseFragment fragment) {
        instance = false;
        synchronized (stackList) {
            ArrayList<FlowBaseFragment> lastList = stackList.get(stackList.size() - 1);
            if (lastList.isEmpty()) {
                lastList.add(fragment);
                return false;
            } else {
                FlowBaseFragment last = lastList.get(lastList.size() - 1);
                if (last.getClass().getName().equals(fragment.getClass().getName())) {
                    fragment.onNewIntent();
                    return true;
                } else {
                    lastList.add(fragment);
                    return false;
                }
            }
        }
    }

    /**
     * singTask mode ,If the current task stack does not create and empty all of
     * the upper instance
     *
     * @param fragment Added fragment
     * @return Whether to contain the current instance
     */
    public boolean putSingleTask(FlowBaseFragment fragment) {
        instance = false;
        synchronized (stackList) {
            boolean isClear = false;
            ArrayList<FlowBaseFragment> lastList = stackList.get(stackList.size() - 1);
            if (lastList.isEmpty()) {
                lastList.add(fragment);
            } else {
                int tempIndex = 0;
                for (int x = 0; x <= lastList.size() - 1; x++) {
                    if (lastList.get(x).getClass().getName().equals(fragment.getClass().getName())) {
                        // clear all instance
                        isClear = true;
                        tempIndex = x;
                        break;
                    }
                }
                if (!isClear) {
                    lastList.add(fragment);
                } else {
                    if (close != null) {
                        close.show(lastList.get(tempIndex));
                        StackManager.isFirstClose = true;
                        for (int i = lastList.size() - 1; i > tempIndex; i--) {
                            close.close(lastList.get(i), false);
                        }
                        for (int j = lastList.size() - 1; j > tempIndex; j--) {
                            lastList.remove(j);
                        }
                    }

                }
            }
            return isClear;
        }
    }

    /**
     * singleInstance mode,Create a new task stack at a time.
     *
     * @param fragment fragment
     */
    public void putSingleInstance(FlowBaseFragment fragment) {
        instance = true;
        synchronized (stackList) {
            ArrayList<FlowBaseFragment> frags = new ArrayList<FlowBaseFragment>();
            frags.add(fragment);
            stackList.add(frags);
        }
    }

    public void closeFragment(FlowBaseFragment fragment) {
        synchronized (Stack.this) {
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

    public void setClose(IClose close) {
        this.close = close;
    }

    public Fragment getFrist() {
        return null;
    }

    public Fragment[] getLast() {
        if (instance) {
            synchronized (stackList) {
                Fragment[] fagArr = new Fragment[2];
                if (stackList.size() > 0) {
                    ArrayList<FlowBaseFragment> list = stackList.get(stackList.size() - 1);
                    if (list != null && (!list.isEmpty())) {
                        fagArr[0] = list.get(list.size() - 1);
                        if (list.size() > 1) {
                            fagArr[1] = list.get(list.size() - 2);
                        }
                    }
                }
                return fagArr;
            }
        } else {
            synchronized (stackList) {
                Fragment[] fagArr = new Fragment[2];
                boolean hasFirst = false;
                for (int x = stackList.size() - 1; x >= 0; x--) {
                    ArrayList<FlowBaseFragment> list = stackList.get(x);
                    if (list != null && (!list.isEmpty())) {
                        if (hasFirst) {
                            fagArr[1] = list.get(list.size() - 1);
                            break;
                        } else {
                            hasFirst = true;
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
    }
}
