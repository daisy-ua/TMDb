package com.tmdb.repository.repositories.movie_paginated_repository

import androidx.paging.PagingData
import com.tmdb.models.movies.Movie
import com.tmdb.models.movies.MoviePaginated
import com.tmdb.repository.utils.Response
import kotlinx.coroutines.flow.Flow

interface MoviePaginatedRepository {

    suspend fun fetchPopularMovies(page: Int = 1): Flow<Response<MoviePaginated>>

    suspend fun fetchTopRatedMovies(page: Int = 1): Flow<Response<MoviePaginated>>

    suspend fun fetchNowPlayingMovies(page: Int = 1): Flow<Response<MoviePaginated>>

    suspend fun fetchSimilarMovies(movieId: Int): Flow<Response<MoviePaginated>>

    suspend fun fetchMovieDiscoverResult(
        options: Map<String, String> = mapOf(),
        includeAdult: Boolean = false,
    ): Flow<PagingData<Movie>>

    suspend fun fetchMovieSearchResult(
        query: String,
    ): Flow<PagingData<Movie>>
}