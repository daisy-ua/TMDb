package com.example.tmdb.ui.components

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

private val Float.toPx get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

class MarginItemDecoration(
    spaceSizeDp: Float,
) : RecyclerView.ItemDecoration() {
    private val spaceSize = spaceSizeDp.toPx

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) != 0) {
                left = spaceSize
            }
        }
    }
}