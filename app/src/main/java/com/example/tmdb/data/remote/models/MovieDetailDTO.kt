package com.example.tmdb.data.remote.models

import com.example.tmdb.data.local.entities.GenreDB
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieDetailDTO(
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
    val genreObjects: List<GenreDB>? = null,
    @Expose(deserialize = false, serialize = false)
    val genreIds: List<Int>,
    val runtime: Int?
) {
    fun getGenreId() = genreObjects?.map { it.id } ?: listOf()
}