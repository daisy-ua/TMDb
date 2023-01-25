package com.tmdb.repository.repositories.moviepaginatedrepository.source

import com.daisy.domain.models.movies.Movie
import com.tmdb.network.services.movies.MoviePaginatedService
import com.tmdb.repository.mappers.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieTrendingPagingDataSource(
    private val apiService: MoviePaginatedService,
) : BasePagingSource() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPageNumber = params.key ?: 1

            withContext(Dispatchers.IO) {
                val response = apiService.getTrendingMovies(nextPageNumber)

                val nextKey = if (response.movies.isEmpty()) null else nextPageNumber + 1

                val prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1

                LoadResult.Page(
                    data = response.movies.map { it.toDomain() },
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}