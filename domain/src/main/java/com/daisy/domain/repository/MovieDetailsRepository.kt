package com.daisy.domain.repository

import com.daisy.constants.Response
import com.daisy.domain.models.Video
import com.daisy.domain.models.movies.MovieDetails
import com.daisy.domain.models.movies.MoviePaginated
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    suspend fun fetchMovieDetails(movieId: Int): Flow<Response<MovieDetails>?>

    suspend fun fetchMovieVideos(movieId: Int): Flow<Response<List<Video>>>

    suspend fun fetchMovieRecommendation(movieId: Int): Flow<Response<MoviePaginated>>
}