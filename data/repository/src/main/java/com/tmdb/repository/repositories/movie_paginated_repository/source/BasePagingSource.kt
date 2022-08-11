package com.tmdb.repository.repositories.movie_paginated_repository.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tmdb.models.movies.Movie

abstract class BasePagingSource(
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}