package com.tmdb.cache.dao.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tmdb.cache.entities.movies.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id IN (:ids)")
    fun getMovies(vararg ids: Int): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id=:id")
    fun getMovie(id: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovies(movieEntities: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun clearTable()
}
