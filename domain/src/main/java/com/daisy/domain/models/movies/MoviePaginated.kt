package com.daisy.domain.models.movies

data class MoviePaginated(
    val page: Int,

    val movies: List<Movie>,

    val totalResults: Int,

    val totalPages: Int,
)
