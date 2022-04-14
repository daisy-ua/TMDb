package com.tmdb.repository.di

import com.tmdb.cache.dao.movies.MovieDao
import com.tmdb.cache.dao.movies.MovieDetailsDao
import com.tmdb.network.services.movies.MovieDetailsService
import com.tmdb.network.services.movies.MoviePaginatedService
import com.tmdb.repository.repositories.movie_details_repository.MovieDetailsRepositoryImpl
import com.tmdb.repository.repositories.movie_paginated_repository.MoviePaginatedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryImpModule {

    @Provides
    @Singleton
    fun provideMoviePaginatedRepository(
        localSource: MovieDao,
        remoteSource: MoviePaginatedService,
    ): MoviePaginatedRepositoryImpl = MoviePaginatedRepositoryImpl(localSource, remoteSource)

    @Provides
    @Singleton
    fun provideMovieDetailsRepository(
        localSource: MovieDetailsDao,
        remoteSource: MovieDetailsService,
    ): MovieDetailsRepositoryImpl = MovieDetailsRepositoryImpl(localSource, remoteSource)
}