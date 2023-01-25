package com.daisy.domain.models.movies

import com.daisy.domain.models.Country
import com.daisy.domain.models.Genre
import com.daisy.domain.models.ProductionStatus
import com.daisy.domain.models.SpokenLanguage

data class MovieDetails(
    val adult: Boolean,

    val backdropPath: String?,

    val budget: Int,

    val genres: List<Genre>,

    val homepage: String?,

    val id: Int,

    val originalLanguage: String,

    val originalTitle: String,

    val overview: String?,

    val popularity: Float,

    val posterPath: String?,

    val productionCountries: List<Country>,

    val releaseDate: String,

    val revenue: Int,

    val runtime: Int?,

    val spokenLanguages: List<SpokenLanguage>,

    val status: ProductionStatus,

    val tagline: String?,

    val title: String,

    val video: Boolean,

    val voteAverage: Float,

    val voteCount: Int,
)
