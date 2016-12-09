package com.liangmayong.base.web.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.liangmayong.base.R;
import com.liangmayong.base.utils.DimenUtils;
import com.liangmayong.base.widget.skinview.SkinRippleButton;
import com.liangmayong.base.widget.superlistview.SuperListView;

import java.util.List;

/**
 * Created by LiangMaYong on 2016/11/9.
 */

public class DefaultMoreFragment extends DialogFragment {

    //fragment tag
    private static final String TAG = "DefaultMoreFragment";

    /**
     * show
     *
     * @param activity activity
     * @param items    items
     */
    public static DefaultMoreFragment show(FragmentActivity activity, String title, List<SuperListView.Item> items) {
        try {
            synchronized (activity) {
                DialogFragment dialogFragment = (DialogFragment) activity.getSupportFragmentManager()
                        .findFragmentByTag(TAG);
                if (dialogFragment != null) {
                    ((DefaultMoreFragment) dialogFragment).setItems(items);
                    ((DefaultMoreFragment) dialogFragment).setTitle(title);
                    if (dialogFragment.isAdded()) {
                        activity.getSupportFragmentManager().beginTransaction().show(dialogFragment).commit();
                    }
                } else {
                    dialogFragment = new DefaultMoreFragment();
                    ((DefaultMoreFragment) dialogFragment).setItems(items);
                    ((DefaultMoreFragment) dialogFragment).setTitle(title);
                    dialogFragment.show(activity.getSupportFragmentManager(), TAG);
                }
                return (DefaultMoreFragment) dialogFragment;
            }
        } catch (Exception e) {
        }
        return null;
    }

    @SuppressLint("ValidFragment")
    private DefaultMoreFragment() {
    }

    private List<SuperListView.Item> items = null;
    private String title = "";

    public void setTitle(String title) {
        if (viewHolder != null) {
            viewHolder.base_more_title.setText(title);
        } else {
            this.title = title;
        }
    }

    /**
     * setItems
     *
     * @param items items
     */
    public void setItems(List<SuperListView.Item> items) {
        if (viewHolder != null) {
            viewHolder.base_more_list.getPool().clear();
            viewHolder.base_more_list.getPool().addAll(items);
            viewHolder.base_more_list.getPool().notifyDataSetChanged();
        } else {
            this.items = items;
        }
    }

    /**
     * cancel
     *
     * @param activity activity
     */
    public static void cancel(FragmentActivity activity) {
        try {
            DialogFragment dialogFragment = (DialogFragment) activity.getSupportFragmentManager()
                    .findFragmentByTag(TAG);
            if (dialogFragment != null) {
                activity.getSupportFragmentManager().beginTransaction().remove(dialogFragment).commit();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout(dm.widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.base_default_dialog_more, null);
        viewHolder = new ViewHolder(view);
        setItems(items);
        setTitle(title);
        return view;
    }

    private ViewHolder viewHolder;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * dip2px
     *
     * @param context context
     * @param dpValue dpValue
     * @return px
     */
    protected int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public class ViewHolder {
        public View rootView;
        public SuperListView base_more_list;
        public SkinRippleButton base_more_close_btn;
        public TextView base_more_title;

        public ViewHolder(View rootView) {

            this.rootView = rootView;
            this.base_more_title = (TextView) rootView.findViewById(R.id.base_dialog_more_title);
            this.base_more_list = (SuperListView) rootView.findViewById(R.id.base_dialog_more_list);
            this.base_more_list.setStaggeredEnable(true);
            this.base_more_list.setOrientation(SuperListView.HORIZONTAL);
            this.base_more_list.setColumnCount(1);
            this.base_more_list.setDecorationSize(DimenUtils.dip2px(rootView.getContext(), 10));
            this.base_more_close_btn = (SkinRippleButton) rootView.findViewById(R.id.base_dialog_more_close_btn);
            this.base_more_close_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DefaultMoreFragment.cancel(getActivity());
                }
            });
        }
    }
}
