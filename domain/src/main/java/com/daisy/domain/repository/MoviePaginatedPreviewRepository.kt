package com.daisy.domain.repository

import com.daisy.constants.Response
import com.daisy.domain.models.movies.MoviePaginated
import kotlinx.coroutines.flow.Flow

interface MoviePaginatedPreviewRepository {

    suspend fun fetchTopRatedMoviesPreview(): Flow<Response<MoviePaginated>>

    suspend fun fetchNowPlayingMoviesPreview(): Flow<Response<MoviePaginated>>

    suspend fun fetchTrendingMoviesPreview(): Flow<Response<MoviePaginated>>
}