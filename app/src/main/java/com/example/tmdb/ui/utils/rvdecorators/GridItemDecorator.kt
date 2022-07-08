package com.example.tmdb.ui.utils.rvdecorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecorator(
    private val spanCount: Int,
    spaceSizeDp: Float,
) : RecyclerView.ItemDecoration() {
    private val spacing = spaceSizeDp.toPx

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position: Int = parent.getChildAdapterPosition(view)

        val column = position % spanCount

        outRect.left = column * spacing / spanCount
        outRect.right = spacing - (column + 1) * spacing / spanCount

        if (position >= spanCount) {
            outRect.top = spacing
        }
    }
}