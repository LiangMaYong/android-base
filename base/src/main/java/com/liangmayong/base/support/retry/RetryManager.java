package com.liangmayong.base.support.retry;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangmayong.base.R;

/**
 * Created by zhy on 15/8/27.
 */
public class RetryManager {
    public static final int NO_LAYOUT_ID = 0;
    private final static int BASE_DEFAULT_LOADING_LAYOUT_ID = R.layout.base_default_retry_layout;
    private final static int BASE_DEFAULT_RETRY_LAYOUT_ID = R.layout.base_default_retry_layout;
    private final static int BASE_DEFAULT_EMPTY_LAYOUT_ID = R.layout.base_default_retry_layout;

    /**
     * generate
     *
     * @param activityOrFragmentOrView activityOrFragmentOrView
     * @param listener                 listener
     * @return RetryManager
     */
    public static RetryManager generate(Object activityOrFragmentOrView, OnGenerateRetryListener listener) {
        return new RetryManager(activityOrFragmentOrView, listener);
    }

    /**
     * generate
     *
     * @param activityOrFragmentOrView activityOrFragmentOrView
     * @return RetryManager
     */
    public static RetryManager generate(Object activityOrFragmentOrView) {
        return new RetryManager(activityOrFragmentOrView, null);
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////// Static
    /////////////////////////////////////////////////////////////////////////////////

    private RetryLayout mLoadingAndRetryLayout = null;

    // defaultShowRetryListener
    private OnShowRetryListener defaultShowRetryListener = new OnShowRetryListener() {
        @Override
        public void showRetryView(View retryView, Object object) {
            TextView textView = (TextView) retryView.findViewById(R.id.base_default_retry_text);
            ImageView imageView = (ImageView) retryView.findViewById(R.id.base_default_retry_img);
            textView.setText(object + "");
            imageView.setImageResource(R.mipmap.base_default_ic_retry_img);
        }

        @Override
        public void showLoadingView(View loadingView, Object object) {
            TextView textView = (TextView) loadingView.findViewById(R.id.base_default_retry_text);
            ImageView imageView = (ImageView) loadingView.findViewById(R.id.base_default_retry_img);
            textView.setText(object + "");
            imageView.setImageResource(R.mipmap.base_default_ic_loading_img);
        }

        @Override
        public void showEmptyView(View emptyView, Object object) {
            TextView textView = (TextView) emptyView.findViewById(R.id.base_default_retry_text);
            ImageView imageView = (ImageView) emptyView.findViewById(R.id.base_default_retry_img);
            textView.setText(object + "");
            imageView.setImageResource(R.mipmap.base_default_ic_empty_img);
        }

        @Override
        public void showContentView(View contentView, Object object) {
        }
    };

    /**
     * RetryManager
     *
     * @param activityOrFragmentOrView activityOrFragmentOrView
     * @param listener                 listener
     */
    private RetryManager(Object activityOrFragmentOrView, OnGenerateRetryListener listener) {
        if (listener == null) {
            listener = new OnGenerateRetryListener();
        }
        ViewGroup contentParent = null;
        Context context;
        if (activityOrFragmentOrView instanceof Activity) {
            Activity activity = (Activity) activityOrFragmentOrView;
            context = activity;
            contentParent = (ViewGroup) activity.findViewById(android.R.id.content);
        } else if (activityOrFragmentOrView instanceof Fragment) {
            Fragment fragment = (Fragment) activityOrFragmentOrView;
            context = fragment.getActivity();
            contentParent = (ViewGroup) (fragment.getView().getParent());
        } else if (activityOrFragmentOrView instanceof View) {
            View view = (View) activityOrFragmentOrView;
            contentParent = (ViewGroup) (view.getParent());
            context = view.getContext();
        } else {
            throw new IllegalArgumentException("the argument's type must be Fragment or Activity: init(context)");
        }
        int childCount = contentParent.getChildCount();
        //get contentParent
        int index = 0;
        View oldContent;
        if (activityOrFragmentOrView instanceof View) {
            oldContent = (View) activityOrFragmentOrView;
            for (int i = 0; i < childCount; i++) {
                if (contentParent.getChildAt(i) == oldContent) {
                    index = i;
                    break;
                }
            }
        } else {
            oldContent = contentParent.getChildAt(0);
        }
        contentParent.removeView(oldContent);
        //setup content layout
        RetryLayout loadingAndRetryLayout = new RetryLayout(context);

        ViewGroup.LayoutParams lp = oldContent.getLayoutParams();
        contentParent.addView(loadingAndRetryLayout, index, lp);
        loadingAndRetryLayout.setContentView(oldContent);
        // setup loading,retry,empty layout
        setupLoadingLayout(listener, loadingAndRetryLayout);
        setupRetryLayout(listener, loadingAndRetryLayout);
        setupEmptyLayout(listener, loadingAndRetryLayout);
        mLoadingAndRetryLayout = loadingAndRetryLayout;
    }

    /**
     * setupEmptyLayout
     *
     * @param listener              listener
     * @param loadingAndRetryLayout loadingAndRetryLayout
     */
    private void setupEmptyLayout(OnGenerateRetryListener listener, RetryLayout loadingAndRetryLayout) {
        if (listener.isSetEmptyLayout()) {
            int layoutId = listener.generateEmptyLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                loadingAndRetryLayout.setEmptyView(layoutId);
            } else {
                loadingAndRetryLayout.setEmptyView(listener.generateEmptyView());
            }
        } else {
            if (BASE_DEFAULT_EMPTY_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setEmptyView(BASE_DEFAULT_EMPTY_LAYOUT_ID);
        }
    }

    /**
     * setupLoadingLayout
     *
     * @param listener              listener
     * @param loadingAndRetryLayout loadingAndRetryLayout
     */
    private void setupLoadingLayout(OnGenerateRetryListener listener, RetryLayout loadingAndRetryLayout) {
        if (listener.isSetLoadingLayout()) {
            int layoutId = listener.generateLoadingLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                loadingAndRetryLayout.setLoadingView(layoutId);
            } else {
                loadingAndRetryLayout.setLoadingView(listener.generateLoadingView());
            }
        } else {
            if (BASE_DEFAULT_LOADING_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setLoadingView(BASE_DEFAULT_LOADING_LAYOUT_ID);
        }
    }

    /**
     * setupRetryLayout
     *
     * @param listener              listener
     * @param loadingAndRetryLayout loadingAndRetryLayout
     */
    private void setupRetryLayout(OnGenerateRetryListener listener, RetryLayout loadingAndRetryLayout) {
        if (listener.isSetRetryLayout()) {
            int layoutId = listener.generateRetryLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                loadingAndRetryLayout.setLoadingView(layoutId);
            } else {
                loadingAndRetryLayout.setLoadingView(listener.generateRetryView());
            }
        } else {
            if (BASE_DEFAULT_RETRY_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setRetryView(BASE_DEFAULT_RETRY_LAYOUT_ID);
        }
    }

    /**
     * showLoading
     */
    public void showLoading(Object object) {
        mLoadingAndRetryLayout.showLoading();
        defaultShowRetryListener.showRetryView(mLoadingAndRetryLayout.getLoadingView(), object);
    }

    /**
     * showRetry
     */
    public void showRetry(Object object) {
        mLoadingAndRetryLayout.showRetry();
        defaultShowRetryListener.showRetryView(mLoadingAndRetryLayout.getRetryView(), object);
    }

    /**
     * showEmpty
     */
    public void showEmpty(Object object) {
        mLoadingAndRetryLayout.showEmpty();
        defaultShowRetryListener.showRetryView(mLoadingAndRetryLayout.getEmptyView(), object);
    }

    /**
     * showContent
     */
    public void showContent(Object object) {
        mLoadingAndRetryLayout.showContent();
        defaultShowRetryListener.showContentView(mLoadingAndRetryLayout.getContentView(), object);
    }

}
