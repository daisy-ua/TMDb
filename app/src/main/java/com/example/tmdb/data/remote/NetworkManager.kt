package com.example.tmdb.data.remote

import com.example.tmdb.data.remote.services.GenreServices
import com.example.tmdb.data.remote.services.MovieServices
import retrofit2.Retrofit

object NetworkManager {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val movieServices: MovieServices by lazy { client.create(MovieServices::class.java) }
    val genreServices: GenreServices by lazy { client.create(GenreServices::class.java) }

    private val client: Retrofit
        get() = RetrofitClient.getClient(BASE_URL)
}