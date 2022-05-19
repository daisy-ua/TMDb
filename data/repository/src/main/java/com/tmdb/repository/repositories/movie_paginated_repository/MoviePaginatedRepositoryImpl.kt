package com.tmdb.repository.repositories.movie_paginated_repository

import android.util.Log
import com.tmdb.cache.dao.movies.MovieDao
import com.tmdb.models.movies.MoviePaginated
import com.tmdb.network.services.movies.MoviePaginatedService
import com.tmdb.repository.mappers.toDomain
import com.tmdb.repository.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviePaginatedRepositoryImpl @Inject constructor(
    private val localDataSource: MovieDao,
    private val remoteDataSource: MoviePaginatedService,
) : MoviePaginatedRepository {
    override suspend fun fetchPopularMovies(page: Int): Flow<Response<MoviePaginated>> {
        return flow {
            val remoteResponse = remoteDataSource.getPopularMovies(page)

            emit(remoteResponse.let {
                Response.Success(it.toDomain())
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchTopRatedMovies(page: Int): Flow<Response<MoviePaginated>> {
        return flow {
            val remoteResponse = remoteDataSource.getTopRatedMovies(page)
            Log.d("daisy", remoteResponse.toString())

            emit(remoteResponse.let {
                Response.Success(it.toDomain())
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchNowPlayingMovies(page: Int): Flow<Response<MoviePaginated>> {
        return flow {
            val remoteResponse = remoteDataSource.getNowPlayingMovies(page)

            emit(remoteResponse.let {
                Response.Success(it.toDomain())
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchSimilarMovies(movieId: Int): Flow<Response<MoviePaginated>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSearchedMovies(query: String): Flow<Response<MoviePaginated>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchDiscoveredMovies(
        genreIds: String,
        page: Int,
    ): Flow<Response<MoviePaginated>> {
        TODO("Not yet implemented")
    }
}