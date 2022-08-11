package com.tmdb.network.services.movies

import com.tmdb.network.models.movie.MoviePaginatedDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MoviePaginatedService {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
    ): MoviePaginatedDto

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
    ): MoviePaginatedDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): MoviePaginatedDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): MoviePaginatedDto

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int,
        @QueryMap options: Map<String, String>,
    ): MoviePaginatedDto
}