package com.tmdb.cache.entities.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    val adult: Boolean,

    val overview: String?,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "genre_ids")
    val genreIds: List<Int>,

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo(name = "original_title")
    val originalTitle: String,

    @ColumnInfo(name = "original_language")
    val originalLanguage: String,

    val title: String,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,

    val popularity: Float,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Float,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int,

    val video: Boolean
)
