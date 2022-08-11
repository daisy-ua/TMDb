package com.tmdb.repository.repositories.moviepaginatedpreview

import com.tmdb.models.movies.MoviePaginated
import com.tmdb.repository.utils.Response
import kotlinx.coroutines.flow.Flow

interface MoviePaginatedPreviewRepository {

    suspend fun fetchTopRatedMoviesPreview(): Flow<Response<MoviePaginated>>

    suspend fun fetchNowPlayingMoviesPreview(): Flow<Response<MoviePaginated>>

    suspend fun fetchTrendingMoviesPreview(): Flow<Response<MoviePaginated>>
}