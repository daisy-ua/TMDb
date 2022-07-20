package com.example.tmdb.ui.components.recyclerview

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun setupRecyclerView(
    rv: RecyclerView,
    context: Context?,
    adapter: RecyclerView.Adapter<*>,
    layout: RecyclerView.LayoutManager = LinearLayoutManager(
        context,
        LinearLayoutManager.HORIZONTAL,
        false
    ),
    hasFixedSize: Boolean = true,
    itemDecoration: RecyclerView.ItemDecoration? = null,
) = with(rv) {
    setHasFixedSize(hasFixedSize)
    this.layoutManager = layout
    this.adapter = adapter
    itemDecoration?.let { itemDecoration ->
        addItemDecoration(itemDecoration)
    }
}