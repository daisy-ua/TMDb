package com.tmdb.repository.repositories.movie_details_repository

import com.tmdb.cache.dao.movies.MovieDetailsDao
import com.tmdb.models.movies.MovieDetails
import com.tmdb.network.services.movies.MovieDetailsService
import com.tmdb.repository.mappers.toDomain
import com.tmdb.repository.mappers.toEntity
import com.tmdb.repository.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val localDataSource: MovieDetailsDao,
    private val remoteDatasource: MovieDetailsService
) : MovieDetailsRepository {

    override suspend fun fetchMovieDetails(movieId: Int): Flow<Response<MovieDetails>?> {
        return flow {
            val cacheResponseEntity = localDataSource.getMovieDetails(movieId)

            val cacheResponse = cacheResponseEntity?.toDomain()

            emit(Response.Loading(cacheResponse))

            val fetchResponse = remoteDatasource.getMovieDetails(movieId)

            fetchResponse.let {
                localDataSource.saveMovieDetails(it.toEntity())
                emit(Response.Success(it.toDomain()))
            }

            emit(fetchResponse.let {
                Response.Loading(it.toDomain())
            })

        }.flowOn(Dispatchers.IO)
    }
}