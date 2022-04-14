package com.tmdb.cache.entities.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tmdb.cache.entities.CountryEntity
import com.tmdb.cache.entities.GenreEntity
import com.tmdb.cache.entities.SpokenLanguageEntity

@Entity(tableName = "movie_details")
data class MovieDetailsEntity(
    val adult: Boolean,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,

    val budget: Int,

    @ColumnInfo(name = "genres")
    val genres: List<GenreEntity>,

    val homepage: String?,

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo(name = "original_language")
    val originalLanguage: String,

    @ColumnInfo(name = "original_title")
    val originalTitle: String,

    val overview: String?,

    val popularity: Float,

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    @ColumnInfo(name = "production_countries")
    val productionCountries: List<CountryEntity>,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    val revenue: Int,

    val runtime: Int?,

    @ColumnInfo(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguageEntity>,

    val status: String,

    val tagline: String?,

    val title: String,

    val video: Boolean,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Float,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int
)
