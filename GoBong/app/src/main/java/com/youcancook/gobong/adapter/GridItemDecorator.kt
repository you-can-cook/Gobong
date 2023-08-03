package com.youcancook.gobong.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class GridItemDecorator() :
    ItemDecoration() {
    var halfMargin = 0

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val index = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex

        val position = parent.getChildLayoutPosition(view)
        if (index == 0) {
            outRect.right = halfMargin
        } else {
            outRect.left = halfMargin
        }
        if (position < 2) {
            outRect.top = 0
        } else {
            outRect.top = halfMargin
        }
    }
}