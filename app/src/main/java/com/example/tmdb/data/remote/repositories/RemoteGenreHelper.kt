package com.example.tmdb.data.remote.repositories

import com.example.tmdb.data.models.Genre
import com.example.tmdb.data.remote.services.GenreServices

class RemoteGenreHelper(private val services: GenreServices) : RemoteDataSource<Genre> {
    override suspend fun getAll() : List<Genre> = services.getGenres().items
}