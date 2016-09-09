package com.liangmayong.base.weight.recycler.decortions;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by LiangMaYong on 2016/8/24.
 */
public class LinearDecoration extends RecyclerView.ItemDecoration {
    //space
    private int space = 0;

    public LinearDecoration(int spacingInPixels) {
        this.space = spacingInPixels;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            outRect.bottom = space;
        }
    }
}
