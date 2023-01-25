package com.daisy.domain.repository

import androidx.paging.PagingData
import com.daisy.constants.Response
import com.daisy.domain.models.movies.Movie
import kotlinx.coroutines.flow.Flow

interface SavedRepository {

    suspend fun isSaved(id: Int): Flow<Response<Boolean>>

    suspend fun insertSaved(id: Int)

    suspend fun deleteSaved(id: Int)

    suspend fun fetchSavedMovies(): Flow<PagingData<Movie>>
}