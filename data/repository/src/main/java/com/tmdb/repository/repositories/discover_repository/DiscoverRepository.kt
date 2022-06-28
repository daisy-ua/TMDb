package com.tmdb.repository.repositories.discover_repository

import com.tmdb.models.Genre
import com.tmdb.repository.utils.Response
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {

    suspend fun fetchMovieGenres(): Flow<Response<List<Genre>>>
}