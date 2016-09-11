package com.liangmayong.base.widget.anyretry;

import android.view.View;

public abstract class OnAnyRetryListener {
    public abstract void setRetryEvent(View retryView);

    public void setLoadingEvent(View loadingView) {
    }

    public void setEmptyEvent(View emptyView) {
    }

    public int generateLoadingLayoutId() {
        return AnyRetryManager.NO_LAYOUT_ID;
    }

    public int generateRetryLayoutId() {
        return AnyRetryManager.NO_LAYOUT_ID;
    }

    public int generateEmptyLayoutId() {
        return AnyRetryManager.NO_LAYOUT_ID;
    }

    public View generateLoadingLayout() {
        return null;
    }

    public View generateRetryLayout() {
        return null;
    }

    public View generateEmptyLayout() {
        return null;
    }

    public boolean isSetLoadingLayout() {
        if (generateLoadingLayoutId() != AnyRetryManager.NO_LAYOUT_ID || generateLoadingLayout() != null)
            return true;
        return false;
    }

    public boolean isSetRetryLayout() {
        if (generateRetryLayoutId() != AnyRetryManager.NO_LAYOUT_ID || generateRetryLayout() != null)
            return true;
        return false;
    }

    public boolean isSetEmptyLayout() {
        if (generateEmptyLayoutId() != AnyRetryManager.NO_LAYOUT_ID || generateEmptyLayout() != null)
            return true;
        return false;
    }


}