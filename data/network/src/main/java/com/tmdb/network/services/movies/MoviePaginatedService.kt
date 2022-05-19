package com.tmdb.network.services.movies

import com.tmdb.network.models.movie.MoviePaginatedDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviePaginatedService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MoviePaginatedDto

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int
    ): MoviePaginatedDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): MoviePaginatedDto

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") id: Int
    ): MoviePaginatedDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String
    ): MoviePaginatedDto

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreIds: String
    ): MoviePaginatedDto
}