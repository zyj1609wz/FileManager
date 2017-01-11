package com.zyj.filemanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ${zhaoyanjun} on 2017/1/11.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private int divider_bottom = 0 ;
    private int divider_top = 0 ;
    private int divider_left = 0 ;
    private int divider_right = 0 ;

    public DividerItemDecoration(Context context ){
        divider_bottom = context.getResources().getDimensionPixelSize(R.dimen.divider_bottom);
        divider_top = context.getResources().getDimensionPixelSize(R.dimen.divider_top);
        divider_left = context.getResources().getDimensionPixelSize(R.dimen.divider_left);
        divider_right = context.getResources().getDimensionPixelSize(R.dimen.divider_right);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = divider_bottom ;//类似加了一个bottom padding
        outRect.top = divider_top  ; //类似加了一个bottom top
        outRect.left = divider_left  ; //类似加了一个bottom left
        outRect.right = divider_right  ; //类似加了一个bottom right
    }
}
