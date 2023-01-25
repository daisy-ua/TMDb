package com.tmdb.repository.repositories.savedrepository.source

import com.daisy.domain.models.movies.Movie
import com.tmdb.cache.dao.SavedMoviesDao
import com.tmdb.network.services.movies.MovieDetailsService
import com.tmdb.repository.mappers.toDomain
import com.tmdb.repository.repositories.moviepaginatedrepository.source.BasePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SavedPagingSource(
    private val localDao: SavedMoviesDao,
    private val apiService: MovieDetailsService,
) : BasePagingSource() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val nextPageNumber = params.key ?: 0

        return try {
            withContext(Dispatchers.IO) {

                val data = mutableListOf<Movie>()

                val entityList = localDao.getSavedMovies(
                    params.loadSize,
                    nextPageNumber * params.loadSize
                )

                for (entity in entityList) {
                    val movieDto = apiService.getMoviePreview(entity.id)
                    data.add(movieDto.toDomain())
                }

                val nextKey = if (data.isEmpty()) null else nextPageNumber + 1

                val prevKey = if (nextPageNumber == 0) null else nextPageNumber - 1

                LoadResult.Page(
                    data = data,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}