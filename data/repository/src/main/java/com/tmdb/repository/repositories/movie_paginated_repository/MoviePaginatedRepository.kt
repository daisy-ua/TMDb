package com.tmdb.repository.repositories.movie_paginated_repository

import com.tmdb.models.movies.MoviePaginated
import com.tmdb.repository.utils.Response
import kotlinx.coroutines.flow.Flow

interface MoviePaginatedRepository {

    suspend fun fetchPopularMovies(page: Int = 1): Flow<Response<MoviePaginated>>

    suspend fun fetchTopRatedMovies(page: Int = 1): Flow<Response<MoviePaginated>>

    suspend fun fetchNowPlayingMovies(page: Int = 1): Flow<Response<MoviePaginated>>

    suspend fun fetchSimilarMovies(movieId: Int): Flow<Response<MoviePaginated>>

    suspend fun fetchSearchedMovies(query: String): Flow<Response<MoviePaginated>>

    suspend fun fetchDiscoveredMovies(
        genreIds: String,
        page: Int = 1,
    ): Flow<Response<MoviePaginated>>
}