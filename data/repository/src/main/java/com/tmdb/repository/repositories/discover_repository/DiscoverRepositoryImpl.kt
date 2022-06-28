package com.tmdb.repository.repositories.discover_repository

import com.tmdb.models.Genre
import com.tmdb.network.services.GenreService
import com.tmdb.repository.mappers.toDomain
import com.tmdb.repository.utils.Response
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