package com.liangmayong.base.widget.recycler.decortions;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by LiangMaYong on 2016/8/24.
 */
public class SpaceDecoration extends RecyclerView.ItemDecoration {
    //space
    private int space = 0;

    public SpaceDecoration(int spacingInPixels) {
        this.space = spacingInPixels / 2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        // Add top margin only for the first item to avoid double space between items
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            outRect.top = space;
            parent.setPadding(space, space, space, space);
        }
    }
}
