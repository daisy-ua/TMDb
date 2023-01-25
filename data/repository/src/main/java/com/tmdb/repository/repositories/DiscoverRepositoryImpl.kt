package com.tmdb.repository.repositories

import com.daisy.constants.Response
import com.daisy.domain.models.Genre
import com.daisy.domain.repository.DiscoverRepository
import com.tmdb.network.services.GenreService
import com.tmdb.repository.mappers.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val genreDataSource: GenreService,
) : DiscoverRepository {
    override suspend fun fetchMovieGenres(): Flow<Response<List<Genre>>> {
        return flow {
            val remoteResponse = genreDataSource.fetchMovieGenres()
            emit(Response.Success(remoteResponse.toDomain()))
        }.flowOn(Dispatchers.IO)
    }
}