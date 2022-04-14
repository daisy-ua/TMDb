package com.tmdb.cache.entities.movies

import androidx.room.ColumnInfo

data class MoviePaginatedEntity(
    val page: Int,

    val movies: List<MovieEntity>,

    @ColumnInfo(name = "total_results")
    val totalResults: Int,

    @ColumnInfo(name = "total_results")
    val totalPages: Int
)
