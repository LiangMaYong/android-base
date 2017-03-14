package com.liangmayong.base.basic.flow.interfaces;

import com.liangmayong.base.basic.flow.FlowBaseFragment;
import com.liangmayong.base.basic.flow.stack.StackManager;

/**
 * IStack
 *
 * @author LiangMaYong
 * @version 1.0
 */
public interface IFlow {

    /**
     * getLastFragment
     *
     * @return visible
     */
    FlowBaseFragment getLastFragment();

    /**
     * set anim
     *
     * @param nextIn  nextIn
     * @param nextOut nextOut
     * @param quitIn  quitIn
     * @param quitOut quitOut
     */
    void setFlowFragmentAnims(int nextIn, int nextOut, int quitIn, int quitOut);


    /**
     * getStackManager
     *
     * @return StackManager
     */
    StackManager getStackManager();
}
