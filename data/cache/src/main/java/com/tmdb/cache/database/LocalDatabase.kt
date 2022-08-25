package com.tmdb.cache.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tmdb.cache.converters.DateTimeConverters
import com.tmdb.cache.dao.SavedMoviesDao
import com.tmdb.cache.entities.MovieEntity

@RequiresApi(Build.VERSION_CODES.O)
@Database(
    entities = [
        MovieEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTimeConverters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun savedMoviesDao(): SavedMoviesDao
}