package com.example.tmdb.data.remote.responses

import com.example.tmdb.data.remote.models.MovieDTO
import com.google.gson.annotations.SerializedName


data class MovieSearchResponse (
    val page: Int,
    val results: List<MovieDTO>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)