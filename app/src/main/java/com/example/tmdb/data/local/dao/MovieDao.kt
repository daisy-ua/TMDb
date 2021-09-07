package com.example.tmdb.data.local.dao

import androidx.room.*
import com.example.tmdb.data.local.entities.MovieDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM popular_movies")
    fun getAll() : Flow<List<MovieDB>>

    @Query("SELECT * FROM popular_movies WHERE uid IN (:ids)")
    fun getById(vararg ids: Int) : Flow<List<MovieDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<MovieDB>)

    @Query("DELETE FROM popular_movies")
    suspend fun deleteAll()
}