package com.tmdb.network.services.movies

import com.tmdb.network.models.movie.MovieDetailsDto
import com.tmdb.network.models.movie.MovieDto
import com.tmdb.network.models.movie.MoviePaginatedDto
import com.tmdb.network.models.video.VideoListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailsService {

    @GET("movie/{movie_id}")
    suspend fun getMoviePreview(
        @Path("movie_id") id: Int,
    ): MovieDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
    ): MovieDetailsDto

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") id: Int,
    ): VideoListDto

    @GET("movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendations(
        @Path("movie_id") id: Int,
    ): MoviePaginatedDto
}