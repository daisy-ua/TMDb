package com.example.tmdb.ui.components

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun setupRecyclerView(
    rv: RecyclerView,
    context: Context?,
    adapter: RecyclerView.Adapter<*>,
    layout: LinearLayoutManager = LinearLayoutManager(
        context,
        LinearLayoutManager.HORIZONTAL,
        false
    ),
    itemDecoration: RecyclerView.ItemDecoration? = null,
) = with(rv) {
    setHasFixedSize(true)
    this.layoutManager = layout
    this.adapter = adapter
    itemDecoration?.let { itemDecoration ->
        addItemDecoration(itemDecoration)
    }
}