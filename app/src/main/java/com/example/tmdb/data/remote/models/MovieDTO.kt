package com.example.tmdb.data.remote.models

import com.google.gson.annotations.SerializedName

data class MovieDTO(
    val id: Int,
    val title: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    val overview: String?,
    @SerializedName("genre_ids")
    val genres: List<Int>,
    val runtime: Int?
)