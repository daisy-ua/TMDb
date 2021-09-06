package com.example.tmdb.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.tmdb.data.local.converters.IntListConverter

@Entity(tableName = "popular_movies")
data class MovieDB(
    @PrimaryKey val uid: Int,
    val title: String,
    @ColumnInfo(name="original_title") val originalTitle: String,
    @ColumnInfo(name="vote_average") val voteAverage: Float,
    @ColumnInfo(name="release_date") val releaseDate: String,
    @ColumnInfo(name="poster_path") val posterPath: String,
    val overview: String,
    val genre: List<Int>,
    val runtime: Int?
)