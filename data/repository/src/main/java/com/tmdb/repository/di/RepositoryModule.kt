package com.tmdb.repository.di

import com.daisy.domain.repository.*
import com.tmdb.repository.repositories.DiscoverRepositoryImpl
import com.tmdb.repository.repositories.MovieDetailsRepositoryImpl
import com.tmdb.repository.repositories.MoviePaginatedPreviewRepositoryImpl
import com.tmdb.repository.repositories.moviepaginatedrepository.MoviePaginatedRepositoryImpl
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