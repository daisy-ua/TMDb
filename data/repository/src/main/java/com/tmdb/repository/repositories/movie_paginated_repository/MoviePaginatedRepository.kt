package com.tmdb.repository.repositories.movie_paginated_repository

import androidx.paging.PagingData
import com.tmdb.models.movies.Movie
import kotlinx.coroutines.flow.Flow

interface MoviePaginatedRepository {

    suspend fun fetchTopRatedMovies(): Flow<PagingData<Movie>>

    suspend fun fetchNowPlayingMovies(): Flow<PagingData<Movie>>

    suspend fun fetchTrendingMovies(): Flow<PagingData<Movie>>

    suspend fun fetchMovieDiscoverResult(
        options: Map<String, String> = mapOf(),
        includeAdult: Boolean = false,
    ): Flow<PagingData<Movie>>

    suspend fun fetchMovieSearchResult(
        query: String,
    ): Flow<PagingData<Movie>>
}