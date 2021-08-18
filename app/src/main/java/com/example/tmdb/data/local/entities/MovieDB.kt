package com.example.tmdb.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tmdb.data.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Entity(tableName = "popular_movies")
data class MovieDB(
    @PrimaryKey val uid: Int,
    val title: String,
    @ColumnInfo(name="original_title") val originalTitle: String,
    @ColumnInfo(name="vote_average") val voteAverage: Float,
    @ColumnInfo(name="release_date") val releaseDate: String,
    @ColumnInfo(name="poster_path") val posterPath: String,
    val overview: String,
//    val genre: List<Int>
) {
    companion object {
        fun from(movie: Movie): MovieDB {
            return MovieDB(movie.id, movie.title, movie.originalTitle, movie.voteAverage,
                movie.releaseDate, movie.posterPath, movie.overview)
        }
    }

    fun toMovie(): Movie {
        return Movie(uid, title, originalTitle, voteAverage,
            releaseDate, posterPath, overview, listOf())
    }
}

fun Flow<List<MovieDB>>.toMovieDomain() : Flow<List<Movie>> =
    this.map { list -> list.map { it.toMovie() } }

fun List<Movie>.toMovieDB() : List<MovieDB> =
    this.map { MovieDB.from(it) }