package com.example.tmdb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.data.local.entities.GenreDB
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres")
    fun getAll() : Flow<List<GenreDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genres: List<GenreDB>)

    @Query("DELETE FROM genres")
    suspend fun deleteAll()
}