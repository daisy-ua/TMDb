package com.tmdb.repository.di

import com.tmdb.repository.repositories.movie_details_repository.MovieDetailsRepository
import com.tmdb.repository.repositories.movie_details_repository.MovieDetailsRepositoryImpl
import com.tmdb.repository.repositories.movie_paginated_repository.MoviePaginatedRepository
import com.tmdb.repository.repositories.movie_paginated_repository.MoviePaginatedRepositoryImpl
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
    abstract fun bindMoviePaginatedRepository(impl: MoviePaginatedRepositoryImpl): MoviePaginatedRepository

    @Binds
    @ViewModelScoped
    abstract fun bindMovieDetailsRepository(impl: MovieDetailsRepositoryImpl): MovieDetailsRepository
}