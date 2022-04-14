package com.tmdb.network.models.movie

import com.google.gson.annotations.SerializedName
import com.tmdb.network.models.CountryDto
import com.tmdb.network.models.GenreDto
import com.tmdb.network.models.SpokenLanguageDto

data class MovieDetailsDto(
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    val budget: Int,

    val genres: List<GenreDto>,

    val homepage: String?,

    val id: Int,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    val overview: String?,

    val popularity: Float,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("production_countries")
    val productionCountries: List<CountryDto>,

    @SerializedName("release_date")
    val releaseDate: String,

    val revenue: Int,

    val runtime: Int?,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDto>,

    val status: String,

    val tagline: String?,

    val title: String,

    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Float,

    @SerializedName("vote_count")
    val voteCount: Int
)