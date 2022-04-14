package com.tmdb.repository.repositories.movie_details_repository

import com.tmdb.models.movies.MovieDetails
import com.tmdb.repository.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    suspend fun fetchMovieDetails(movieId: Int): Flow<Response<MovieDetails>?>
}