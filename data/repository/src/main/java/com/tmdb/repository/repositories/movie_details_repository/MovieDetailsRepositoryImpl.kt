package com.tmdb.repository.repositories.movie_details_repository

import com.tmdb.cache.dao.movies.MovieDetailsDao
import com.tmdb.models.Video
import com.tmdb.models.movies.MovieDetails
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
        return flow {
//            val cacheResponseEntity = localDataSource.getMovieDetails(movieId)

//            val cacheResponse = cacheResponseEntity?.toDomain()

//            emit(Response.Loading(cacheResponse))

            val fetchResponse = remoteDatasource.getMovieDetails(movieId)

//            fetchResponse.let {
//                localDataSource.saveMovieDetails(it.toEntity())
//                emit(Response.Success(it.toDomain()))
//            }

            emit(fetchResponse.let {
                Response.Success(it.toDomain())
            })
//            emit(fetchResponse.let {
//                Response.Loading(it.toDomain())
//            })

        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchMovieVideos(movieId: Int): Flow<Response<List<Video>>> {
        return flow<Response<List<Video>>> {
            emit(Response.Loading(null))

            val flow = try {
                val fetchResponse = flow { emit(remoteDatasource.getMovieVideos(movieId)) }

                fetchResponse.map { Response.Success(it.toDomain()) }

            } catch (throwable: Throwable) {
                flow { emit(Response.Error(throwable)) }
            }

            emitAll(flow)
        }.flowOn(Dispatchers.IO)
    }
}