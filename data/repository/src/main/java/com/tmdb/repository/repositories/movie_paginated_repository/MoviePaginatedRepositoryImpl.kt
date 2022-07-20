package com.tmdb.repository.repositories.movie_paginated_repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tmdb.cache.dao.movies.MovieDao
import com.tmdb.models.movies.Movie
import com.tmdb.models.movies.MoviePaginated
import com.tmdb.network.services.movies.MoviePaginatedService
import com.tmdb.repository.mappers.toDomain
import com.tmdb.repository.repositories.movie_paginated_repository.source.MovieDiscoverPagingSource
import com.tmdb.repository.repositories.movie_paginated_repository.source.MovieSearchPagingSource
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

    override suspend fun fetchMovieDiscoverResult(
        options: Map<String, String>,
        includeAdult: Boolean,
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MovieDiscoverPagingSource(remoteDataSource, options) }
        ).flow
    }

    override suspend fun fetchMovieSearchResult(
        query: String,
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MovieSearchPagingSource(remoteDataSource, query) }
        ).flow
    }
}