package com.daisy.domain.models.movies

data class Movie(
    val posterPath: String?,

    val adult: Boolean,

    val overview: String?,

    val releaseDate: String,

    val genreIds: List<Int>,

    val id: Int,

    val originalTitle: String,

    val originalLanguage: String,

    val title: String,

    val backdropPath: String?,

    val popularity: Float,

    val voteAverage: Float,

    val voteCount: Int,

    val video: Boolean,
)
