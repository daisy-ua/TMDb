package com.tmdb.cache.dao

import androidx.room.*
import com.tmdb.cache.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedMoviesDao {
    @Query("SELECT * FROM saved")
    fun getSavedMovies(): Flow<List<MovieEntity>>

    @Query("SELECT EXISTS(SELECT * FROM saved WHERE id == :movieId)")
    fun isMovieSaved(movieId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteSavedMovie(movie: MovieEntity)
}