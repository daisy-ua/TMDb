package com.tmdb.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tmdb.cache.dao.SavedMoviesDao
import com.tmdb.cache.entities.MovieEntity

@Database(
    entities = [
        MovieEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun savedMoviesDao(): SavedMoviesDao
}