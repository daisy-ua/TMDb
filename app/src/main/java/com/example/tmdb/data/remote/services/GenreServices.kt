package com.example.tmdb.data.remote.services

import com.example.tmdb.data.remote.models.GenreListDTO
import retrofit2.http.GET

interface GenreServices {

    @GET("genre/movie/list")
    suspend  fun getGenres() : GenreListDTO
}