package com.liangmayong.base.support.retry;

import android.view.View;

public interface OnShowRetryListener {

    void showRetryView(View retryView, Object object);

    void showLoadingView(View loadingView, Object object);

    void showEmptyView(View emptyView, Object object);

    void showContentView(View contentView, Object object);

}