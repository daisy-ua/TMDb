package com.tmdb.cache.dao.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tmdb.cache.entities.movies.MovieDetailsEntity

@Dao
interface MovieDetailsDao {
    @Query("SELECT * FROM movie_details WHERE id=:id")
    fun getMovieDetails(id: Int): MovieDetailsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieDetails(movieDetails: MovieDetailsEntity)

    @Query("DELETE FROM movie_details")
    suspend fun clearTable()
}
