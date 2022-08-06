package com.tmdb.repository.repositories.movie_details_repository

import com.tmdb.models.Video
import com.tmdb.models.movies.MovieDetails
import com.tmdb.models.movies.MoviePaginated
import com.tmdb.repository.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    suspend fun fetchMovieDetails(movieId: Int): Flow<Response<MovieDetails>?>

    suspend fun fetchMovieVideos(movieId: Int): Flow<Response<List<Video>>>

    suspend fun fetchMovieRecommendation(movieId: Int): Flow<Response<MoviePaginated>>
}