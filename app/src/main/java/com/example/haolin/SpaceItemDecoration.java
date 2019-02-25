package com.example.haolin;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

// the purpose of this class is to add spacing between genre buttons
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    // the spacing between genre buttons
    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // each button has the same spacing in four directions
        outRect.left = space;
        outRect.right = space;
        outRect.top = space;
        outRect.bottom = space;
    }

}

