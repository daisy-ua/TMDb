package com.tmdb.repository.repositories.movie_paginated_repository

import com.tmdb.models.movies.MoviePaginated
import com.tmdb.repository.utils.Response
import kotlinx.coroutines.flow.Flow

interface MoviePaginatedRepository {

    suspend fun fetchPopularMovies(page: Int = 1): Flow<Response<MoviePaginated>>

    suspend fun fetchSimilarMovies(movieId: Int): Flow<Response<MoviePaginated>>

    suspend fun fetchSearchedMovies(
        query: String,
        page: Int = 1,
    ): Flow<Response<MoviePaginated>>

    suspend fun fetchDiscoveredMovies(
        sortBy: String?,
        genreIds: String?,
        page: Int = 1,
        includeAdult: Boolean = false,
    ): Flow<Response<MoviePaginated>>
}