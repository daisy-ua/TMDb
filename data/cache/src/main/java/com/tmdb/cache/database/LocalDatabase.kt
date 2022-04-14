package com.tmdb.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tmdb.cache.converters.*
import com.tmdb.cache.dao.movies.MovieDao
import com.tmdb.cache.dao.movies.MovieDetailsDao
import com.tmdb.cache.entities.movies.MovieDetailsEntity
import com.tmdb.cache.entities.movies.MovieEntity

@Database(
    entities = [
        MovieEntity::class,
        MovieDetailsEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    MovieListConverter::class,
    GenreListConverter::class,
    CountryListConverter::class,
    SpokenLanguageListConverter::class,
    IntListConverter::class
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieDetailsDao(): MovieDetailsDao
}
