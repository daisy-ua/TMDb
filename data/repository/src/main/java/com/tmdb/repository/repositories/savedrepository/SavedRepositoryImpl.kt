package com.tmdb.repository.repositories.savedrepository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.daisy.constants.Response
import com.daisy.domain.models.movies.Movie
import com.daisy.domain.repository.SavedRepository
import com.tmdb.cache.dao.SavedMoviesDao
import com.tmdb.cache.entities.MovieEntity
import com.tmdb.network.services.movies.MovieDetailsService
import com.tmdb.repository.repositories.savedrepository.source.SavedPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime
import javax.inject.Inject

class SavedRepositoryImpl @Inject constructor(
    private val localDataSource: SavedMoviesDao,
    private val remoteDataSource: MovieDetailsService,
) : SavedRepository {
    override suspend fun isSaved(id: Int): Flow<Response<Boolean>> {
        return flow<Response<Boolean>> {
            emit(Response.Loading(null))

            val flow = try {
                val localResponse = localDataSource.isMovieSaved(id)

                localResponse.map {
                    Response.Success(it)
                }

            } catch (throwable: Throwable) {
                flow { emit(Response.Error(throwable)) }
            }

            emitAll(flow)
        }.flowOn(Dispatchers.IO)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun insertSaved(id: Int) {
        localDataSource.insertSavedMovie(MovieEntity(id, LocalDateTime.now()))
    }

    override suspend fun deleteSaved(id: Int) {
        localDataSource.deleteSavedMovie(id)
    }

    override suspend fun fetchSavedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = { SavedPagingSource(localDataSource, remoteDataSource) }
        ).flow
    }
}