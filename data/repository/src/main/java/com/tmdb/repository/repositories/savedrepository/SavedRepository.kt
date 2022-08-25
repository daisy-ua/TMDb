package com.tmdb.repository.repositories.savedrepository

import androidx.paging.PagingData
import com.tmdb.models.movies.Movie
import com.tmdb.repository.utils.Response
import kotlinx.coroutines.flow.Flow

interface SavedRepository {

    suspend fun isSaved(id: Int): Flow<Response<Boolean>>

    suspend fun insertSaved(id: Int)

    suspend fun deleteSaved(id: Int)

    suspend fun fetchSavedMovies(): Flow<PagingData<Movie>>
}