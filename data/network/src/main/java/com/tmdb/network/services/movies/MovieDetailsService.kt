package com.tmdb.network.services.movies

import com.tmdb.network.models.movie.MovieDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailsService {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int
    ): MovieDetailsDto
}