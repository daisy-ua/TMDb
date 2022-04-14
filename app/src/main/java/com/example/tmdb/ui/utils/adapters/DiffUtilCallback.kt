package com.example.tmdb.ui.utils.adapters

import androidx.recyclerview.widget.DiffUtil
import com.tmdb.models.movies.Movie

class DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem
}
