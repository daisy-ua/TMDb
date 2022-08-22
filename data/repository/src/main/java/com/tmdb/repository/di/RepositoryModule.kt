package com.tmdb.repository.di

import com.tmdb.repository.repositories.discover_repository.DiscoverRepository
import com.tmdb.repository.repositories.discover_repository.DiscoverRepositoryImpl
import com.tmdb.repository.repositories.movie_details_repository.MovieDetailsRepository
import com.tmdb.repository.repositories.movie_details_repository.MovieDetailsRepositoryImpl
import com.tmdb.repository.repositories.movie_paginated_repository.MoviePaginatedRepository
import com.tmdb.repository.repositories.movie_paginated_repository.MoviePaginatedRepositoryImpl
import com.tmdb.repository.repositories.moviepaginatedpreview.MoviePaginatedPreviewRepository
import com.tmdb.repository.repositories.moviepaginatedpreview.MoviePaginatedPreviewRepositoryImpl
import com.tmdb.repository.repositories.savedrepository.SavedRepository
import com.tmdb.repository.repositories.savedrepository.SavedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindMoviePaginatedRepository(
        impl: MoviePaginatedRepositoryImpl,
    ): MoviePaginatedRepository

    @Binds
    @ViewModelScoped
    abstract fun bindMoviePaginatedPreviewRepository(
        impl: MoviePaginatedPreviewRepositoryImpl,
    ): MoviePaginatedPreviewRepository

    @Binds
    @ViewModelScoped
    abstract fun bindMovieDetailsRepository(
        impl: MovieDetailsRepositoryImpl,
    ): MovieDetailsRepository

    @Binds
    @ViewModelScoped
    abstract fun DiscoverRepository(
        impl: DiscoverRepositoryImpl,
    ): DiscoverRepository

    @Binds
    @ViewModelScoped
    abstract fun SavedRepository(
        impl: SavedRepositoryImpl,
    ): SavedRepository
}