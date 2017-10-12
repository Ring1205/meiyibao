package com.wmcd.myb.wigdet;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 设置recyclerview的item的间距
 * Created by 邓志宏 on 2017/2/21.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * The Spacing.
     */
    private int spacing;

    /**
     * Instantiates a new Space item decoration.
     *
     * @param spacing 每一个矩形的间距
     */
    public SpaceItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    /**
     * Gets item offsets.
     *
     * @param outRect the out rect
     * @param view    the view
     * @param parent  the parent
     * @param state   the state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.top = spacing;
        outRect.bottom = spacing;
    }
}
