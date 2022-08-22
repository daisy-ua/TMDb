package com.tmdb.cache.di

import android.content.Context
import androidx.room.Room
import com.tmdb.cache.dao.SavedMoviesDao
import com.tmdb.cache.database.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): LocalDatabase {
        return Room.databaseBuilder(
            appContext,
            LocalDatabase::class.java,
            "tmdb.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSavedMoviesDao(database: LocalDatabase): SavedMoviesDao = database.savedMoviesDao()
}
