package com.example.tmdb.data.remote.services

import com.example.tmdb.data.remote.models.GenreListDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreServices {

    @GET("genre/movie/list")
    suspend  fun getGenres(@Query("api_key") api_key: String) : GenreListDTO
}