package com.tmdb.repository.repositories.movie_details_repository

import com.tmdb.cache.dao.movies.MovieDetailsDao
import com.tmdb.models.Video
import com.tmdb.models.movies.MovieDetails
import com.tmdb.models.movies.MoviePaginated
import com.tmdb.network.services.movies.MovieDetailsService
import com.tmdb.repository.mappers.toDomain
import com.tmdb.repository.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val localDataSource: MovieDetailsDao,
    private val remoteDatasource: MovieDetailsService,
) : MovieDetailsRepository {

    override suspend fun fetchMovieDetails(movieId: Int): Flow<Response<MovieDetails>?> {
        return flow<Response<MovieDetails>?> {
            emit(Response.Loading(null))

            val flow = try {
                val networkResponse = remoteDatasource.getMovieDetails(movieId)

                flow { emit(networkResponse) }
                    .map { Response.Success(it.toDomain()) }

            } catch (throwable: Throwable) {
                flow { emit(Response.Error(throwable)) }
            }

            emitAll(flow)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchMovieVideos(movieId: Int): Flow<Response<List<Video>>> {
        return flow<Response<List<Video>>> {
            emit(Response.Loading(null))

            val flow = try {
                val networkResponse = remoteDatasource.getMovieVideos(movieId)

                flow { emit(networkResponse) }
                    .map { Response.Success(it.toDomain()) }

            } catch (throwable: Throwable) {
                flow { emit(Response.Error(throwable)) }
            }

            emitAll(flow)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchMovieRecommendation(movieId: Int): Flow<Response<MoviePaginated>> {
        return flow<Response<MoviePaginated>> {
            emit(Response.Loading(null))

            val flow = try {
                val networkResponse = remoteDatasource.getMovieRecommendations(movieId)

                flow { emit(networkResponse) }
                    .map { Response.Success(it.toDomain()) }

            } catch (throwable: Throwable) {
                flow { emit(Response.Error(throwable)) }
            }

            emitAll(flow)
        }.flowOn(Dispatchers.IO)
    }
}