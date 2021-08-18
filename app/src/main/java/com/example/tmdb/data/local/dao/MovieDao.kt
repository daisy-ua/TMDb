package com.example.tmdb.data.local.dao

import androidx.room.*
import com.example.tmdb.data.local.entities.MovieDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM popular_movies")
    fun getPopular() : Flow<List<MovieDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieDB>)

    @Query("DELETE FROM popular_movies")
    suspend fun deleteAllMovies()
}