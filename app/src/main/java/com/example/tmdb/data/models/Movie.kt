package com.example.tmdb.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val voteAverage: Float,
    val releaseDate: String,
    val posterPath: String,
    val overview: String,
    val genre: List<Int>,
    val runtime: Int?
) : Parcelable
