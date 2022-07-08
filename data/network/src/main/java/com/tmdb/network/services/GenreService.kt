package com.tmdb.network.services

import com.tmdb.network.models.genre.GenreListDto
import retrofit2.http.GET

interface GenreService {

    @GET("genre/movie/list")
    suspend fun fetchMovieGenres(): GenreListDto
}