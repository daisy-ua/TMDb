package com.tmdb.repository.repositories

import com.daisy.constants.Response
import com.daisy.domain.models.movies.MoviePaginated
import com.daisy.domain.repository.MoviePaginatedPreviewRepository
import com.tmdb.network.services.movies.MoviePaginatedService
import com.tmdb.repository.mappers.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MoviePaginatedPreviewRepositoryImpl @Inject constructor(
    private val remoteDataSource: MoviePaginatedService,
) : MoviePaginatedPreviewRepository {
    override suspend fun fetchTopRatedMoviesPreview(): Flow<Response<MoviePaginated>> {
        return flow<Response<MoviePaginated>> {
            emit(Response.Loading(null))

            val flow = try {
                val networkResponse = remoteDataSource.getTopRatedMovies(1)

                flow { emit(networkResponse) }
                    .map { Response.Success(it.toDomain()) }

            } catch (throwable: Throwable) {
                flow { emit(Response.Error(throwable)) }
            }

            emitAll(flow)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchNowPlayingMoviesPreview(): Flow<Response<MoviePaginated>> {
        return flow<Response<MoviePaginated>> {
            emit(Response.Loading(null))

            val flow = try {
                val networkResponse = remoteDataSource.getNowPlayingMovies(1)

                flow { emit(networkResponse) }
                    .map { Response.Success(it.toDomain()) }

            } catch (throwable: Throwable) {
                flow { emit(Response.Error(throwable)) }
            }

            emitAll(flow)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchTrendingMoviesPreview(): Flow<Response<MoviePaginated>> {
        return flow<Response<MoviePaginated>> {
            emit(Response.Loading(null))

            val flow = try {
                val networkResponse = remoteDataSource.getTrendingMovies(1)

                flow { emit(networkResponse) }
                    .map { Response.Success(it.toDomain()) }

            } catch (throwable: Throwable) {
                flow { emit(Response.Error(throwable)) }
            }

            emitAll(flow)
        }.flowOn(Dispatchers.IO)
    }
}