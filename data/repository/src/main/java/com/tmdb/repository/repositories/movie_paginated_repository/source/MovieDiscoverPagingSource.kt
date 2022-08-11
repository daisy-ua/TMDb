package com.tmdb.repository.repositories.movie_paginated_repository.source

import com.tmdb.models.movies.Movie
import com.tmdb.network.services.movies.MoviePaginatedService
import com.tmdb.repository.mappers.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDiscoverPagingSource(
    private val apiService: MoviePaginatedService,
    private val options: Map<String, String>,
) : BasePagingSource() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPageNumber = params.key ?: 1

            withContext(Dispatchers.IO) {
                val response = apiService.discoverMovies(nextPageNumber, options)

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