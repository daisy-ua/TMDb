package com.example.tmdb.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tmdb.data.local.converters.IntListConverter
import com.example.tmdb.data.local.dao.GenreDao
import com.example.tmdb.data.local.dao.MovieDao
import com.example.tmdb.data.local.entities.GenreDB
import com.example.tmdb.data.local.entities.MovieDB

@Database(entities = [MovieDB::class, GenreDB::class], version = 1, exportSchema = false)
@TypeConverters(IntListConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun genreDao() : GenreDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            if (INSTANCE == null) {
                synchronized(LocalDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            LocalDatabase::class.java,
                            "tmdb.db"
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() { INSTANCE = null }
    }
}