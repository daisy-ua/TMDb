package com.tmdb.repository.repositories.savedrepository

import com.tmdb.repository.utils.Response
import kotlinx.coroutines.flow.Flow

interface SavedRepository {

    suspend fun getSavedIds(): Flow<Response<List<Int>>>

    suspend fun isSaved(id: Int): Flow<Response<Boolean>>

    suspend fun insertSaved(id: Int)

    suspend fun deleteSaved(id: Int)
}