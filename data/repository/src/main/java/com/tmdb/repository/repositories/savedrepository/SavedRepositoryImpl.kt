package com.tmdb.repository.repositories.savedrepository

import com.tmdb.cache.dao.SavedMoviesDao
import com.tmdb.cache.entities.MovieEntity
import com.tmdb.repository.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SavedRepositoryImpl @Inject constructor(
    private val localDataSource: SavedMoviesDao,
) : SavedRepository {
    override suspend fun getSavedIds(): Flow<Response<List<Int>>> {
        return flow<Response<List<Int>>> {
            emit(Response.Loading(null))

            val flow = try {
                val localResponse = localDataSource.getSavedMovies()

                localResponse.map { flowData ->
                    Response.Success(
                        flowData.map { it.id }
                    )
                }

            } catch (throwable: Throwable) {
                flow { emit(Response.Error(throwable)) }
            }

            emitAll(flow)
        }.flowOn(Dispatchers.IO)
    }

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

    override suspend fun insertSaved(id: Int) {
        localDataSource.insertSavedMovie(MovieEntity(id))
    }

    override suspend fun deleteSaved(id: Int) {
        localDataSource.deleteSavedMovie(MovieEntity(id))
    }
}