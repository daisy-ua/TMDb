package com.tmdb.repository.repositories.moviepaginatedrepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.daisy.domain.models.movies.Movie
import com.daisy.domain.repository.MoviePaginatedRepository
import com.tmdb.network.services.movies.MoviePaginatedService
import com.tmdb.repository.repositories.moviepaginatedrepository.source.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviePaginatedRepositoryImpl @Inject constructor(
    private val remoteDataSource: MoviePaginatedService,
) : MoviePaginatedRepository {
    override suspend fun fetchTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MovieTopRatedPagingDataSource(remoteDataSource) }
        ).flow
    }

    override suspend fun fetchNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MovieNowPlayingPagingDataSource(remoteDataSource) }
        ).flow
    }

    override suspend fun fetchTrendingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { MovieTrendingPagingDataSource(remoteDataSource) }
        ).flow
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