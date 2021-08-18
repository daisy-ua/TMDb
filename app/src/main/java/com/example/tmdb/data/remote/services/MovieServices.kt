package com.example.tmdb.data.remote.services

import com.example.tmdb.data.remote.models.MovieDTO
import com.example.tmdb.data.remote.responses.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServices {
    @GET("movie/{movie_id}")
   suspend  fun getMovieDetails(@Path("movie_id") id: Int, ) : MovieDTO

    @GET("movie/popular")
    suspend  fun getPopularMovies() : MovieSearchResponse

    @GET("movie/{movie_id}/similar")
    suspend  fun getSimilarMovies(@Path("movie_id") id: Int) : MovieSearchResponse

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String) : MovieSearchResponse

    @GET("discover/movie")
    suspend fun discoverMovies(@Query("with_genres") genre_ids: String) : MovieSearchResponse
}