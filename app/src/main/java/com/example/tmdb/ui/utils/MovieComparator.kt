package com.example.tmdb.ui.utils

import androidx.recyclerview.widget.DiffUtil
import com.daisy.domain.models.movies.Movie

object MovieComparator : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem
}
