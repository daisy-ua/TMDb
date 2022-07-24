package com.tmdb.repository.repositories.movie_paginated_repository.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tmdb.models.movies.Movie
import com.tmdb.network.services.movies.MoviePaginatedService
import com.tmdb.repository.mappers.toDomain

class MovieDiscoverPagingSource(
    private val apiService: MoviePaginatedService,
    private val options: Map<String, String>,
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = apiService.discoverMovies(nextPageNumber, options)

            LoadResult.Page(
                data = response.movies.map { it.toDomain() },
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}