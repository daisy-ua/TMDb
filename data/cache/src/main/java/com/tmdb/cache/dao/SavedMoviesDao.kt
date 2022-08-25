package com.tmdb.cache.dao

import androidx.room.*
import com.tmdb.cache.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedMoviesDao {
    @Query("SELECT * FROM saved ORDER BY date_added DESC LIMIT :limit OFFSET :offset")
    suspend fun getSavedMovies(limit: Int, offset: Int): List<MovieEntity>

    @Query("SELECT EXISTS(SELECT * FROM saved WHERE id == :movieId)")
    fun isMovieSaved(movieId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedMovie(movie: MovieEntity)

    @Query("DELETE FROM saved WHERE id = :id")
    suspend fun deleteSavedMovie(id: Int)
}