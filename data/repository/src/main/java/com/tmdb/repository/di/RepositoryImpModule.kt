package com.tmdb.repository.di

import com.tmdb.cache.dao.SavedMoviesDao
import com.tmdb.network.services.GenreService
import com.tmdb.network.services.movies.MovieDetailsService
import com.tmdb.network.services.movies.MoviePaginatedService
import com.tmdb.repository.repositories.discover_repository.DiscoverRepositoryImpl
import com.tmdb.repository.repositories.movie_details_repository.MovieDetailsRepositoryImpl
import com.tmdb.repository.repositories.movie_paginated_repository.MoviePaginatedRepositoryImpl
import com.tmdb.repository.repositories.moviepaginatedpreview.MoviePaginatedPreviewRepositoryImpl
import com.tmdb.repository.repositories.savedrepository.SavedRepositoryImpl
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
        remoteSource: MoviePaginatedService,
    ): MoviePaginatedRepositoryImpl = MoviePaginatedRepositoryImpl(remoteSource)

    @Provides
    @Singleton
    fun provideMoviePaginatedPreviewRepository(
        remoteSource: MoviePaginatedService,
    ): MoviePaginatedPreviewRepositoryImpl = MoviePaginatedPreviewRepositoryImpl(remoteSource)

    @Provides
    @Singleton
    fun provideMovieDetailsRepository(
        remoteSource: MovieDetailsService,
    ): MovieDetailsRepositoryImpl = MovieDetailsRepositoryImpl(remoteSource)

    @Provides
    @Singleton
    fun provideDiscoverRepository(
        genreDataSource: GenreService,
    ): DiscoverRepositoryImpl = DiscoverRepositoryImpl(genreDataSource)

    @Provides
    @Singleton
    fun provideSavedRepository(
        localDataSource: SavedMoviesDao,
    ): SavedRepositoryImpl = SavedRepositoryImpl(localDataSource)
}