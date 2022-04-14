package com.tmdb.network.services

import com.tmdb.network.models.GenreDto
import retrofit2.http.GET

interface GenreService {

    @GET("genre/movie/list")
    suspend  fun getGenres() : List<Result<GenreDto>>
}