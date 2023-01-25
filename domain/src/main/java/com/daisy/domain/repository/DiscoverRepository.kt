package com.daisy.domain.repository

import com.daisy.constants.Response
import com.daisy.domain.models.Genre
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {

    suspend fun fetchMovieGenres(): Flow<Response<List<Genre>>>
}