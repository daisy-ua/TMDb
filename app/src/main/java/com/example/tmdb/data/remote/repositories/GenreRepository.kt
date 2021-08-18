package com.example.tmdb.data.remote.repositories

import com.example.tmdb.data.models.Genre
import com.example.tmdb.data.remote.services.GenreServices

class GenreRepository(private val services: GenreServices) {
    suspend fun getGenres(api_key: String) : List<Genre> = services.getGenres().items
}