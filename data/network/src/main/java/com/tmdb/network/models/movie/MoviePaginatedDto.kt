package com.tmdb.network.models.movie

import com.google.gson.annotations.SerializedName
import com.tmdb.network.models.movie.MovieDto

data class MoviePaginatedDto(
    val page: Int,

    @SerializedName("results")
    val movies: List<MovieDto>,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int
)
