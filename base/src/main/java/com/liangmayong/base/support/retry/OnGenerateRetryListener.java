package com.liangmayong.base.support.retry;

import android.view.View;

public class OnGenerateRetryListener {

    public int generateLoadingLayoutId() {
        return RetryManager.NO_LAYOUT_ID;
    }

    public int generateRetryLayoutId() {
        return RetryManager.NO_LAYOUT_ID;
    }

    public int generateEmptyLayoutId() {
        return RetryManager.NO_LAYOUT_ID;
    }

    public View generateLoadingView() {
        return null;
    }

    public View generateRetryView() {
        return null;
    }

    public View generateEmptyView() {
        return null;
    }

    /**
     * isSetLoadingLayout
     *
     * @return bool
     */
    public boolean isSetLoadingLayout() {
        if (generateLoadingLayoutId() != RetryManager.NO_LAYOUT_ID || generateLoadingView() != null)
            return true;
        return false;
    }

    /**
     * isSetRetryLayout
     *
     * @return bool
     */
    public boolean isSetRetryLayout() {
        if (generateRetryLayoutId() != RetryManager.NO_LAYOUT_ID || generateRetryView() != null)
            return true;
        return false;
    }

    /**
     * isSetEmptyLayout
     *
     * @return bool
     */
    public boolean isSetEmptyLayout() {
        if (generateEmptyLayoutId() != RetryManager.NO_LAYOUT_ID || generateEmptyView() != null)
            return true;
        return false;
    }


}